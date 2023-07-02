package com.osi.rating.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osi.rating.entities.Rating;
import com.osi.rating.services.IRatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {
	@Autowired
	private IRatingService ratingService;

	// create
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/create")
	public ResponseEntity<Rating> addRating(@RequestBody Rating rating) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.createRating(rating));
	}

	// get All
	@GetMapping("/")
	public ResponseEntity<List<Rating>> getAllRatings() {
		return ResponseEntity.ok(ratingService.getRatings());
	}

	// get all by user
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<Rating>> getAllRatingByUser(@PathVariable String userId) {
		return ResponseEntity.ok(ratingService.getRatingByUser(userId));
	}

	// get all by Hotel
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<List<Rating>> getAllRatingByHotel(@PathVariable String hotelId) {
		return ResponseEntity.ok(ratingService.getRatingByHotel(hotelId));
	}

}
