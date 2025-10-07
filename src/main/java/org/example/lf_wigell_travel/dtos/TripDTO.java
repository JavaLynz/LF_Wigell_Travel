package org.example.lf_wigell_travel.dtos;

import java.math.BigDecimal;

public class TripDTO {
    private Long tripId;
    private BigDecimal tripPricePerWeekSEK;
    private String hotelName;
    private String destinationCity;
    private String destinationCountry;
    private String message;

    public TripDTO() {}

    public TripDTO(Long tripId, BigDecimal tripPricePerWeekSEK, String hotelName, String destinationCity, String destinationCountry) {
        this.tripId = tripId;
        this.tripPricePerWeekSEK = tripPricePerWeekSEK;
        this.hotelName = hotelName;
        this.destinationCity = destinationCity;
        this.destinationCountry = destinationCountry;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public BigDecimal getTripPricePerWeekSEK() {
        return tripPricePerWeekSEK;
    }

    public void setTripPricePerWeekSEK(BigDecimal tripPricePerWeekSEK) {
        this.tripPricePerWeekSEK = tripPricePerWeekSEK;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TripDTO{" +
                "TripId=" + tripId +
                ", tripPricePerWeekSEK=" + tripPricePerWeekSEK +
                ", hotelName='" + hotelName + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", destinationCountry='" + destinationCountry + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
