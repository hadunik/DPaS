package com.example.demo.DTO

import com.example.demo.entity.Airport

class AirportDTO(
    val airportCode: String,
    val airportName: String,
    val city: String,
    val timezone: String
) {
    companion object {
        fun convertToDTO(lang: String, airport: Airport): AirportDTO {
            val dto = AirportDTO(
                airportCode = airport.airportCode,
                airportName = if (lang == "en") {
                    airport.airportName.en
                } else {
                    airport.airportName.ru
                },
                city = if (lang == "en") {
                    airport.city.en
                } else {
                    airport.city.ru
                },
                timezone = airport.timezone
                )
            return dto
        }
    }
}