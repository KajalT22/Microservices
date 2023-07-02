package com.osi.user.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osi.user.service.entities.User;
import com.osi.user.service.services.IUserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
	
	//create
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody User user){
		User savedUser = userService.saveUser(user);
		return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
	}
	
	//int retryCount=1;
	//get single user
	@GetMapping("/{userId}")
	//@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
	//@Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name = "userRateLimiter",fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		logger.info("Get Single user Handler: UserController");
		//logger.info("Retry Count: {}",retryCount);
		//retryCount++;
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	//creating fallback method for circuit breaker
	
	public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
		//logger.info("Fallback is excuted because service is down: ",ex.getMessage());
		ex.printStackTrace();
		User user = User.builder().email("dummy@gmail.com")
		.name("circuit breaker").about("fallback method some services may down").userId("1234").build();
				
		return new ResponseEntity<>(user,HttpStatus.OK);
		
	}
	
	//get all users
	@GetMapping("/")
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = userService.getAllUser();
		
		return ResponseEntity.ok(allUser);
	}
	


}
