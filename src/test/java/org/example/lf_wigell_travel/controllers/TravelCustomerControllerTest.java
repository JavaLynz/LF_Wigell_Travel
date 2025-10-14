package org.example.lf_wigell_travel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lf_wigell_travel.dtos.TravelBookingRequestDTO;
import org.example.lf_wigell_travel.entities.TravelBooking;
import org.example.lf_wigell_travel.entities.Trip;
import org.example.lf_wigell_travel.repositories.TravelBookingRepository;
import org.example.lf_wigell_travel.repositories.TripRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TravelCustomerControllerTest {

    @Autowired
    private TravelBookingRepository travelBookingRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TripRepository tripRepository;

    private ObjectMapper objectMapper;

    private Trip trip, trip1, trip2;

    private TravelBooking travelBooking1, travelBooking2, travelBooking3;

    @BeforeEach
    void setUp() {
        travelBookingRepository.deleteAll();
        tripRepository.deleteAll();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        trip = new Trip(BigDecimal.valueOf(10000.50),"Pleasure Paradise","Las Vegas","USA");
        trip1 = new Trip(BigDecimal.valueOf(5000.50),"Grand Curiosa","Gothenburg","SWEDEN");
        trip2 = new Trip(BigDecimal.valueOf(6000.50),"Four Seasons","Southampton","ENGLAND");

        travelBooking1 = new TravelBooking("martymcfly", trip, LocalDate.of(2025,10,19),2L);
        travelBooking1.setTotalPriceSEK(trip.getTripPricePerWeekSEK().multiply(BigDecimal.valueOf(2L)));
        travelBookingRepository.save(travelBooking1);

        travelBooking2 = new TravelBooking("martymcfly",trip1,LocalDate.of(2026,04,21),1L);
        travelBooking2.setTotalPriceSEK(trip1.getTripPricePerWeekSEK().multiply(BigDecimal.valueOf(1L)));
        travelBookingRepository.save(travelBooking2);

        travelBooking3 = new TravelBooking("martymcfly",trip2,LocalDate.of(2026,07,21),3L);
        travelBooking3.setTotalPriceSEK(trip2.getTripPricePerWeekSEK().multiply(BigDecimal.valueOf(3L)));
        travelBookingRepository.save(travelBooking3);

    }

    @Test
    @WithMockUser(username="martymcfly")
    void myBookingsShouldReturnListOf3Bookings() throws Exception {

        mockMvc.perform(get("/api/wigelltravels/v1/mybookings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerName").value("martymcfly"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].customerName").value("martymcfly"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].customerName").value("martymcfly"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));

    }

    @Test
    @WithMockUser(username="nobookingsuser")
    void myBookingsShouldThrowResponseStatusException() throws Exception{

        mockMvc.perform(get("/api/wigelltravels/v1/mybookings"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> Assertions.assertEquals("404 NOT_FOUND \"No active travel bookings found\"",
                        result.getResolvedException().getMessage()));


    }

    @Test
    @WithMockUser(username="martymcfly")
    void cancelTripShouldSucceed() throws Exception {

        TravelBookingRequestDTO requestDTO = new TravelBookingRequestDTO();
        requestDTO.setBookingId(travelBooking1.getBookingId());

        mockMvc.perform(put("/api/wigelltravels/v1/canceltrip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Travel booking with id: " + travelBooking1.getBookingId() + " has been successfully cancelled."))
                    .andExpect(jsonPath("$.cancelled").value(true));

    }

    @Test
    @WithMockUser
    void cancelTripShouldReturnBadRequestNoId() throws Exception {
        TravelBookingRequestDTO requestDTO = new TravelBookingRequestDTO();

        mockMvc.perform(put("/api/wigelltravels/v1/canceltrip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Booking ID is required"));
    }

    @Test
    @WithMockUser
    void cancelTripShouldReturnBadRequestCancellationWithin7Days() throws Exception {

        TravelBookingRequestDTO requestDTO = new TravelBookingRequestDTO();
        requestDTO.setBookingId(travelBooking1.getBookingId());

        mockMvc.perform(put("/api/wigelltravels/v1/canceltrip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(status().reason("Cancellation not allowed less than 7 days before departure date"));
    }

    @Test
    @WithMockUser
    void cancelTripShouldReturnResourceNotFoundException() throws Exception {

        TravelBookingRequestDTO requestDTO = new TravelBookingRequestDTO();
        requestDTO.setBookingId(999L);

        mockMvc.perform(put("/api/wigelltravels/v1/canceltrip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(username="martymcfly")
    void bookTripShouldSucceedWhenValid() throws Exception {
        TravelBookingRequestDTO requestDTO = new TravelBookingRequestDTO();
        requestDTO.setTripId(trip2.getTripId());
        requestDTO.setDepartureDate(LocalDate.of(2025,10,29));
        requestDTO.setNumberOfWeeks(3L);

        mockMvc.perform(post("/api/wigelltravels/v1/booktrip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.message").value("Booking successfully created."))
                    .andExpect(jsonPath("$.tripId").value(trip2.getTripId()));

    }

    @Test
    @WithMockUser
    void bookTripShouldReturnResourceNotFoundException() throws Exception {
        TravelBookingRequestDTO requestDTO = new TravelBookingRequestDTO();
        requestDTO.setTripId(55L);
        requestDTO.setDepartureDate(LocalDate.of(2025,12,9));
        requestDTO.setNumberOfWeeks(1L);

        mockMvc.perform(post("/api/wigelltravels/v1/booktrip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isNotFound());


    }


}
