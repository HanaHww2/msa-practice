package me.study.order.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.study.order.model.entity.Order;
import me.study.order.model.repository.OrderRepository;

public interface JpaOrderRepository extends JpaRepository<Order, UUID>, OrderRepository {
}
