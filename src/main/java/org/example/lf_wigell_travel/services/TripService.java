package org.example.lf_wigell_travel.services;

import org.example.lf_wigell_travel.dtos.TripDTO;
import org.example.lf_wigell_travel.entities.Trip;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TripService {

    ResponseEntity<TripDTO> addTrip(TripDTO trip);

    ResponseEntity<List<TripDTO>> getAllTrips();

    ResponseEntity<TripDTO> updateTrip(TripDTO trip);

    ResponseEntity<String> deleteTrip(Long id);
}
