package me.study.product.application.usecase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.study.common.exception.CustomApiException;
import me.study.product.global.exception.ProductException;
import me.study.product.infrastructure.RedisProductRepository;
import me.study.product.model.entity.Product;
import me.study.product.model.repository.ProductRepository;
import me.study.product.presentation.dto.request.CreateProductRequest;
import me.study.common.dto.DecreaseProductStockRequest;
import me.study.common.dto.DecreaseProductStockResponse;
import me.study.product.presentation.dto.response.ProductResponse;
import me.study.product.presentation.dto.response.ProductsResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final RedisProductRepository redisProductRepository;

	@Transactional
	@Override
	public ProductResponse createProduct(CreateProductRequest request) {

		Product product = request.toEntity();
		productRepository.save(product);
		return ProductResponse.from(product);
	}

	@Override
	public ProductResponse getProduct(UUID productId) {

		ProductResponse cachedProductResponse =
			redisProductRepository.getAndExpireByKey(productId.toString());
		if (cachedProductResponse != null) {
			return cachedProductResponse;
		}

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new CustomApiException(ProductException.INVALID_PRD_ID));
		ProductResponse productResponse = ProductResponse.from(product);

		redisProductRepository.setWithTtl(productId.toString(), productResponse);
		return productResponse;
	}

	@Override
	public ProductsResponse getProductList(Set<UUID> productIds) {

		// 1. 캐시 조회
		Map<UUID, ProductResponse> cachedPayload = new HashMap<>();
		for (UUID productId : productIds) {
			ProductResponse cachedProductResponse =
				redisProductRepository.getAndExpireByKey(productId.toString());
			if (cachedProductResponse != null) {
				cachedPayload.put(productId, cachedProductResponse);
			}
		}
		productIds.removeAll(cachedPayload.keySet());

		// 2. db 조회
		List<Product> products = productRepository.findByIdIn(productIds);
		Map<UUID, ProductResponse> payload = new HashMap<>();
		for (Product product : products) {
			ProductResponse productResponse = ProductResponse.from(product);
			payload.put(product.getId(), productResponse);

			redisProductRepository.setWithTtl(product.getId().toString(), productResponse);
		}

		// 3. 응답 반환
		payload.putAll(cachedPayload);
		return new ProductsResponse(payload);
	}

	@Transactional
	@Override
	public DecreaseProductStockResponse decreaseStock(DecreaseProductStockRequest request) {

		Product product = productRepository.findById(request.productId())
			.orElseThrow(() -> new CustomApiException(ProductException.INVALID_PRD_ID));
		boolean result = product.decreaseStock(request.quantity());

		redisProductRepository.setWithTtl(
			request.productId().toString(), ProductResponse.from(product));
		return DecreaseProductStockResponse.of(request.productId(), result);
	}


	@Transactional
	@Override
	public List<DecreaseProductStockResponse> decreaseStocks(List<DecreaseProductStockRequest> requests) {

		Map<UUID, Integer> quantityMap = requests.stream()
			.collect(Collectors.toMap(
				DecreaseProductStockRequest::productId,
				DecreaseProductStockRequest::quantity,
				Integer::sum
			));

		List<Product> products = productRepository.findByIdIn(quantityMap.keySet());

		List<DecreaseProductStockResponse> responses = products.stream()
			.map(product -> {
				boolean result = product.decreaseStock(quantityMap.get(product.getId()));
				// 트랜잭션이 완료되기 전에 캐시를 갱신해도 문제가 없을까.
				redisProductRepository.setWithTtl(product.getId().toString(), ProductResponse.from(product));
				return DecreaseProductStockResponse.of(product.getId(), result);
			})
			.toList();

		return responses;
	}

	private ProductResponse getProductResponseFromRedis(String key) {

		return redisProductRepository.getAndExpireByKey(key);
	}
}
