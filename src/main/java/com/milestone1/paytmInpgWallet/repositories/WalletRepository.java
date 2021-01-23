package com.milestone1.paytmInpgWallet.repositories;

import java.util.List;
import com.milestone1.paytmInpgWallet.entities.Wallet;

import org.springframework.data.jpa.repository.JpaRepository;

//import com.milestone1.paytmInpgWallet.users.User;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	
	public List<Wallet> findByPhone(int phone);

}

