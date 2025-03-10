package me.study.order.infrastructure.feign;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import me.study.common.dto.DecreaseProductStockRequest;
import me.study.common.dto.DecreaseProductStockResponse;
import me.study.order.infrastructure.feign.dto.response.ProductResponse;
import me.study.order.infrastructure.feign.dto.response.ProductsResponse;
import me.study.order.presentation.dto.response.CommonResponse;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

	@GetMapping("/api/products")
	ResponseEntity<CommonResponse<ProductsResponse>> getProducts(@RequestParam List<UUID> productIds);

	@GetMapping("/api/products")
	ResponseEntity<CommonResponse<ProductResponse>> getProduct(@PathVariable UUID productId);

	@PatchMapping("/api/products/decrease")
	ResponseEntity<CommonResponse<List<DecreaseProductStockResponse>>> decreaseStock(@RequestBody List<DecreaseProductStockRequest> requests);

}
