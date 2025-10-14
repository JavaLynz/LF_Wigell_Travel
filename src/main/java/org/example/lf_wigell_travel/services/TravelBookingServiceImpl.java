package org.example.lf_wigell_travel.services;

import org.example.lf_wigell_travel.dtos.ConversionResponse;
import org.example.lf_wigell_travel.dtos.TravelBookingConfirmationDTO;
import org.example.lf_wigell_travel.dtos.TravelBookingRequestDTO;
import org.example.lf_wigell_travel.entities.TravelBooking;
import org.example.lf_wigell_travel.entities.Trip;
import org.example.lf_wigell_travel.exceptions.ResourceNotFoundException;
import org.example.lf_wigell_travel.repositories.TravelBookingRepository;
import org.example.lf_wigell_travel.repositories.TripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TravelBookingServiceImpl implements TravelBookingService {


    private static final Logger LOGGER = LoggerFactory.getLogger(TravelBookingServiceImpl.class);
    private final TripRepository tripRepository;
    private final TravelBookingRepository bookingRepository;
    private final TravelBookingRepository travelBookingRepository;

    public TravelBookingServiceImpl(TravelBookingRepository bookingRepository, TripRepository tripRepository, TravelBookingRepository travelBookingRepository) {
        this.bookingRepository = bookingRepository;
        this.tripRepository = tripRepository;
        this.travelBookingRepository = travelBookingRepository;
    }


    @Override
    public TravelBookingConfirmationDTO newTravelBooking(TravelBookingRequestDTO request) {

        String customerUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Trip> tripOpt = tripRepository.findById(request.getTripId());

        if(tripOpt.isEmpty()) {
            throw new ResourceNotFoundException("TRIP","ID",request.getTripId());
        }

        Trip trip = tripOpt.get();

        BigDecimal totalPriceSEK = trip.getTripPricePerWeekSEK().multiply(BigDecimal.valueOf(request.getNumberOfWeeks()));

        TravelBooking booking = new TravelBooking();
        booking.setTrip(trip);
        booking.setTravelCustomer(customerUsername);
        booking.setDepartureDate(request.getDepartureDate());
        booking.setDurationWeeks(request.getNumberOfWeeks());
        booking.setTotalPriceSEK(totalPriceSEK);
        booking.setCancelled(false);

        TravelBooking savedBooking = bookingRepository.save(booking);

        LOGGER.info("User {} booked {} weeks at {}.", customerUsername, request.getNumberOfWeeks(), trip.getDestinationCity());
        return getBookingConfirmation(savedBooking.getBookingId(),"Booking successfully created.");

    }

    public TravelBookingConfirmationDTO getBookingConfirmation(Long bookingId, String message) {

        TravelBooking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new ResourceNotFoundException("Booking", "ID", bookingId)
        );

        Trip trip = booking.getTrip();


            TravelBookingConfirmationDTO dto = new TravelBookingConfirmationDTO();
            dto.setBookingId(booking.getBookingId());
            dto.setCustomerName(booking.getTravelCustomer());
            dto.setTripId(trip.getTripId());
            dto.setDestinationCity(trip.getDestinationCity());
            dto.setDestinationCountry(trip.getDestinationCountry());
            dto.setHotelName(trip.getHotelName());
            dto.setDepartureDate(booking.getDepartureDate());
            dto.setDurationWeeks(booking.getDurationWeeks());
            dto.setTotalPriceSek(booking.getTotalPriceSEK());
            dto.setTotalPriceEur(convertSekToEur(booking.getTotalPriceSEK()));
            dto.setMessage(message);
            dto.setCancelled(false);

            return dto;
        }


    public BigDecimal convertSekToEur(BigDecimal amountInSek) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.frankfurter.app/latest?from=SEK&to=EUR";
        ResponseEntity<ConversionResponse> response = restTemplate.getForEntity(url, ConversionResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            BigDecimal eurRate = response.getBody().getRates().get("EUR");

            return amountInSek
                    .multiply(eurRate)
                    .setScale(2, RoundingMode.HALF_UP);

        }

            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to fetch EUR conversion rate");
        }


    public TravelBookingConfirmationDTO convertToDTO(TravelBooking travelBooking) {
        return getBookingConfirmation(travelBooking.getBookingId(), "Booking confirmed");
    }

    @Override
    public List<TravelBookingConfirmationDTO> getAllTravelBookings() {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        List<TravelBooking> bookings = bookingRepository.findByTravelCustomerAndCancelledFalse(loggedInUsername);

        if(bookings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No active travel bookings found");
        }

        return travelBookingList(bookings);
    }

    @Override
    public TravelBookingConfirmationDTO cancelTravelBooking(Long id) {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<TravelBooking> optionalBooking = bookingRepository.findByBookingId(id);
        if(optionalBooking.isPresent()) {
            TravelBooking travelBookingToCancel = optionalBooking.get();
            LocalDate todaysDate = LocalDate.now();
            LocalDate departureDate = travelBookingToCancel.getDepartureDate();

            if(todaysDate.isBefore(departureDate.minusDays(6))){
                travelBookingToCancel.setCancelled(true);
                travelBookingRepository.save(travelBookingToCancel);

                LOGGER.info("Booking with id {} cancelled by user {}", id, loggedInUsername);
                TravelBookingConfirmationDTO cancellationConfirmationDTO = getBookingConfirmation(travelBookingToCancel.getBookingId(),"Travel booking with id: " + travelBookingToCancel.getBookingId() + " has been successfully cancelled.");
                cancellationConfirmationDTO.setCancelled(true);
                return cancellationConfirmationDTO;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cancellation not allowed less than 7 days before departure date");
            }

        } else {
            throw new ResourceNotFoundException("Booking","ID",id);
        }

    }

    public List<TravelBookingConfirmationDTO> travelBookingList(List<TravelBooking> travelBookings){
        if (travelBookings == null || travelBookings.isEmpty()) {
            // Return an empty list with a message DTO
            TravelBookingConfirmationDTO emptyMessageDTO = new TravelBookingConfirmationDTO();
            emptyMessageDTO.setMessage("No travel bookings found");
            return List.of(emptyMessageDTO);
        }

        return travelBookings.stream()
                        .map(this::convertToDTO)
                        .toList();
    }

    @Override
    public List<TravelBookingConfirmationDTO> getAllUpcomingBookings() {
        List<TravelBooking> bookings = bookingRepository.findByDepartureDateAfter(LocalDate.now());

        if(bookings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No active travel bookings found");
        }

        return travelBookingList(bookings);
    }

    @Override
    public List<TravelBookingConfirmationDTO> getAllPastBookings() {
        List<TravelBooking> bookings = bookingRepository.findByDepartureDateBefore(LocalDate.now());

        if(bookings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No active travel bookings found");
        }

        return travelBookingList(bookings);
    }

    @Override
    public List<TravelBookingConfirmationDTO> getCancelledTravelBookings() {
        List<TravelBooking> bookings = bookingRepository.findByCancelledTrue();

        if(bookings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No active travel bookings found");
        }

        return travelBookingList(bookings);
    }
}
