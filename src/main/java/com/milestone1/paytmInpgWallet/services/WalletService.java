package com.milestone1.paytmInpgWallet.services;

import java.util.List;

import javax.transaction.Transactional;

import com.milestone1.paytmInpgWallet.repositories.TransactionRepository;
import com.milestone1.paytmInpgWallet.repositories.UserRepository;
import com.milestone1.paytmInpgWallet.repositories.WalletRepository;
import com.milestone1.paytmInpgWallet.entities.Wallet;
import com.milestone1.paytmInpgWallet.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WalletService {
	
	@Autowired
	private WalletRepository walletrepo;
	@Autowired
	private TransactionRepository txnrepo;
	@Autowired
	private UserRepository repo;
	
	
	////////////////GET///////////////////
	////get wallets////
	public List<Wallet> getWallet(){
		return walletrepo.findAll();
	}
	
	public List<Transaction> getTxns(){
		return txnrepo.findAll();
	}
	
	
	/////get trnsactions///
	public Wallet getWbid(int phoneId) {
		return walletrepo.findById(phoneId).get();
	}
	
	public Transaction gettxnbid(Integer txnid) {
		return txnrepo.findById(txnid).get();
	}
	
	///////////////POST//////////////////////////
	
	public void saveWallet(Wallet wallet) {
		walletrepo.save(wallet);
	}
	
	public void saveTxn(Transaction transaction) {
		txnrepo.save(transaction);
	}
    
	////////////////////////////////////////
	
	
	//////////////custom validation methods////////
	
	public List<Wallet> findByPhone(int phone) {
		return walletrepo.findByPhone(phone);
		}
	
	public List<Transaction> findBytxnid(int txnid) {
		return txnrepo.findBytxnid(txnid);
		}
	
	public List<Transaction> findBypayerphonenumber(int payerphonenumber) {
		return txnrepo.findBypayerphonenumber(payerphonenumber);
		}
	
	public List<Transaction> findBypayeephonenumber(int payeephonenumber) {
		return txnrepo.findBypayeephonenumber(payeephonenumber);
		}
}
