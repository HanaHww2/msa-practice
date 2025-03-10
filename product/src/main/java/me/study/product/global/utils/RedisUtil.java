package me.study.product.global.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class RedisUtil {

	private static final String PRODUCT_KEY_PREFIX = "product:";

	public static String getProductKey(String productId) {
		return PRODUCT_KEY_PREFIX + productId;
	}

	@Getter
	@AllArgsConstructor
	public enum RedisTTL {

		PRODUCT(600L);
		private long ttl;
	}
}
