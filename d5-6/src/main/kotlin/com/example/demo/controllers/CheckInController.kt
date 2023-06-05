package com.example.demo.controllers

import com.example.demo.DTO.CheckInDTO
import com.example.demo.cast.JSONForBookingBody
import com.example.demo.cast.JSONForCheckinBody
import com.example.demo.entity.BoardingPasses
import com.example.demo.repos.CheckInRepo
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CheckInController(
    val checkInRepo: CheckInRepo
) {
    @PostMapping("/{lang}/checkin")
    fun checkIn(
        @PathVariable lang: String,
        @RequestBody json: JSONForCheckinBody
    ) : CheckInDTO {
//    ): Boolean {
        val boardingNo = checkInRepo.countByFlightId(json.flightId) + 1
        val flag = checkInRepo.existsByFlightIdAndSeatNo(json.flightId, json.seatNumber)
        if (flag) {
            throw Exception("this seat not available")
        }
        val ans = checkInRepo.checkOnAdditionalMoney(json.flightId, json.seatNumber)
        var addMoney = 0
        if (ans) {
            addMoney = checkInRepo.getAdditionalMoney(json.flightId)
        }
        checkInRepo.save(BoardingPasses(json.ticketNo, json.flightId, boardingNo, json.seatNumber))
        return CheckInDTO(boardingNo, addMoney)
    }
}