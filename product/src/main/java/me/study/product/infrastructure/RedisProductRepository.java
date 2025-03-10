package me.study.product.infrastructure;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import me.study.product.presentation.dto.response.ProductResponse;

@Repository
public class RedisProductRepository extends RedisRepository<String, ProductResponse> {
	private static final String PREFIX = "product:";
	private static final Duration TIMEOUT = Duration.ofMinutes(30);

	public RedisProductRepository(RedisTemplate<String, Object> redisTemplate) {
		super(PREFIX, TIMEOUT, redisTemplate);
	}
}
