package com.konkanvoyage.konkanvoyage.repository;

import com.konkanvoyage.konkanvoyage.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // ✅ REQUIRED!

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUsername(String username);
}
