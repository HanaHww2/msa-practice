package me.study.product.infrastructure.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.study.common.exception.CustomApiException;
import me.study.product.application.usecase.ProductService;
import me.study.product.infrastructure.kafka.event.DecreaseStockErrorEvent;
import me.study.product.infrastructure.kafka.event.DecreaseStockEvent;

@Slf4j(topic = "Kafka::consumer::")
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

	private final ProductService productService;
	private final KafkaProducer kafkaProducer;

	@KafkaListener(topics = "${kafka.topic.decrease-stock}")
	protected void consumeDecreaseStockEvent(DecreaseStockEvent event, Acknowledgment ack) {
		log.info("Consume decrease-stock event::{}", event);
		try {
			productService.decreaseStocks(event.decreaseProductStockRequests());
		} catch (CustomApiException e) {
			kafkaProducer.sendDecreaseStockErrorEvent(new DecreaseStockErrorEvent(event.orderId()));
		}
		ack.acknowledge();
	}
}
