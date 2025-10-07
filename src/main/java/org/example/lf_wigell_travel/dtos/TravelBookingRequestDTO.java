package org.example.lf_wigell_travel.dtos;

import java.time.LocalDate;

public class BookingRequestDTO {
    private Long tripId;
    private LocalDate departureDate;
    private Long numberOfWeeks;

    public BookingRequestDTO(Long tripId, LocalDate departureDate, Long numberOfWeeks) {
        this.tripId = tripId;
        this.departureDate = departureDate;
        this.numberOfWeeks = numberOfWeeks;
    }

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

    @Override
    public String toString() {
        return "BookingRequest{" +
                "tripId=" + tripId +
                ", departureDate=" + departureDate +
                ", numberOfWeeks=" + numberOfWeeks +
                '}';
    }
}
