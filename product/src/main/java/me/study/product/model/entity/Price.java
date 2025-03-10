package me.study.product.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price {

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	private Price(BigDecimal price) {
		if (price.compareTo(BigDecimal.valueOf(200000L)) > 0) {
			throw new IllegalArgumentException("Price must be smaller than or equal to 200000");
		}
		this.price = price;
	}

	public static Price of(BigDecimal price) {
		return new Price(price);
	}

	public BigDecimal getPrice() {
		return price;
	}
}
