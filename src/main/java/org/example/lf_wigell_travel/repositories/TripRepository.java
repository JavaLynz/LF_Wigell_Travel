package org.example.lf_wigell_travel.repositories;

import org.example.lf_wigell_travel.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;


public interface TripRepository extends JpaRepository<Trip, Long> {
    boolean existsByDestinationCityAndDestinationCountryAndHotelNameAndTripPricePerWeekSEK(
            String destinationCity,
            String destinationCountry,
            String hotelName,
            BigDecimal tripPricePerWeekSEK
    );

    Optional<Trip> findByDestinationCityAndDestinationCountryAndHotelNameAndTripPricePerWeekSEK(
            String destinationCity,
            String destinationCountry,
            String hotelName,
            BigDecimal tripPricePerWeekSEK
    );
}
