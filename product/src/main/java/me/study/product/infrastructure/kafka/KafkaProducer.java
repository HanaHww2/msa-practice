package me.study.product.infrastructure.kafka;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.study.product.infrastructure.kafka.event.DecreaseStockErrorEvent;

@Slf4j(topic = "Kafka::producer::")
@RequiredArgsConstructor
@Component
public class KafkaProducer {

	@Value("${kafka.topic.decrease-stock-error}")
	private String topic;
	private final KafkaTemplate<String, DecreaseStockErrorEvent> kafkaTemplate;

	public void sendDecreaseStockErrorEvent(DecreaseStockErrorEvent event) {
		String key = UUID.randomUUID().toString();
		kafkaTemplate.send(topic, key, event);
		log.info("Sent event to Kafka: {}", event);
	}
}
