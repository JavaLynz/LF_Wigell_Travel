package org.example.lf_wigell_travel.services;

import org.example.lf_wigell_travel.dtos.TripDTO;
import org.example.lf_wigell_travel.entities.Trip;
import org.example.lf_wigell_travel.exceptions.ResourceNotFoundException;
import org.example.lf_wigell_travel.repositories.TripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripserviceImpl implements TripService{

    private final TripRepository tripRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TripserviceImpl.class);

    public TripserviceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public ResponseEntity<String> deleteTrip(Long id) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);

        Trip tripToDelete = optionalTrip.orElseThrow(() ->
                new ResourceNotFoundException("Trip", "id", id)
        );

            tripRepository.delete(tripToDelete);
            LOGGER.info("Trip with id {} deleted", id);
            return ResponseEntity.ok("Booking with id " + id + " has been deleted successfully");
    }

    @Override
    public ResponseEntity<TripDTO> addTrip(TripDTO trip) {
        Trip newTrip = new Trip();
        newTrip.setDestinationCity(trip.getDestinationCity());
        newTrip.setDestinationCountry(trip.getDestinationCountry());
        newTrip.setHotelName(trip.getHotelName());
        newTrip.setTripPricePerWeekSEK(trip.getTripPricePerWeekSEK());

        tripRepository.save(newTrip);

        LOGGER.info("New trip added");
        TripDTO confirmation = getTripDTO(newTrip.getTripId(), "New trip successfully created.");
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmation);
    }

    public TripDTO getTripDTO(Long tripId, String message) {

        Trip trip = tripRepository.findById(tripId).orElseThrow(() ->
                new ResourceNotFoundException("Trip", "id", tripId)
        );

            TripDTO dto = new TripDTO();
            dto.setDestinationCity(trip.getDestinationCity());
            dto.setDestinationCountry(trip.getDestinationCountry());
            dto.setHotelName(trip.getHotelName());
            dto.setTripPricePerWeekSEK(trip.getTripPricePerWeekSEK());
            dto.setTripId(tripId);
            dto.setMessage(message);

            return dto;
        }

    public TripDTO convertToDTO(Trip trip) {
        return getTripDTO(trip.getTripId(), null);
    }

    @Override
    public ResponseEntity<List<TripDTO>> getAllTrips() {


        return ResponseEntity.ok(tripRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<TripDTO> updateTrip(TripDTO trip) {
        Optional<Trip> optionalTrip = tripRepository.findById(trip.getTripId());

        Trip tripToUpdate = optionalTrip.orElseThrow(() ->
                new ResourceNotFoundException("Trip", "id", trip.getTripId())
        );

        if(!(trip.getDestinationCity() == null)){
            tripToUpdate.setDestinationCity(trip.getDestinationCity());
        }

        if(!(trip.getDestinationCountry() == null)){
            tripToUpdate.setDestinationCountry(trip.getDestinationCountry());
        }

        if(!(trip.getHotelName() == null)){
            tripToUpdate.setHotelName(trip.getHotelName());
        }

        if(!(trip.getTripPricePerWeekSEK() == null)){
            tripToUpdate.setTripPricePerWeekSEK(trip.getTripPricePerWeekSEK());
        }

        Trip updatedTrip = tripRepository.save(tripToUpdate);

        LOGGER.info("Trip with id {} updated", trip.getTripId());
        return ResponseEntity.ok(getTripDTO(updatedTrip.getTripId(), "Trip with id: " +  updatedTrip.getTripId() + " successfully updated"));
    }

}
