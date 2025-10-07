package org.example.lf_wigell_travel.controllers;

import org.example.lf_wigell_travel.dtos.TripDTO;
import org.example.lf_wigell_travel.services.TripserviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/wigelltravels/v1")
@PreAuthorize("isAuthenticated()")
public class TravelCombinedController {
    private final TripserviceImpl tripService;

    @Autowired
    public TravelCombinedController(final TripserviceImpl tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/travels")
    public ResponseEntity<List<TripDTO>> getAllTrips(){
        return tripService.getAllTrips();
    }
}
