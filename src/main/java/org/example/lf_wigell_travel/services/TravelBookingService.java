package org.example.lf_wigell_travel.services;

import org.example.lf_wigell_travel.dtos.TravelBookingConfirmationDTO;
import org.example.lf_wigell_travel.dtos.TravelBookingRequestDTO;
import java.util.List;

public interface TravelBookingService {

    TravelBookingConfirmationDTO newTravelBooking(TravelBookingRequestDTO request);

    List<TravelBookingConfirmationDTO> getAllTravelBookings();

    TravelBookingConfirmationDTO cancelTravelBooking(Long id);

    List<TravelBookingConfirmationDTO> getCancelledTravelBookings();

    List<TravelBookingConfirmationDTO> getAllPastBookings();

    List<TravelBookingConfirmationDTO> getAllUpcomingBookings();
}
