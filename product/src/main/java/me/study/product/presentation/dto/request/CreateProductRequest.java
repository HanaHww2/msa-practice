package me.study.product.presentation.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import me.study.product.model.entity.Product;

public record CreateProductRequest(
	@NotBlank String name,
	@DecimalMax("200000.00")
	@DecimalMin("0.00")
	@Digits(integer = 10, fraction = 2)
	BigDecimal price,
	@Min(1)
	Integer stock
) {
	public Product toEntity() {
		return Product.of(name, price, stock);
	}
}
