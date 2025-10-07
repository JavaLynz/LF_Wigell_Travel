package org.example.lf_wigell_travel.repositories;

import org.example.lf_wigell_travel.entities.TravelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TravelBookingRepository extends JpaRepository<TravelBooking, Long> {

    Optional<TravelBooking> findByBookingId(Long bookingId);
    List<TravelBooking> findByTravelCustomerAndCancelledFalse(String username);
    List<TravelBooking> findByCancelledTrue();
    List<TravelBooking> findByDepartureDateAfter(LocalDate dateAfter);
    List<TravelBooking> findByDepartureDateBefore(LocalDate dateBefore);
}
