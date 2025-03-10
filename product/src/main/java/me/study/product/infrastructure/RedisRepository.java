package me.study.product.infrastructure;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RedisRepository<K, V> {
	private final String PREFIX;
	private final Duration TIMEOUT;
	private final RedisTemplate<String, Object> redisTemplate;

	public void setWithTtl(K key, V value) {
		redisTemplate.opsForValue().set(PREFIX + key, value, TIMEOUT);
	}

	public V getByKey(K key) {
		return (V) redisTemplate.opsForValue().get(PREFIX + key);
	}

	public V getAndExpireByKey(K key) {
		return (V) redisTemplate.opsForValue().getAndExpire(PREFIX + key, TIMEOUT);
	}

	public boolean expireByKey(K key) {
		return redisTemplate.expire(PREFIX + key, TIMEOUT);
	}

	public Boolean deleteByKey(K key) {
		return redisTemplate.delete(PREFIX + key);
	}

	public Boolean hasKey(K key) {
		return redisTemplate.hasKey(PREFIX + key);
	}

	public Duration getRemainingExpirationByKey(K key) {
		Long expire = redisTemplate.getExpire(PREFIX + key);
		if (expire != null && expire >= 0) {
			return Duration.ofSeconds(expire);
		} else {
			return null; // 유효 기간이 없거나 키가 존재하지 않는 경우
		}
	}
}
