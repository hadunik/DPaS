package com.example.demo.DTO

import com.example.demo.entity.Airport
import java.sql.Time

class FlightDTO (
    val daysOfTheWeek: IntArray,
    val flightNo: String,
    val departureAirport: AirportDTO,
    val arrivalAirport: AirportDTO,
    val departureTime: Time,
    val arrivalTime: Time
)