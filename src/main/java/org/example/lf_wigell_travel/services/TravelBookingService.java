package org.example.lf_wigell_travel.services;

import org.example.lf_wigell_travel.dtos.TravelBookingConfirmationDTO;
import org.example.lf_wigell_travel.dtos.TravelBookingRequestDTO;
import org.example.lf_wigell_travel.entities.TravelBooking;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface TravelBookingService {

    ResponseEntity<TravelBookingConfirmationDTO> newTravelBooking(TravelBookingRequestDTO request);

    ResponseEntity<List<TravelBookingConfirmationDTO>> getAllTravelBookings(String username);

    ResponseEntity<TravelBookingConfirmationDTO> cancelTravelBooking(Long id);
}
