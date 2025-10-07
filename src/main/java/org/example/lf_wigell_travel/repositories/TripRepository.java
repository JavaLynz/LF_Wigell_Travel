package org.example.lf_wigell_travel.repositories;

import org.example.lf_wigell_travel.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TripRepository extends JpaRepository<Trip, Long> {

}
