package com.budget;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("")
public class StockPriceController {

    private static final String REDIS_KEY_PREFIX = "stock-price:";
    private static final Logger logger = LoggerFactory.getLogger(StockPriceController.class);

    @GetMapping("/price")
    public String getLatestStockPrice(@RequestParam String symbol) {
        System.out.println("Recenced request ");
        logger.info("Received request for stock price for symbol: {}", symbol);
        
        try (Jedis jedis = new Jedis("localhost")) {
            String stockPrice = jedis.get(REDIS_KEY_PREFIX + symbol); // Changed to fetch price for the requested symbol

            if (stockPrice != null) {
                return "Latest price for " + symbol + ": " + stockPrice;
            } else {
                return "Stock price not found for symbol: " + symbol;
            }
        } catch (Exception e) {
            logger.error("Error retrieving stock price", e);
            return "Error retrieving stock price for symbol: " + symbol;
        }
    }

    @GetMapping("/ok")
    public String getMethodName(@RequestParam String param) {
        return new String("Done");
    }
    
}


