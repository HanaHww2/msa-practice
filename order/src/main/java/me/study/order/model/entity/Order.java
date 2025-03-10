package me.study.order.model.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.study.common.entity.BaseEntity;
import me.study.common.exception.CustomApiException;
import me.study.order.global.exception.OrderException;
import me.study.order.model.enums.OrderStatus;

@Entity
@Table(name = "p_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private BigDecimal totalPrice;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus orderStatus;

	@Embedded
	private OrderItems orderItems;

	@Builder
	private Order(String name, OrderItems orderItems, BigDecimal totalPrice, OrderStatus orderStatus) {
		this.name = name;
		this.orderItems = orderItems;
		this.totalPrice = totalPrice;
		this.orderStatus = orderStatus;
	}

	public static Order of(OrderStatus orderStatus) {
		return Order.builder()
			.orderItems(new OrderItems())
			.orderStatus(orderStatus)
			.build();
	}

	public void assignName() {
		this.name = this.orderItems.getOrderName();
	}

	public void calcTotalPrice() {
		this.totalPrice = this.orderItems.getTotalPrice();
		if (this.totalPrice.compareTo(BigDecimal.valueOf(30000)) < 0) {
			throw new CustomApiException(OrderException.NOT_ENOUGH_TOTAL_PRICE);
		}
	}

	public void orderCancel() {
		this.orderStatus = OrderStatus.CANCELED;
	}

}
