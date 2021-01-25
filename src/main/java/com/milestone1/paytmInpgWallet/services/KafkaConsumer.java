package com.milestone1.paytmInpgWallet.services;

import com.milestone1.paytmInpgWallet.entities.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;


@Service
public class KafkaConsumer {

    @KafkaListener(topics = "txn_by_id", groupId = "group_json",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumeJson(Transaction transaction) {
        System.out.println("Consumed JSON Message: " + transaction);
    }
}
