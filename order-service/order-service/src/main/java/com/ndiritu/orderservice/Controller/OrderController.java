package com.ndiritu.orderservice.Controller;

import com.ndiritu.orderservice.Dto.OrderDto;
import com.ndiritu.orderservice.Repo.OrderRepo;
import com.ndiritu.orderservice.client.InventoryClient;
import com.ndiritu.orderservice.model.Order;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
//import org.springframework.cloud.stream.function.StreamBridge;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.UUID;
//import java.util.concurrent.ExecutorService;
//import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderRepo orderRepo;
    private final InventoryClient inventoryClient;
//    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    private final StreamBridge streamBridge;
    private final ExecutorService traceableExecutorService;

    @PostMapping("/placeorder")
    public String placeorder(@RequestBody OrderDto orderdto){
       try {
        circuitBreakerFactory.configureExecutorService(traceableExecutorService);
        Resilience4JCircuitBreaker circuitBreaker=circuitBreakerFactory.create("inventory");

        java.util.function.Supplier<Boolean> booleanSupplier=()->orderdto.getOrderLineItems().stream()
                .allMatch(orderLineItems -> {
                log.info("Making call to inventory service for skuCode {}",orderLineItems.getSkuCode());
                    return inventoryClient.checkstock(orderLineItems.getSkuCode());
                });
           boolean productsinstock= circuitBreaker.run(booleanSupplier,throwable -> handleErrorCase());
           if(productsinstock) {
               Order order = new Order();
               order.setOrderLineItems(orderdto.getOrderLineItems());
               order.setOrderNumber(UUID.randomUUID().toString());
               orderRepo.save(order);
               log.info("sending order details to Notificaions Service with order id {}",order.getId());
//               streamBridge.send("notificationEventSupplier-out-0",order.getId());
               streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(order.getId()).build());

               return "Order placed successfully";
           }else{
               return "Order failed One of the produt in the order is not in stock";
           }
       }catch (Exception e){
           return "An exception happened while saving the order "+e.getMessage();
//           throw new RuntimeExcthe ordereption("An exception occurred while saving the order "+e.getMessage());
       }
    }

    private Boolean handleErrorCase() {
     return false;
    }
}
