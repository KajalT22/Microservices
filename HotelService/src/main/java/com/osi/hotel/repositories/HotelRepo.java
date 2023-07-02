package com.osi.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osi.hotel.entities.Hotel;

public interface HotelRepo extends JpaRepository<Hotel, String> {

}
