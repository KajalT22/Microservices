package com.osi.hotel.services;

import java.util.List;

import com.osi.hotel.entities.Hotel;

public interface IHotelService {
	
	//create
	Hotel addHotel(Hotel hotel);
	
	List<Hotel> getAll();
	
	//get single
	Hotel getSingleHotel(String id);

}
