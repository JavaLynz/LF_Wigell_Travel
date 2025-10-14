package org.example.lf_wigell_travel.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(length=25, nullable=false)
    private Long tripId;

    @Column(precision = 10, scale = 2, nullable=false)
    private BigDecimal tripPricePerWeekSEK;

    @Column(length=75, nullable = false)
    private String hotelName;

    @Column(length=75, nullable = false)
    private String destinationCity;

    @Column(length=75, nullable = false)
    private String destinationCountry;

    public Trip() {}

    public Trip(BigDecimal tripPricePerWeekSEK, String hotelName, String destinationCity, String destinationCountry) {
        this.tripPricePerWeekSEK = tripPricePerWeekSEK;
        this.hotelName = hotelName;
        this.destinationCity = destinationCity;
        this.destinationCountry = destinationCountry;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long id) {
        this.tripId = id;
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

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + tripId +
                ", tripPricePerWeekSEK=" + tripPricePerWeekSEK +
                ", hotelName='" + hotelName + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", destinationCountry='" + destinationCountry + '\'' +
                '}';
    }
}
