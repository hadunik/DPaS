package com.example.demo.controllers

import com.example.demo.DTO.AirportDTO
import com.example.demo.DTO.FlightDTO
import com.example.demo.repos.FlightRepo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.sql.Date

@RestController
class ScheduleController(
    val flightRepo: FlightRepo
) {

    @GetMapping("/{lang}/airports/inbound")
    fun getInboubdFlights(@PathVariable lang: String, @RequestParam airportInboundName: String): List<FlightDTO> {
        return flightRepo.findAll().filter { it.arrivalAirport.airportCode == airportInboundName }.map {
            FlightDTO(
                it.daysOfTheWeek,
                it.flightNo,
                AirportDTO.convertToDTO(lang, it.departureAirport),
                AirportDTO.convertToDTO(lang, it.arrivalAirport),
                it.departureTime,
                it.arrivalTime
            )
        }
    }

    @GetMapping("/{lang}/airports/outbound")
    fun getOutboundFlights(@PathVariable lang: String, @RequestParam airportInboundName: String): List<FlightDTO> {
        return flightRepo.findAll().filter { it.departureAirport.airportCode == airportInboundName }.map {
            FlightDTO(
                it.daysOfTheWeek,
                it.flightNo,
                AirportDTO.convertToDTO(lang, it.departureAirport),
                AirportDTO.convertToDTO(lang, it.arrivalAirport),
                it.departureTime,
                it.arrivalTime
            )
        }
    }

//    @GetMapping("/{lang}/flights")
//    fun findFlightsToDestination(@PathVariable lang: String,
//                                 @RequestParam departureAirport: String,
//                                 @RequestParam arrivalAirport: String,
//                                 @RequestParam fareCondition: String,
//                                 @RequestParam date: Date,
//                                 @RequestParam upperBound: Int): List<FlightDTO>{
//
//    }
}

