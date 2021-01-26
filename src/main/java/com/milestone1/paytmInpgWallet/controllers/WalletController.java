package com.milestone1.paytmInpgWallet.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//import java.util.logging.Level;

//import org.hibernate.annotations.common.util.impl.Log_.logger;
import com.milestone1.paytmInpgWallet.entities.ElasticTransaction;
import com.milestone1.paytmInpgWallet.entities.User;
import com.milestone1.paytmInpgWallet.entities.Wallet;
import com.milestone1.paytmInpgWallet.entities.Transaction;
import com.milestone1.paytmInpgWallet.repositories.ElasticRepository;
import com.milestone1.paytmInpgWallet.services.UserService;
import com.milestone1.paytmInpgWallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
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
	



	
}
