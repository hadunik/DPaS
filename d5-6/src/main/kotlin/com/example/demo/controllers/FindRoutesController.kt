package com.example.demo.controllers

import com.example.demo.DTO.AirportDTO
import com.example.demo.DTO.AvailableRoutesDTO
import com.example.demo.entity.AvailableRoutes
import com.example.demo.repos.AirportRepo
import com.example.demo.repos.FlightWithIdRepo
import com.example.demo.repos.RouteRepo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat

@RestController
class FindRoutesController(
    val routeRepo: RouteRepo,
    val flightWithIDRepo: FlightWithIdRepo,
    val airportRepo: AirportRepo
) {

    @GetMapping("/{lang}/routes")
    fun findFlightsToDestination(
        @PathVariable lang: String,
        @RequestParam departure: String,
        @RequestParam arrival: String,
        @RequestParam fareCondition: String,
        @RequestParam dateInString: String,
        @RequestParam upperBound: Int
    ): List<List<AvailableRoutesDTO>> {
        val airportsDest : MutableList<String> = mutableListOf()
        if (departure.length != 3){
            for (airport in airportRepo.airportsbycity(departure, departure)) {
                airportsDest.add(airport.airportCode)
            }
        }else{
            airportsDest.add(departure)
        }
        val airportsArriv : MutableList<String> = mutableListOf()
        if (arrival.length != 3){
            for (airport in airportRepo.airportsbycity(arrival, arrival)) {
                airportsArriv.add(airport.airportCode)
            }
        }else{
            airportsArriv.add(arrival)
        }
        val allFlightsInMyDay = flightWithIDRepo.findAllByDateInString(dateInString)
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(dateInString)
        val format2 = SimpleDateFormat("u")
        var dayOfWeek = format2.format(date).toInt()
        if (dayOfWeek == 7) {
            dayOfWeek = 0
        }
        val neededFlights = routeRepo.findAll()
            .filter { it.daysOfWeek.contains(dayOfWeek) && it.fareCondition.contains(fareCondition) }
        val flightsWithDepartureAirports = neededFlights.filter { airportsDest.contains(it.departureAirport) }
        val res: MutableList<List<AvailableRoutes>> = mutableListOf()
        for (i in flightsWithDepartureAirports) {
            res.addAll(findRoute(neededFlights, listOf(i), airportsArriv, 0, upperBound))
        }
        return res.map {
            it.map {
                AvailableRoutesDTO(
                    allFlightsInMyDay.filter { j -> j.flightNo == it.flightNo }. map { j -> j.flightId }.first(),
                    it.flightNo,
                    it.scheduledDeparture,
                    it.scheduledArrival,
                    AirportDTO.convertToDTO(lang ,airportRepo.findByAirportCode(it.departureAirport)),
                    AirportDTO.convertToDTO(lang ,airportRepo.findByAirportCode(it.arrivalAirport)),
                    fareCondition,
                    dayOfWeek
                )
            }
        }
//        return findRoute(neededFlights).map {
//            AvialableRoutesDTO(
//                it.flightNo,
//                it.scheduledDeparture,
//                it.scheduledArrival,
//                it.departureAirport,
//                it.arrivalAirport,
//                fareCondition,
//                dayOfWeek
//            )
//        }
    }

    fun findRoute(
        source: List<AvailableRoutes>,
        path: List<AvailableRoutes>,
        to: List<String>,
        currentDepth: Int,
        upperDepth: Int
    ): List<List<AvailableRoutes>> {
        if (to.contains(path.last().arrivalAirport)) {
            return listOf(path)
        }
        if (currentDepth >= upperDepth) {
            return emptyList()
        }
        val mutableList: MutableList<List<AvailableRoutes>> = mutableListOf()
        source.filter {
            path.last().scheduledArrival < it.scheduledDeparture && path.last().arrivalAirport == it.departureAirport
        }.forEach {
            val tmp = path.toMutableList()
            tmp.add(it)
            val forFindPath = findRoute(source, tmp, to, currentDepth + 1, upperDepth)
            mutableList.addAll(forFindPath)
        }
        return mutableList
    }
}
