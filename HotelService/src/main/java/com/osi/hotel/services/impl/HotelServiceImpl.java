package com.osi.hotel.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osi.hotel.entities.Hotel;
import com.osi.hotel.exceptions.ResourceNotFoundException;
import com.osi.hotel.repositories.HotelRepo;
import com.osi.hotel.services.IHotelService;

@Service
public class HotelServiceImpl implements IHotelService {
	
	@Autowired
	private HotelRepo hotelRepo;

	@Override
	public Hotel addHotel(Hotel hotel) {
		//gen random id 
		String randomId = UUID.randomUUID().toString();
		hotel.setId(randomId);
		return hotelRepo.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		// TODO Auto-generated method stub
		return hotelRepo.findAll();
	}

	@Override
	public Hotel getSingleHotel(String id) {
		// TODO Auto-generated method stub
		return hotelRepo.findById(id).orElseThrow(()
				->new ResourceNotFoundException("Hotel not found for id: "+id));
	}
	
	

}
