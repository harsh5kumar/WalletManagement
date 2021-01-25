package com.milestone1.paytmInpgWallet.controllers;

import com.milestone1.paytmInpgWallet.services.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



//@RestController
//@RequestMapping(value = "/txn-kafka/")
public class KafkaController {

    /*@Autowired
    KafkaSender kafkaSender;

    @GetMapping(value = "/transaction")
    public String producer(@RequestParam("message") String message) {
        kafkaSender.send(message);

        return "Message sent to the Kafka Topic Successfully";
    }*/

}
