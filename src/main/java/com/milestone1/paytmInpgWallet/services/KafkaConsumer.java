package com.milestone1.paytmInpgWallet.services;

import com.milestone1.paytmInpgWallet.entities.ElasticTransaction;
import com.milestone1.paytmInpgWallet.entities.Transaction;
import com.milestone1.paytmInpgWallet.repositories.ElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;


@Service
public class KafkaConsumer {

    @Autowired
    private ElasticRepository elasticRepository;

    @KafkaListener(topics = "txn_by_id", groupId = "group_json",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumeJson(ElasticTransaction transaction) {
        elasticRepository.save(transaction); /////////////////////////for saving in elastic repository//////
        System.out.println("Consumed JSON Message: " + transaction);
    }
}
