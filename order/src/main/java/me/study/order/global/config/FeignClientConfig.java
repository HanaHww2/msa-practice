package me.study.order.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import me.study.order.global.exception.FeignClientErrorDecoder;

@Configuration
@RequiredArgsConstructor
@EnableFeignClients("me.study.order.infrastructure.feign")
public class FeignClientConfig {

	private final ObjectMapper objectMapper;

	// @Bean
	// public FeignClientErrorDecoder feignErrorDecoder() {
	// 	return new FeignClientErrorDecoder(objectMapper);
	// }
}
