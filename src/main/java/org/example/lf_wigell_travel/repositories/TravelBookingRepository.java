package org.example.lf_wigell_travel.repositories;

import org.example.lf_wigell_travel.entities.TravelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelBookingRespository extends JpaRepository<TravelBooking, Long> {

    Optional<TravelBooking> findByBookingId(Long bookingId);
}
