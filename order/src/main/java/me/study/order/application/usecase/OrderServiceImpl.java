package me.study.order.application.usecase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.study.common.dto.DecreaseProductStockRequest;
import me.study.common.exception.CustomApiException;
import me.study.order.global.exception.OrderException;
import me.study.order.infrastructure.feign.ProductFeignClient;
import me.study.order.infrastructure.feign.dto.response.ProductResponse;
import me.study.order.infrastructure.feign.dto.response.ProductsResponse;
import me.study.order.model.entity.Order;
import me.study.order.model.entity.OrderItem;
import me.study.order.model.repository.OrderRepository;
import me.study.order.presentation.dto.request.CreateOrderProductRequest;
import me.study.order.presentation.dto.request.CreateOrderRequest;
import me.study.order.presentation.dto.response.OrderResponse;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ProductFeignClient productFeignClient;

	@Override
	@Transactional
	public OrderResponse createOrderWithProducts(CreateOrderRequest request) {

		ProductsResponse productsResponse = productFeignClient.getProducts(
			request.orderProducts()
				.stream()
				.map(CreateOrderProductRequest::productId)
				.toList())
			.getBody()
			.getResult();

		Order order = request.toEntity();
		List<DecreaseProductStockRequest> decreaseRequests = new ArrayList<>();
		for (CreateOrderProductRequest orderProductRequest : request.orderProducts()) {

			ProductResponse productResponse = productsResponse.products()
				.get(orderProductRequest.productId());
			if (!this.validateRequest(orderProductRequest, productResponse)) {
				throw new IllegalArgumentException("orderProducts contains invalid product");
			}

			OrderItem orderItem = orderProductRequest.toEntity();
			orderItem.associateWithOrder(order);

			decreaseRequests.add(
				new DecreaseProductStockRequest(
					orderProductRequest.productId(),
					orderProductRequest.productQuantity())
			);
		}
		order.assignName();
		order.calcTotalPrice();
		orderRepository.save(order);

		// 재고 차감 요청
		productFeignClient.decreaseStock(decreaseRequests);

		return OrderResponse.from(order);
	}

	public boolean validateRequest(
		CreateOrderProductRequest orderProductRequest, ProductResponse productResponse)
	{
		if (productResponse.stock() < orderProductRequest.productQuantity()) {
			throw new CustomApiException(OrderException.NOT_ENOUGH_STOCK);
		}
		if (productResponse.price().compareTo(orderProductRequest.productPrice()) != 0) {
			throw new CustomApiException(OrderException.INVALID_PRODUCT_INFO);
		}
		if (!productResponse.name().equals(orderProductRequest.productName())) {
			throw new CustomApiException(OrderException.INVALID_PRODUCT_INFO);
		}
		return true;
	}
}
