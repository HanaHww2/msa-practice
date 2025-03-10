package me.study.order.infrastructure.kafka;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.study.order.infrastructure.kafka.event.DecreaseStockEvent;

@Slf4j(topic = "Kafka::producer::")
@RequiredArgsConstructor
@Component
public class KafkaProducer {

	@Value("${kafka.topic.decrease-stock}")
	private String topic;
	private final KafkaTemplate<String, DecreaseStockEvent> kafkaTemplate;

	public void sendDecreaseStockEvent(DecreaseStockEvent event) {
		String key = UUID.randomUUID().toString();
		kafkaTemplate.send(topic, key, event)
			.whenComplete((r, e) -> {
				if (e != null) {
					handleFailure(event);
				}
			});
		log.info("Sent event to Kafka: {}", event);
	}

	private void handleFailure(DecreaseStockEvent event) {
		log.debug("Failed to send decrease-stock event: {}", event);
		// 아웃박스?
	}
}
