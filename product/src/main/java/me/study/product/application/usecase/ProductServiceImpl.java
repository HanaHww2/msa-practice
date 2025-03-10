package me.study.product.application.usecase;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.study.common.exception.CustomApiException;
import me.study.product.global.exception.ProductException;
import me.study.product.global.utils.RedisUtil;
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
	private final RedisTemplate<String, Object> redisTemplate;


	@Transactional
	@Override
	public ProductResponse createProduct(CreateProductRequest request) {

		Product product = request.toEntity();
		productRepository.save(product);
		return ProductResponse.from(product);
	}

	@Override
	public ProductResponse getProduct(UUID productId) {

		String cacheKey = RedisUtil.getProductKey(productId.toString());
		ProductResponse cachedProductResponse = (ProductResponse) redisTemplate.opsForValue()
			.get(cacheKey);
		if (cachedProductResponse != null) {
			redisTemplate.expire(cacheKey, RedisUtil.RedisTTL.PRODUCT.getTtl(), TimeUnit.SECONDS);
			return cachedProductResponse;
		}

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new CustomApiException(ProductException.INVALID_PRD_ID));
		ProductResponse productResponse = ProductResponse.from(product);

		redisTemplate.opsForValue().set(cacheKey, productResponse, RedisUtil.RedisTTL.PRODUCT.getTtl(), TimeUnit.SECONDS);
		return productResponse;
	}

	@Override
	public ProductsResponse getProductList(Set<UUID> productIds) {
		// todo 캐싱 기능 추가
		// 1. 캐시 조회
		// 2. 전체 데이터 셋 - 캐시된 데이터 셋 디비 조회
		// 3. 응답 병합 및 반환


		List<Product> products = productRepository.findByIdIn(productIds);
		Map<UUID, ProductResponse> payload = products.stream()
			.collect(
				Collectors.toMap(
					Product::getId,
					ProductResponse::from,
					(existing, replacement) -> existing
				));
		return new ProductsResponse(payload);
	}

	@Transactional
	@Override
	public DecreaseProductStockResponse decreaseStock(DecreaseProductStockRequest request) {

		Product product = productRepository.findById(request.productId())
			.orElseThrow(() -> new CustomApiException(ProductException.INVALID_PRD_ID));
		boolean result = product.decreaseStock(request.quantity());

		redisTemplate.opsForValue()
			.set(RedisUtil.getProductKey(request.productId().toString()),
				ProductResponse.from(product),
				RedisUtil.RedisTTL.PRODUCT.getTtl(),
				TimeUnit.SECONDS);
		return DecreaseProductStockResponse.of(request.productId(), result);
	}


	@Transactional
	@Override
	public List<DecreaseProductStockResponse> decreaseStocks(List<DecreaseProductStockRequest> requests) {
		return requests.stream()
			.map(req -> this.decreaseStock(req))
			.toList();
	}
}
