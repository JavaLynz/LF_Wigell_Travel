package org.example.lf_wigell_travel.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class TravelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length=25, nullable=false)
    private Long bookingId;

    @Column(length=75, nullable = false)
    private String travelCustomer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "trip_id",nullable = true)
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private Trip trip;

    @Column(length=100, nullable = false)
    private LocalDate departureDate;

    @Column(length=100, nullable = false)
    private Long durationWeeks;

    @Column(length=25, nullable = false)
    private boolean cancelled;

    @Column(length=50, nullable = false)
    private BigDecimal totalPriceSEK;


    public TravelBooking() {}

    public TravelBooking(String travelCustomer, LocalDate departureDate, Long durationWeeks) {
        this.travelCustomer = travelCustomer;
        this.departureDate = departureDate;
        this.durationWeeks = durationWeeks;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long id) {
        this.bookingId = id;
    }

    public String getTravelCustomer() {
        return travelCustomer;
    }

    public void setTravelCustomer(String travelCustomer) {
        this.travelCustomer = travelCustomer;
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

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public BigDecimal getTotalPriceSEK() {
        return totalPriceSEK;
    }

    public void setTotalPriceSEK(BigDecimal totalPriceSEK) {
        this.totalPriceSEK = totalPriceSEK;
    }


    @Override
    public String toString() {
        return "TravelBooking{" +
                "id=" + bookingId +
                ", travelCustomer=" + travelCustomer +
                ", departureDate=" + departureDate +
                ", durationDays=" + durationWeeks +
                ", cancelled=" + cancelled +
                ", trip=" + trip +
                ", totalPriceSEK=" + totalPriceSEK +
                '}';
    }
}
