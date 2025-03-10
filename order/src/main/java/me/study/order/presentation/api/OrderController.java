package me.study.order.presentation.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import me.study.order.application.usecase.OrderService;
import me.study.order.presentation.dto.request.CreateOrderRequest;
import me.study.order.presentation.dto.response.CommonResponse;
import me.study.order.presentation.dto.response.OrderResponse;

@RequiredArgsConstructor
@RequestMapping("/api/orders")
@RestController
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<CommonResponse<OrderResponse>> createOrderWithProducts(
		@Validated @RequestBody CreateOrderRequest requestDto) {

		OrderResponse response = orderService.createOrderWithProducts(requestDto);

		URI location = UriComponentsBuilder.fromUriString("/api/orders/{orderId}")
			.buildAndExpand(response.orderId())
			.toUri();

		return ResponseEntity.created(location)
			.body(new CommonResponse<>("Order Created", response));
	}
}
