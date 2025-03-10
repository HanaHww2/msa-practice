package me.study.product.application.usecase;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import me.study.product.presentation.dto.request.CreateProductRequest;
import me.study.common.dto.DecreaseProductStockRequest;
import me.study.common.dto.DecreaseProductStockResponse;
import me.study.product.presentation.dto.response.ProductsResponse;
import me.study.product.presentation.dto.response.ProductResponse;

public interface ProductService {

	ProductResponse createProduct(CreateProductRequest request);

	ProductResponse getProduct(UUID productId);

	DecreaseProductStockResponse decreaseStock(DecreaseProductStockRequest request);

	ProductsResponse getProductList(Set<UUID> productIds);

	List<DecreaseProductStockResponse> decreaseStocks(List<DecreaseProductStockRequest> requests);
}
