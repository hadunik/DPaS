package com.example.demo.DTO

import java.sql.Time

class AvailableRoutesDTO(
    val flightId: Int, //TODO добавить из flights
    val flightNo: String,
    val scheduledDeparture: Time,
    val arrivalDeparture: Time,
    val departureAirport: AirportDTO,
    val arrivalAirport: AirportDTO,
    val fareCondition: String,
    val dayOfWeek: Int
) {
}