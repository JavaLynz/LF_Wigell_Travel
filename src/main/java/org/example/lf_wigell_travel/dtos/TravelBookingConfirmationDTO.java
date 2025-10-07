package org.example.lf_wigell_travel.dtos;

import org.example.lf_wigell_travel.entities.TravelBooking;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TravelBookingConfirmationDTO {
    private Long bookingId;
    private String customerName;
    private Long tripId;
    private String destinationCity;
    private String destinationCountry;
    private String hotelName;
    private LocalDate departureDate;
    private Long durationWeeks;
    private BigDecimal totalPriceSek;
    private BigDecimal totalPriceEur;
    private String message;
    private boolean cancelled;

    public TravelBookingConfirmationDTO() {}

    public TravelBookingConfirmationDTO(TravelBooking travelBooking, String message) {
        this.bookingId = travelBooking.getBookingId();
        this.customerName = travelBooking.getTravelCustomer();
        this.tripId = travelBooking.getTrip().getTripId();
        this.destinationCity = travelBooking.getTrip().getDestinationCity();
        this.destinationCountry = travelBooking.getTrip().getDestinationCountry();
        this.hotelName = travelBooking.getTrip().getHotelName();
        this.departureDate = travelBooking.getDepartureDate();
        this.durationWeeks = travelBooking.getDurationWeeks();
        this.totalPriceSek = travelBooking.getTotalPriceSEK();
        this.message = message;
        this.cancelled = travelBooking.isCancelled();
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public Long getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(Long durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    public BigDecimal getTotalPriceSek() {
        return totalPriceSek;
    }

    public void setTotalPriceSek(BigDecimal totalPriceSek) {
        this.totalPriceSek = totalPriceSek;
    }

    public BigDecimal getTotalPriceEur() {
        return totalPriceEur;
    }

    public void setTotalPriceEur(BigDecimal totalPriceEur) {
        this.totalPriceEur = totalPriceEur;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public String toString() {
        return "TravelBookingConfirmationDTO{" +
                "bookingId=" + bookingId +
                ", customerName='" + customerName + '\'' +
                ", tripId=" + tripId +
                ", destinationCity='" + destinationCity + '\'' +
                ", destinationCountry='" + destinationCountry + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", departureDate=" + departureDate +
                ", durationWeeks=" + durationWeeks +
                ", totalPriceSek=" + totalPriceSek +
                ", totalPriceEur=" + totalPriceEur +
                ", message='" + message + '\'' +
                ", cancelled=" + cancelled +
                '}';
    }
}
