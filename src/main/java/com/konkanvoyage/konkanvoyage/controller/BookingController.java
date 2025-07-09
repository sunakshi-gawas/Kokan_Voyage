package com.konkanvoyage.konkanvoyage.controller;

import com.konkanvoyage.konkanvoyage.model.Booking;
import com.konkanvoyage.konkanvoyage.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("booking", new Booking());
        return "index";
    }

    @PostMapping("/book")
    public String saveBooking(@ModelAttribute Booking booking, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        booking.setUsername(auth.getName());
        booking.setCreatedAt(LocalDateTime.now());
        bookingRepository.save(booking);
        return "redirect:/ticket/" + booking.getId() + "?success=true";
    }

    @GetMapping("/ticket/{id}")
    public String viewTicket(@PathVariable Long id, Model model) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        model.addAttribute("booking", booking);
        return "ticket";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Booking> bookings = bookingRepository.findByUsername(username);
        model.addAttribute("bookings", bookings);
        return "user-dashboard";
    }

    @GetMapping("/user/cancel/{id}")
    public String cancelUserBooking(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null && booking.getUsername().equals(username)) {
            booking.setStatus("Cancelled");
            bookingRepository.save(booking);
        }

        return "redirect:/user/dashboard";
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
