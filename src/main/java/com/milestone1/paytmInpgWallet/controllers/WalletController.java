package com.milestone1.paytmInpgWallet.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//import java.util.logging.Level;

//import org.hibernate.annotations.common.util.impl.Log_.logger;
import com.milestone1.paytmInpgWallet.entities.User;
import com.milestone1.paytmInpgWallet.entities.Wallet;
import com.milestone1.paytmInpgWallet.entities.Transaction;
import com.milestone1.paytmInpgWallet.services.UserService;
import com.milestone1.paytmInpgWallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

	@Autowired
	private WalletService wservice;
	
	@Autowired
	private UserService service;
	
///////////////////////////////////////////////////
	
	///READ all the wallet//////
	
	@GetMapping("/wallets")
	public List<Wallet> listWallet(){
		System.out.println("started on this port");
		return wservice.getWallet();
	}
	
	///READ wallet by phone num////
	
	@GetMapping("/wallet/{phoneId}")
	public Wallet getWbid(@PathVariable Integer phoneId) {
		return wservice.getWbid(phoneId);
	}
	
	
	
	///READ all the transactions///
	
	@GetMapping("/transactions")
	public List<Transaction> listTxns(){
		return wservice.getTxns();
	}
	
	///READ transaction by id///////
	
	@GetMapping("/transaction/{txnid}")
	public ResponseEntity<Transaction> gettxnbid( @PathVariable Integer txnid) {
		try {
			Transaction txn=wservice.gettxnbid(txnid);
			return new ResponseEntity<Transaction>(txn,HttpStatus.ACCEPTED);
		}catch(NoSuchElementException e) {
			//logger.log(Level.INFO, "Cannot read nonexistent transaction");
			 return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND);
		}
	}
 ////////////TRANSACTION STATUS///////////
	
	@GetMapping("/transactionstatus/{txnid}")
	public String gettxnstatus(@PathVariable Integer txnid) {
		
	 List<Transaction> txn_status=wservice.findBytxnid(txnid);
	 
	 if(!txn_status.isEmpty())
	 {
		 return "Transaction complete";
	 }
	 else {
		 return "Transaction f";
	 }
		
	}
	
	
	
////////////////////////////////////////////////
	
  ///////////POST request for wallet creation//////////
	
	@PostMapping("/wallet")
	public String createWallet(@RequestBody Wallet wallet) {
		
		List<Wallet> phone_num=wservice.findByPhone(wallet.getPhone());
		List<User> user_phone_number=service.findbyMobileNumber(wallet.getPhone());
		if(!user_phone_number.isEmpty()) {
			if(phone_num.isEmpty()) {
				wservice.saveWallet(wallet);
				return "wallet created";
				}else {
					return "wallet already exists";
				}
		}else {
			return "User not present";
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
