package me.study.order.model.repository;

import me.study.order.model.entity.Order;

public interface OrderRepository {
	Order save(Order order);
}
