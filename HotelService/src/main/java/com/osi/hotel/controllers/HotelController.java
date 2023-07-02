package com.osi.hotel.controllers;

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

import com.osi.hotel.entities.Hotel;
import com.osi.hotel.services.IHotelService;

@RestController
@RequestMapping("/hotel")
public class HotelController {
	
	@Autowired
	private IHotelService hotelService;
	
	//create 
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/create")
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
		return ResponseEntity
				.status(HttpStatus.CREATED).body(hotelService.addHotel(hotel));
	}
	
	//get single
	@PreAuthorize("hasAuthority('SCOPE_internal')") //this method only access by those who has internal scope 
	@GetMapping("/{hotelId}")
	public ResponseEntity<Hotel> getHotel(@PathVariable String hotelId){
		return ResponseEntity
				.status(HttpStatus.CREATED).body(hotelService.getSingleHotel(hotelId));
	}
	
	//get All
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping("/")
	public ResponseEntity<List<Hotel>> getAllHotel(){
		return ResponseEntity
				.status(HttpStatus.CREATED).body(hotelService.getAll());
	}
	
	
	
	
	

}
