package me.study.product.model.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import me.study.product.model.entity.Product;

public interface ProductRepository {
	List<Product> findByIdIn(Set<UUID> productIds);

	Optional<Product> findById(UUID productId);

	Product save(Product product);
}
