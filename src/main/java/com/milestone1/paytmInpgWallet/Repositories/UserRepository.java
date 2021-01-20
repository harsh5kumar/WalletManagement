package com.milestone1.paytmInpgWallet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//By inheriting from JpaRepository or CrudRepository, we can call 
//many methods without the need to implement them ourself

public interface UserRepository extends JpaRepository<User, Integer> {

	
//these are the validation methods defined by user in repository
	
	
	
	public List<User> findByEmailid(String emailid);

    public List<User> findByUserName(String userName);

    public List<User> findByMobileNumber(Integer mobileNumber);

}
