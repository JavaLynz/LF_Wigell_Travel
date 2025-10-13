package org.example.lf_wigell_travel.controllers;

import org.example.lf_wigell_travel.dtos.TripDTO;
import org.example.lf_wigell_travel.entities.TravelBooking;
import org.example.lf_wigell_travel.repositories.TravelBookingRepository;
import org.example.lf_wigell_travel.services.TripserviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/wigelltravels/v1")
@PreAuthorize("hasRole('ADMIN')")
public class TravelAdminController {

    private final TravelBookingRepository travelBookingRepository;
    private final TripserviceImpl tripserviceImpl;

    @Autowired
    public TravelAdminController(TravelBookingRepository travelBookingRepository, TripserviceImpl tripserviceImpl){
        this.travelBookingRepository = travelBookingRepository;
        this.tripserviceImpl = tripserviceImpl;
    }

    @GetMapping("/listcancelled")
    public ResponseEntity<List<TravelBooking>> getCanceledBookings(){
        return ResponseEntity.ok(travelBookingRepository.findByCancelledTrue());
    }

    @GetMapping("/listupcoming")
    public ResponseEntity<List<TravelBooking>> getUpcomingBookings(){
        return ResponseEntity.ok(travelBookingRepository.findByDepartureDateAfter(LocalDate.now()));
    }

    @GetMapping("/listpast")
    public ResponseEntity<List<TravelBooking>> getPastBookings(){
        return ResponseEntity.ok(travelBookingRepository.findByDepartureDateBefore(LocalDate.now()));
    }

    @PostMapping("/addtravel")
    public ResponseEntity<TripDTO> addTrip(@RequestBody TripDTO trip){
        return ResponseEntity.status(HttpStatus.CREATED).body(tripserviceImpl.addTrip(trip));
    }

    @PutMapping("/updatetravel")
    public ResponseEntity<TripDTO> updateTrip(@RequestBody TripDTO trip){
        return ResponseEntity.ok(tripserviceImpl.updateTrip(trip));
    }

    @DeleteMapping("/removetravel/{id}")
    public ResponseEntity<String> removeTrip(@PathVariable Long id){
        return ResponseEntity.ok(tripserviceImpl.deleteTrip(id));
    }

}
