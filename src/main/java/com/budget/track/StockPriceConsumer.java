package com.budget.track;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class StockPriceConsumer {


    private static final String REDIS_KEY_PREFIX = "stock-price:";

    @KafkaListener(topics = "stock-price", groupId = "stock-group")
    public void consume(String message) {
        
        System.out.println("Received Stock Price Data: " + message);

        // Process stock price data and store it in Redis
        Jedis jedis = new Jedis("localhost");
        jedis.set(REDIS_KEY_PREFIX + "latest", message);
        jedis.close();
    }
}

