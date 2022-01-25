package com.notificationservice.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSender {
    public  void sendemail(String order){
//        log.info("This is the placed order",order);
        log.info("Order Placed Successfully - Order Number is {}", order);
        log.info("Email Sent For Order Id {}", order);
    }

}
