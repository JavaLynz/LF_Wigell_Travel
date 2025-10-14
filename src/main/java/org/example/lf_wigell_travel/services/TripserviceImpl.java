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
import org.springframework.web.server.ResponseStatusException;

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
    public String deleteTrip(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip ID is required for deletion");
        }

        Optional<Trip> optionalTrip = tripRepository.findById(id);

        Trip tripToDelete = optionalTrip.orElseThrow(() ->
                new ResourceNotFoundException("Trip", "id", id)
        );

            tripRepository.delete(tripToDelete);
            LOGGER.info("Trip with id {} deleted", id);
            return "Booking with id " + id + " has been deleted successfully";
    }

    @Override
    public TripDTO addTrip(TripDTO trip) {

        if(trip.getDestinationCity()==null || trip.getDestinationCity().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required details: Destination City is a required field");
        }

        if(trip.getDestinationCountry()==null || trip.getDestinationCountry().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required details: Destination Country is a required field");
        }

        if(trip.getHotelName()==null || trip.getHotelName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required details: Hotel name is a required field");
        }

        if(trip.getTripPricePerWeekSEK()==null || trip.getTripPricePerWeekSEK().equals(0)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required details: Trip price per week is a required field");
        }


        Optional<Trip> existingTrip = tripRepository.findByDestinationCityAndDestinationCountryAndHotelNameAndTripPricePerWeekSEK(
                trip.getDestinationCity(),
                trip.getDestinationCountry(),
                trip.getHotelName(),
                trip.getTripPricePerWeekSEK()
        );

        if (existingTrip.isPresent()) {
            Trip duplicate = existingTrip.get();
            LOGGER.info("Duplicate trip found: id={}", duplicate.getTripId());

            TripDTO existingTripDTO = convertToTripDTO(duplicate, "Trip already exists in the database");


            throw new ResponseStatusException(HttpStatus.CONFLICT, existingTripDTO.toString());
        }

        Trip newTrip = new Trip();
        newTrip.setDestinationCity(trip.getDestinationCity());
        newTrip.setDestinationCountry(trip.getDestinationCountry());
        newTrip.setHotelName(trip.getHotelName());
        newTrip.setTripPricePerWeekSEK(trip.getTripPricePerWeekSEK());

        tripRepository.save(newTrip);

        LOGGER.info("New trip added by Admin");

        return convertToTripDTO(newTrip, "New trip successfully created.");
    }

    public TripDTO convertToTripDTO(Trip trip, String message) {
        if (trip == null) {
            throw new ResourceNotFoundException("Trip", "Trip object", null);
        }

        TripDTO dto = new TripDTO();
        dto.setDestinationCity(trip.getDestinationCity());
        dto.setDestinationCountry(trip.getDestinationCountry());
        dto.setHotelName(trip.getHotelName());
        dto.setTripPricePerWeekSEK(trip.getTripPricePerWeekSEK());
        dto.setTripId(trip.getTripId());
        dto.setMessage(message);
        return dto;

        }

    public TripDTO convertToTripDTO(Trip trip) {
        return convertToTripDTO(trip, null);
    }

    @Override
    public List<TripDTO> getAllTrips() {


        return (tripRepository.findAll()
                .stream()
                .map(this::convertToTripDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public TripDTO updateTrip(TripDTO trip) {
        if (trip.getTripId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip ID is required for update");
        }


        Optional<Trip> optionalTrip = tripRepository.findById(trip.getTripId());

        Trip tripToUpdate = optionalTrip.orElseThrow(() ->
                new ResourceNotFoundException("Trip", "id", trip.getTripId())
        );

        if(trip.getDestinationCity() != null){
            tripToUpdate.setDestinationCity(trip.getDestinationCity());
        }

        if(trip.getDestinationCountry() != null){
            tripToUpdate.setDestinationCountry(trip.getDestinationCountry());
        }

        if(trip.getHotelName() != null){
            tripToUpdate.setHotelName(trip.getHotelName());
        }

        if(trip.getTripPricePerWeekSEK() != null){
            tripToUpdate.setTripPricePerWeekSEK(trip.getTripPricePerWeekSEK());
        }

        Trip updatedTrip = tripRepository.save(tripToUpdate);

        LOGGER.info("Trip with id {} updated", trip.getTripId());
        return convertToTripDTO(updatedTrip, "Trip with id: " +  updatedTrip.getTripId() + " successfully updated");
    }

}
