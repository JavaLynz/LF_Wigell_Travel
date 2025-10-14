package org.example.lf_wigell_travel.services;

import org.example.lf_wigell_travel.dtos.TripDTO;
import org.example.lf_wigell_travel.entities.Trip;
import org.example.lf_wigell_travel.exceptions.ResourceNotFoundException;
import org.example.lf_wigell_travel.repositories.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class TripServiceImplTest {

    private TripRepository tripRepository;
    private TripserviceImpl tripService;
    private Trip trip;
    private TripDTO tripDTO;
    private TripDTO invalidTripDTO;

    private static final Long TRIP_ID = 7L;
    private static final BigDecimal PRICE = BigDecimal.valueOf(6000.00);
    private static final String HOTEL = "Quality Matchstick Hotel";
    private static final String CITY = "Jönköping";
    private static final String COUNTRY = "Sweden";

@BeforeEach
    public void setUp() {
        tripRepository = mock(TripRepository.class);
        tripService =new TripserviceImpl(tripRepository);
        trip = new Trip(PRICE, HOTEL, CITY, COUNTRY);
        trip.setTripId(TRIP_ID);

        tripDTO = new TripDTO();
        tripDTO.setTripId(TRIP_ID);
        tripDTO.setDestinationCity(CITY);
        tripDTO.setDestinationCountry(COUNTRY);
        tripDTO.setHotelName(HOTEL);
        tripDTO.setTripPricePerWeekSEK(PRICE);

        invalidTripDTO = new TripDTO();
        invalidTripDTO.setDestinationCity(null);
        invalidTripDTO.setDestinationCountry(null);
        invalidTripDTO.setHotelName(null);
        invalidTripDTO.setTripPricePerWeekSEK(null);

}

@Test
void addTripShouldSucceedWhenValid(){
    when(tripRepository.save(any(Trip.class))).thenAnswer(inv -> {
        Trip t = inv.getArgument(0);
        t.setTripId(TRIP_ID);
        return t;
    });
    when(tripRepository.findById(trip.getTripId())).thenReturn(Optional.of(trip));

    TripDTO confirmation = tripService.addTrip(tripService.convertToTripDTO(trip));

    assertThat(confirmation).isNotNull();
    assertThat(confirmation.getTripId()).isEqualTo(trip.getTripId());
    assertThat(confirmation.getDestinationCity()).isEqualTo(trip.getDestinationCity());
    assertThat(confirmation.getDestinationCountry()).isEqualTo(trip.getDestinationCountry());
    assertThat(confirmation.getHotelName()).isEqualTo(trip.getHotelName());
    assertThat(confirmation.getTripPricePerWeekSEK()).isEqualTo(trip.getTripPricePerWeekSEK());

    verify(tripRepository, times(1)).save(any(Trip.class));

}

@Test
    void addTripShouldThrowExceptionWhenMissingDetails(){
    trip.setTripPricePerWeekSEK(BigDecimal.ZERO);

    when(tripRepository.save(any(Trip.class))).thenAnswer(inv -> {
        Trip t = inv.getArgument(0);
        t.setTripId(TRIP_ID);
        return t;
    });

    assertThatThrownBy(()->tripService.addTrip(invalidTripDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Missing required details");

}

@Test
void getTripDTOShouldSucceedWhenTripExists(){

    TripDTO dto = tripService.convertToTripDTO(trip);

    assertThat(dto).isNotNull();
    assertThat(dto.getTripId()).isEqualTo(trip.getTripId());
    assertThat(dto.getDestinationCity()).isEqualTo(trip.getDestinationCity());
    assertThat(dto.getDestinationCountry()).isEqualTo(trip.getDestinationCountry());
    assertThat(dto.getHotelName()).isEqualTo(trip.getHotelName());
    assertThat(dto.getTripPricePerWeekSEK()).isEqualTo(trip.getTripPricePerWeekSEK());
    assertThat(dto.getMessage()).isNull();

}

@Test
void getTripDTOShouldThrowExceptionWhenTripNotFound(){

    assertThatThrownBy(()->tripService.convertToTripDTO(null, "Trip not found"))
    .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Trip object");
}

@Test
    void getAllTripsShouldReturnAListOf6TripDTOs(){
        List<Trip> trips = List.of(
            new Trip(BigDecimal.valueOf(1000), "Hotel 1", "City 1", "Country 1"),
            new Trip(BigDecimal.valueOf(2000), "Hotel 2", "City 2", "Country 2"),
            new Trip(BigDecimal.valueOf(3000), "Hotel 3", "City 3", "Country 3"),
            new Trip(BigDecimal.valueOf(4000), "Hotel 4", "City 4", "Country 4"),
            new Trip(BigDecimal.valueOf(5000), "Hotel 5", "City 5", "Country 5"),
            new Trip(BigDecimal.valueOf(6000), "Hotel 6", "City 6", "Country 6")
    );
        when(tripRepository.findAll()).thenReturn(trips);

        List<TripDTO> expectedTrips = tripService.getAllTrips();

        assertThat(expectedTrips.size()).isEqualTo(6);
        verify(tripRepository, times(1)).findAll();

    }

    @Test
    void updateTripShouldSucceedWhenTripExists(){
        TripDTO updatedTripDTO = new TripDTO();
        updatedTripDTO.setTripId(trip.getTripId());
        updatedTripDTO.setDestinationCity("Gothenburg");
        updatedTripDTO.setHotelName("Elite Park Avenue");
        updatedTripDTO.setTripPricePerWeekSEK(BigDecimal.valueOf(7200));

        when(tripRepository.findById(trip.getTripId())).thenReturn(Optional.of(trip));
        when(tripRepository.save(any(Trip.class))).thenAnswer(inv -> inv.getArgument(0));


        TripDTO confirmation = tripService.updateTrip(updatedTripDTO);

        assertThat(confirmation).isNotNull();
        assertThat(confirmation.getTripId()).isEqualTo(trip.getTripId());
        assertThat(confirmation.getDestinationCity()).isEqualTo("Gothenburg");
        assertThat(confirmation.getHotelName()).isEqualTo("Elite Park Avenue");
        assertThat(confirmation.getTripPricePerWeekSEK()).isEqualByComparingTo(BigDecimal.valueOf(7200));
        assertThat(confirmation.getMessage()).contains("successfully updated");

        verify(tripRepository, times(1)).findById(trip.getTripId());
        verify(tripRepository, times(1)).save(any(Trip.class));

}

@Test
    void updateTripShouldThrowExceptionWhenTripNotFound(){
        TripDTO updatedTripDTO = new TripDTO();
        updatedTripDTO.setTripId(8L);

        when(tripRepository.findById(8L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->tripService.updateTrip(updatedTripDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Trip with id '8' not found");

        verify(tripRepository, times(1)).findById(8L);
        verify(tripRepository, never()).save(any(Trip.class));
}

@Test
    void updateTripShouldThrowExceptionWhenIdIsMissing(){
    TripDTO updatedTripDTO = new TripDTO();
    updatedTripDTO.setTripId(null);

    assertThatThrownBy(()->tripService.updateTrip(updatedTripDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Trip ID is required for update");

    verify(tripRepository, never()).findById(any());
    verify(tripRepository, never()).save(any());
}

@Test
    void deleteTripShouldSucceedWhenTripExists(){
    when(tripRepository.findById(trip.getTripId())).thenReturn(Optional.of(trip));

    String result = tripService.deleteTrip(trip.getTripId());

    assertThat(result).isEqualTo("Booking with id 7 has been deleted successfully");

    verify(tripRepository, times(1)).findById(trip.getTripId());
    verify(tripRepository,times(1)).delete(trip);

}

@Test
    void deleteTripShouldThrowExceptionWhenTripNotFound(){
        when(tripRepository.findById(trip.getTripId())).thenReturn(Optional.empty());

        assertThatThrownBy(()->tripService.deleteTrip(trip.getTripId()))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Trip with id '7' not found");

        verify(tripRepository, times(1)).findById(trip.getTripId());
        verify(tripRepository,never()).delete(any());
}

@Test
    void deleteTripShouldThrowExceptionWhenIdIsMissing(){
        assertThatThrownBy(()->tripService.deleteTrip(null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Trip ID is required for deletion");

}

}
