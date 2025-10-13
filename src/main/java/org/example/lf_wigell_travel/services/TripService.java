package org.example.lf_wigell_travel.services;

import org.example.lf_wigell_travel.dtos.TripDTO;


import java.util.List;

public interface TripService {

    TripDTO addTrip(TripDTO trip);

    List<TripDTO> getAllTrips();

    TripDTO updateTrip(TripDTO trip);

    String deleteTrip(Long id);
}
