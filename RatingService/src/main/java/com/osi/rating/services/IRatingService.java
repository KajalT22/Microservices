package com.osi.rating.services;

import java.util.List;

import com.osi.rating.entities.Rating;

public interface IRatingService {
	
	//create
	Rating createRating(Rating rating);
	
	//get All
	List<Rating> getRatings();
	
	
	//get all by userId
	List<Rating> getRatingByUser(String userId);
	
	
	//get all by Hotel
	List<Rating> getRatingByHotel(String hotelId);

}
