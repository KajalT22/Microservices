package com.osi.user.service.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.osi.user.service.entities.Hotel;
import com.osi.user.service.entities.Rating;
import com.osi.user.service.entities.User;
import com.osi.user.service.exceptions.ResourceNotFoundException;
import com.osi.user.service.external.services.HotelService;
import com.osi.user.service.repositories.UserRepo;
import com.osi.user.service.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private HotelService hotelService;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		//Generate uniq id as we are not generating the same in entity
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepo.save(user);
	}

	@Override
	public List<User> getAllUser() {
		
		return userRepo.findAll();
	}

	@Override
	public User getUser(String userId) {
		User user = userRepo.findById(userId)
		.orElseThrow(()
				->new ResourceNotFoundException("user not found with id: "+userId));
		
		//fetching ratings of above user from rating Service
		//call this url to fetch ratings of particular user
		//http://localhost:8083/ratings/users/32b84a3f-a293-4032-ac6a-3b8d4ad665ee
		
		//localhost:8083 instead will use service name
		Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		
		logger.info("{} ",ratingOfUser);
		
		List<Rating> ratings = Arrays.stream(ratingOfUser).toList();
		
		List<Rating> ratingList = ratings.stream().map(rating -> {
			//api call to hotelService to get the hotel
			//http://localhost:8082/hotel/32b84a3f-a293-4032-ac6a-3b8d4ad665ee
			//ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotel/"+rating.getHotelId(), Hotel.class);
			//Hotel hotel = forEntity.getBody();
			Hotel hotel = hotelService.getHotel(rating.getHotelId()); //by feign client
			//logger.info("Response Status code: {}", forEntity.getStatusCode());
			//set the hotel to rating 
			rating.setHotel(hotel);
			//return rating
			
			return rating;
		}).collect(Collectors.toList());
		
		user.setRatingList(ratingList);
		
		return user;
	}
	
	

}
