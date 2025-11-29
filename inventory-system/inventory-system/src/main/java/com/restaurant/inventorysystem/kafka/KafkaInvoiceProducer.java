package com.restaurant.inventorysystem.kafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaInvoiceProducer {

    private final String TOPIC_NAME = "invoice-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendInvoiceEvent(String invoiceJson) {
        kafkaTemplate.send(TOPIC_NAME, invoiceJson);
        System.out.println("Invoice event sent to Kafka: " + invoiceJson);
    }
}
