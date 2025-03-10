package me.study.product.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.study.common.exception.CustomApiException;
import me.study.product.global.exception.ProductException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

	@Column(nullable = false)
	private Integer stock;

	private Stock(int stock)	{
		if (stock < 1) {
			throw new CustomApiException(ProductException.INVALID_STOCK);
		}
		this.stock = stock;
	}

	public static Stock of(int stock)	{
		return new Stock(stock);
	}

	public Integer getStockQuantity() {
		return stock;
	}

	public boolean decreaseStock(Integer quantity) {
		if (stock - quantity < 0) {
			throw new CustomApiException(ProductException.UNAVAILABLE_STOCK);
		}
		stock -= quantity;
		return true;
	}
}
