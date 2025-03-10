package me.study.order.infrastructure.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.study.order.application.usecase.OrderService;
import me.study.order.infrastructure.kafka.event.DecreaseStockErrorEvent;

@Slf4j(topic = "Kafka::consumer::")
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

	private final OrderService orderService;

	@KafkaListener(topics = "${kafka.topic.decrease-stock-error}")
	protected void consumeDecreaseStockEvent(DecreaseStockErrorEvent event, Acknowledgment ack) {
		log.info("Consume decrease-stock-error event::{}", event);

		orderService.cancelOrder(event.orderId());
		ack.acknowledge();
	}
}
