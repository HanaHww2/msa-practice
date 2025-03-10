package me.study.order.model.repository;

import java.util.Optional;
import java.util.UUID;

import me.study.order.model.entity.Order;

public interface OrderRepository {
	Order save(Order order);

	Optional<Order> findById(UUID orderId);
}
