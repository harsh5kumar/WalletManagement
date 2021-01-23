package com.milestone1.paytmInpgWallet.entities;

import net.bytebuddy.implementation.bind.annotation.Default;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wallet")
public class Wallet {

	private int phoneId;
	private int phone;
	private int wallBalance;

	public Wallet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Wallet(int phoneId,int phone, int wallBalance) {
		super();
		this.phoneId = phoneId;
		this.phone = phone;
		this.wallBalance = wallBalance;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
	}
	
	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public int getWallBalance() {
		return wallBalance;
	}

	public void setWallBalance(int wallBalance) {
		this.wallBalance = wallBalance;
	}
	
	public void updateBalance(Integer update){
		this.wallBalance += update; 
		}

}
