package com.milestone1.paytmInpgWallet.entities;



import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Document(indexName = "transactions", indexStoreType = "transaction",shards = 2)
 public class ElasticTransaction{

    private String txnid;
    private int payerphonenumber;
    private int payeephonenumber;
    private int txnamount;

    public ElasticTransaction() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ElasticTransaction(String txnid, int payerphonenumber, int payeephonenumber, int txnamount) {
        super();
        this.txnid = txnid;
        this.payerphonenumber = payerphonenumber;
        this.payeephonenumber = payeephonenumber;
        this.txnamount = txnamount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public int getPayerphonenumber() {
        return payerphonenumber;
    }

    public void setPayerphonenumber(int payerphonenumber) {
        this.payerphonenumber = payerphonenumber;
    }

    public int getPayeephonenumber() {
        return payeephonenumber;
    }

    public void setPayeephonenumber(int payeephonenumber) {
        this.payeephonenumber = payeephonenumber;
    }

    public int getTxnamount() {
        return txnamount;
    }

    public void setTxnamount(int txnamount) {
        this.txnamount = txnamount;
    }

}

