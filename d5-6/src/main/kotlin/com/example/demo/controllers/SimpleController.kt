package com.example.demo.controllers

import com.example.demo.DTO.AirportDTO
import com.example.demo.DTO.CItyDTO
import com.example.demo.repos.AirportRepo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

//@RequestMapping("/demo")
@RestController
class SimpleController(
    val airportRepo: AirportRepo
) {

    @GetMapping("/{lang}/airports")
    fun getAllAirports(@PathVariable lang: String): List<AirportDTO> {
        return airportRepo.findAll().map {
            AirportDTO(
                it.airportCode,
                if (lang == "en") {
                    it.airportName.en
                } else {
                    it.airportName.ru
                },
                if (lang == "en") {
                    it.city.en
                } else {
                    it.city.ru
                },
                it.timezone
            )
        }
    }

    @GetMapping("/{lang}/cities")
    fun getUniqCity(@PathVariable lang: String): List<CItyDTO> {
        return airportRepo.getDistinctCity().map {
            CItyDTO(
                if (lang == "en") {
                    it.en
                } else {
                    it.ru
                }
            )
        }
    }

    @GetMapping("/{lang}/airports/incity")
    fun getAirportsInThisCity(@PathVariable lang: String, @RequestParam city: String): List<AirportDTO> {
        return airportRepo.findAll().filter { it.city.ru == city || it.city.en == city }.map {
            AirportDTO(
                it.airportCode,
                if (lang == "en") {
                    it.airportName.en
                } else {
                    it.airportName.ru
                },
                if (lang == "en") {
                    it.city.en
                } else {
                    it.city.ru
                },
                it.timezone
            )
        }
//        return airportRepo.getAirportByCity(city)
    }
}