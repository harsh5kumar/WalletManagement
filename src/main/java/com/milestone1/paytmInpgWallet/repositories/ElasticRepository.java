package com.milestone1.paytmInpgWallet.repositories;

import com.milestone1.paytmInpgWallet.entities.ElasticTransaction;
//import com.milestone1.paytmInpgWallet.entities.Transaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticRepository extends ElasticsearchRepository<ElasticTransaction, String> {
    List<ElasticTransaction> findBypayerphonenumber(Integer phone);

    List<ElasticTransaction> findBypayeephonenumber(Integer phone);
}
