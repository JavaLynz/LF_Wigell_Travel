package org.example.lf_wigell_travel.controllers;


import org.example.lf_wigell_travel.dtos.TravelBookingConfirmationDTO;
import org.example.lf_wigell_travel.dtos.TravelBookingRequestDTO;
import org.example.lf_wigell_travel.services.TravelBookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/wigelltravels/v1")
public class TravelCustomerController {

    private final TravelBookingServiceImpl travelBookingServiceImpl;

    @Autowired
    public TravelCustomerController(TravelBookingServiceImpl travelBookingServiceImpl) {
        this.travelBookingServiceImpl = travelBookingServiceImpl;
    }

    @PostMapping("/booktrip")
    public ResponseEntity<?> bookTrip(@RequestBody TravelBookingRequestDTO request){
        return ResponseEntity.ok(travelBookingServiceImpl.newTravelBooking(request));
    }

    @PutMapping("/canceltrip")
    public ResponseEntity<TravelBookingConfirmationDTO> cancelTrip(@RequestBody TravelBookingRequestDTO tripToCancel){
        if (tripToCancel.getBookingId() == null) {
            TravelBookingConfirmationDTO dto = new TravelBookingConfirmationDTO();
            dto.setMessage("Booking ID is required");
            return ResponseEntity.badRequest().body(dto);
        }

        return travelBookingServiceImpl.cancelTravelBooking(tripToCancel.getBookingId());
    }

    @GetMapping("mybookings")
    public ResponseEntity<?> getAllBookings(String username){
        return ResponseEntity.ok(travelBookingServiceImpl.getAllTravelBookings(username));
    }

}
