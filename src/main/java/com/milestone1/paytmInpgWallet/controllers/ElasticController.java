package com.milestone1.paytmInpgWallet.controllers;

import com.milestone1.paytmInpgWallet.entities.ElasticTransaction;
import com.milestone1.paytmInpgWallet.entities.Wallet;
import com.milestone1.paytmInpgWallet.repositories.ElasticRepository;
import com.milestone1.paytmInpgWallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ElasticController {

    @Autowired
    private ElasticRepository elasticRepository;

    @Autowired
    private WalletService wservice;

    @Autowired
    private KafkaTemplate<String,ElasticTransaction> kafkaTemplate;

    String kafkaTopic = "txn_by_id";


    @PostMapping("/eltransaction")
    public String createTxn(@RequestBody ElasticTransaction transaction) {
        List<Wallet> payer_num=wservice.findByPhone(transaction.getPayerphonenumber());
        List<Wallet> payee_num=wservice.findByPhone(transaction.getPayeephonenumber());

        int payer_balance=payer_num.get(0).getWallBalance();
        int txnAmount=transaction.getTxnamount();

        if(!payer_num.isEmpty())
        {
            if(!payee_num.isEmpty())
            {
                if(payer_balance>=txnAmount)
                {
                    //wservice.saveTxn(transaction);
                    kafkaTemplate.send(kafkaTopic, transaction); ////send to the kafka topic//////////

                    Wallet payer=payer_num.get(0);
                    Wallet payee=payee_num.get(0);

                    payer.updateBalance(-txnAmount);
                    payee.updateBalance(txnAmount);

                    wservice.saveWallet(payer);
                    wservice.saveWallet(payee);

                    return "Successfully sent";
                }
                else return "Insufficient balance";
            } else return "payee doesn't exist";
        } else return "payer doesn't exist";

    }


//////////////GET REQ for finding txns from elasticDB////////////////////////

    @GetMapping("/transacts/{phone}")
    public List<ElasticTransaction> paging(@PathVariable Integer phone ){
        List<ElasticTransaction> txnassender = elasticRepository.findBypayerphonenumber(phone);   //el repo////
        List<ElasticTransaction> txnasreceiver = elasticRepository.findBypayeephonenumber(phone);  ///el repo///

        List<ElasticTransaction> combolist = new ArrayList<ElasticTransaction>();

        combolist.addAll(txnassender);
        combolist.addAll(txnasreceiver);

        return combolist;
    }

}
