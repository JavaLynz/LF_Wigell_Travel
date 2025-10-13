package org.example.lf_wigell_travel.dtos;

import java.time.LocalDate;

public class TravelBookingRequestDTO {
    private Long bookingId;
    private Long tripId;
    private LocalDate departureDate;
    private Long numberOfWeeks;

    public TravelBookingRequestDTO(Long bookingId, Long tripId, LocalDate departureDate, Long numberOfWeeks) {
        this.bookingId = bookingId;
        this.tripId = tripId;
        this.departureDate = departureDate;
        this.numberOfWeeks = numberOfWeeks;
    }

    public TravelBookingRequestDTO() {}


    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public Long getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public void setNumberOfWeeks(Long numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        return "TravelBookingRequestDTO{" +
                "bookingId=" + bookingId +
                ", tripId=" + tripId +
                ", departureDate=" + departureDate +
                ", numberOfWeeks=" + numberOfWeeks +
                '}';
    }
}
