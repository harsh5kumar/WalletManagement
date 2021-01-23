package com.milestone1.paytmInpgWallet.repositories;

import java.util.List;

import com.milestone1.paytmInpgWallet.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	public List<Transaction> findBytxnid(Integer txnid);
	
	public List<Transaction> findBypayerphonenumber(int payerphonenumber);
	public List<Transaction> findBypayeephonenumber(int payeephonenumber);

}
