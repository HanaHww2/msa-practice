package me.study.product.model.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.study.common.entity.BaseEntity;

@Table(name = "p_products")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String name;

	@Embedded
	private Price price;

	@Embedded
	private Stock stock;

	private Product(String name, Price price, Stock stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public static Product of(String name, BigDecimal price, Integer stock) {
		return new Product(name, Price.of(price), Stock.of(stock));
	}

	public boolean decreaseStock(Integer quantity) {
		return this.stock.decreaseStock(quantity);
	}
}
