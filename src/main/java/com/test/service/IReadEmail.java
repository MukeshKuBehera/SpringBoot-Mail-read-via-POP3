package com.test.service;

import org.springframework.stereotype.Repository;

@Repository
public interface IReadEmail {
	
	public void readEmail(String username,String password);

}
