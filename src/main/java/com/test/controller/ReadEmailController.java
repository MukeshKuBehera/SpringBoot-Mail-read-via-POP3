package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.entity.UserEntity;
import com.test.service.IReadEmail;

@RestController
public class ReadEmailController {
	
	
	@Autowired
	private IReadEmail reademail;
	
	@GetMapping("/getMail/{username}/{password}")
	public List<String> readEmail(@PathVariable("username") String username,@PathVariable("password") String password) {
		
		
	    this.reademail.readEmail(username, password);
	    
	    
		
		return null;
	}

}
