package com.budget.track;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StockPriceProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC = "stock-price";
    private final String ALPHA_VANTAGE_API = "https://www.alphavantage.co/query";
    private final String API_KEY = "CIAVRU9GXJETK";

    public StockPriceProducer(KafkaTemplate<String, String> kafkaTemplate) {
        System.out.println("Kafka Startf hjnwdj");
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendStockPrices(String symbol) {
        WebClient webClient = WebClient.create(ALPHA_VANTAGE_API);
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("function", "TIME_SERIES_INTRADAY")
                    .queryParam("symbol", symbol)
                    .queryParam("interval", "1min")
                    .queryParam("apikey", API_KEY)
                    .build())
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> {
                    // Send stock data to Kafka topic
                    kafkaTemplate.send(new ProducerRecord<>(TOPIC, symbol, response));
                });
    }
}
