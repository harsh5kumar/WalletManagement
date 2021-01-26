package com.milestone1.paytmInpgWallet.controllers;

import com.milestone1.paytmInpgWallet.entities.ResponseHttp;
import com.milestone1.paytmInpgWallet.entities.Transaction;
import com.milestone1.paytmInpgWallet.entities.Wallet;
import com.milestone1.paytmInpgWallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class TransactionController {


    @Autowired
    private WalletService wservice;

    ///READ all the transactions/////////////////////////////////

    @GetMapping("/transactions")
    public List<Transaction> listTxns(){
        return wservice.getTxns();
    }

   /////////////////READ transaction by id/////////////////////////

   @GetMapping("/transaction/{txnid}")
    public ResponseEntity<Transaction> gettxnstatus(@PathVariable Integer txnid) {
        try {
            Transaction txn=wservice.gettxnbid(txnid);
            return new ResponseEntity<Transaction>(txn, HttpStatus.ACCEPTED);
        }catch(NoSuchElementException e) {
            return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND);
        }
    }


    ////////////TRANSACTION STATUS////////////////////////////////


    @GetMapping("/transactionstatus/{txnid}")
    public ResponseEntity<ResponseHttp> gettxnbid(@PathVariable Integer txnid) {
        ResponseHttp responseHttp;               ////responsebody added//////////
        try {
            Transaction txn=wservice.gettxnbid(txnid);
            responseHttp = new ResponseHttp("Transaction with id = "+txnid, "Successful");
            // return new ResponseEntity<Transaction>(txn, HttpStatus.ACCEPTED);
            return new ResponseEntity<>(responseHttp, OK);
        }catch(NoSuchElementException e) {
            responseHttp = new ResponseHttp("Transaction id not exists", "Not found");
            //return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(responseHttp, NOT_FOUND);
        }
    }




/////////////POST req for transaction creation//////////

    @PostMapping("/transaction")
    public String createTxn(@RequestBody Transaction transaction) {
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
                    wservice.saveTxn(transaction);
                    //kafkaTemplate.send(kafkaTopic, transaction);

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


///////////GET pagination transactions//////////////

    @GetMapping("/transactions/{phone}/{pageno}/{pagesize}")
    public List<Transaction> paging(@PathVariable Integer phone,@PathVariable Integer pageno,
                                    @PathVariable Integer pagesize){
        List<Transaction> txnassender = wservice.findBypayerphonenumber(phone);
        List<Transaction> txnasreceiver = wservice.findBypayeephonenumber(phone);

        List<Transaction> combolist =new ArrayList<Transaction>();

        combolist.addAll(txnassender);
        combolist.addAll(txnasreceiver);



		/*int pageSize=1; // Number of records per page
        int pageNo=1;
        pageNo=(pageNo-1)*pageSize; //The starting index of each page

    Integer sum = combolist.size(); //Total number of records

    if (pageNo+pageSize > sum) {
      combolist = combolist.subList(pageNo,sum );
    }else {
       combolist = combolist.subList(pageNo,pageNo+pageSize);
    }

		return combolist;*/

        int sum = combolist.size();

        int pageend=((pageno-1)*pagesize)+pagesize;
        int pageindex = (pageno-1)*pagesize;

        if(pageend>sum) {
            return combolist.subList(pageindex, sum);
        }
        else {
            return combolist.subList(pageindex, pageend);
        }
    }
}
