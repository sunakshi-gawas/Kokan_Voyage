package com.konkanvoyage.konkanvoyage.controller;

import com.konkanvoyage.konkanvoyage.model.Booking;
import com.konkanvoyage.konkanvoyage.repository.BookingRepository;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/admin/bookings")
    public String viewAllBookings(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        return "admin-bookings";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "redirect:/admin/bookings";
    }

    @GetMapping("/admin/cancel/{id}")
    public String cancelBooking(@PathVariable Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus("Cancelled"); // or add a "status" column
            bookingRepository.save(booking);
        }
        return "redirect:/admin/bookings";
    }

    @GetMapping("/admin/export/excel")
    public void exportBookings(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=bookings.xlsx");

        List<Booking> bookings = bookingRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bookings");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Email");

        int rowIdx = 1;
        for (Booking b : bookings) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(b.getId());
            row.createCell(1).setCellValue(b.getFullName());
            row.createCell(2).setCellValue(b.getEmail());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
