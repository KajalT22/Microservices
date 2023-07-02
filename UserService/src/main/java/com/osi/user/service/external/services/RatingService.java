package com.osi.user.service.external.services;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.osi.user.service.entities.Rating;

@FeignClient("RATING-SERVICE")
public interface RatingService {
	//POST
	@PostMapping("/ratings/create")
	Rating createRating(Rating rating);
	
	
	//update 
	Rating updateRating();

}
