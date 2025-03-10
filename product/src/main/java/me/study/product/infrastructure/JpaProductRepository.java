package me.study.product.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.study.product.model.entity.Product;
import me.study.product.model.repository.ProductRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {

	List<Product> findByIdIn(List<UUID> productIds);
}
