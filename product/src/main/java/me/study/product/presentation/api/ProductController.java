package me.study.product.presentation.api;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import me.study.product.presentation.dto.request.CreateProductRequest;
import me.study.common.dto.DecreaseProductStockRequest;
import me.study.common.dto.DecreaseProductStockResponse;
import me.study.common.dto.CommonResponse;
import me.study.product.presentation.dto.response.ProductsResponse;
import me.study.product.application.usecase.ProductService;
import me.study.product.presentation.dto.response.ProductResponse;

@RequiredArgsConstructor
@RequestMapping("/api/products")
@RestController
public class ProductController {

	private final ProductService productService;

	@GetMapping("/{productId}")
	public ResponseEntity<CommonResponse<ProductResponse>> getProduct(
		@PathVariable UUID productId
	) {
		ProductResponse productResponse = productService.getProduct(productId);
		return ResponseEntity.ok()
			.body(new CommonResponse<>("Successfully Get", productResponse));
	}

	@GetMapping
	public ResponseEntity<CommonResponse<ProductsResponse>> getProductList(
		@RequestParam Set<UUID> productIds
	) {
		ProductsResponse productsResponse = productService.getProductList(productIds);
		return ResponseEntity.ok()
			.body(new CommonResponse<>("Successfully Get", productsResponse));
	}

	@PostMapping
	public ResponseEntity<CommonResponse<Void>> createProduct(
		@RequestBody CreateProductRequest request
	) {
		ProductResponse productResponse = productService.createProduct(request);

		var uri = UriComponentsBuilder.fromUriString("/api/products/{productId}")
			.buildAndExpand(productResponse.id())
			.toUri();
		return ResponseEntity.created(uri)
			.body(new CommonResponse<>("Successfully Created", null));
	}

	@PatchMapping("/decrease/one")
	public ResponseEntity<CommonResponse<DecreaseProductStockResponse>> decreaseStock(
		@RequestBody DecreaseProductStockRequest request
	) {
		DecreaseProductStockResponse decreaseProductStockResponse = productService.decreaseStock(request);
		return ResponseEntity.ok()
			.body(new CommonResponse<>("Successfully Decreased", decreaseProductStockResponse));
	}

	@PatchMapping("/decrease")
	public ResponseEntity<CommonResponse<List<DecreaseProductStockResponse>>> decreaseStocks(
		@RequestBody List<DecreaseProductStockRequest> requests
	) {
		List<DecreaseProductStockResponse> decreaseProductStockResponses = productService.decreaseStocks(requests);
		return ResponseEntity.ok()
			.body(new CommonResponse<>("Successfully Decreased", decreaseProductStockResponses));
	}
}
