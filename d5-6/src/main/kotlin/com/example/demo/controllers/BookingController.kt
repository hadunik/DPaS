package com.example.demo.controllers

import com.example.demo.DTO.TicketDTO
import com.example.demo.cast.JSONForBookingBody
import com.example.demo.entity.Booking
import com.example.demo.entity.Ticket
import com.example.demo.entity.TicketFlight
import com.example.demo.repos.BookingsRepo
import com.example.demo.repos.FlightWithIdRepo
import com.example.demo.repos.TicketFlightsRepo
import com.example.demo.repos.TicketRepo
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.sql.Timestamp
import java.util.*

@RestController
class BookingController(
    val flightWithIdRepo: FlightWithIdRepo,
    val ticketFlightsRepo: TicketFlightsRepo,
    val bookingsRepo: BookingsRepo,
    val ticketRepo: TicketRepo
) {
    //проверить что такой рейс есть и что статус не вылетел
    //посчитать все записи из ticket_flights по flight_id и fare_conditions
    //посчитать все возможные сидения по id
    //посчитать цену
    //новая строка в букинг
    //новая строка в тикетс
    //новая строка в тикетсфлайт
    @PostMapping("/{lang}/bookings")
    fun buyTickets(
        @PathVariable lang: String,
        @RequestBody json: JSONForBookingBody
//    ): Int {
    ):TicketDTO{
        val price = mutableListOf<Int>()
        for (flight in json.flights) {
            val existFlight = flightWithIdRepo.findByFlightId(flight.id)
                ?: throw Exception("Flight with id $json.flightId doesn't exist")
            if (existFlight.status != "Scheduled") {
                throw Exception("Flight already arrived")
            }
            val availableSeats = flightWithIdRepo.countAvailableSeats(flight.condition, flight.id)
            if (availableSeats == 0) {
                throw Exception("we don't have enough seats")
            }
            price.add(ticketFlightsRepo.knowPrice(flight.id, flight.condition))
        }
        // if we have enough seats then we can book the flight
        // booking_ref is in 0085C7 this format
        var bookingRef = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase()
        while (bookingsRepo.checkUniqBookRef(bookingRef) != 0) {
            bookingRef = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase()
        }
        bookingsRepo.save(Booking(bookingRef, Timestamp(System.currentTimeMillis()), price.sum()))

        val ticketNo: String = UUID.randomUUID().toString().replace("-", "").substring(0, 13)
        ticketRepo.save(Ticket(ticketNo, bookingRef, json.passportData, json.name, json.contactData))
        // ticket_no is in 0005432000988 this format
        var i = 0
        for (flight in json.flights) {
            ticketFlightsRepo.save(TicketFlight(ticketNo, flight.id, flight.condition, price[i]))
            i++
        }

        return TicketDTO(bookingRef, ticketNo)
    }
}