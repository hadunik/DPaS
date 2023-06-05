package com.example.demo.repos

import com.example.demo.entity.FlightWithID
import com.example.demo.entity.Ticket
import com.example.demo.entity.TicketFlight
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TicketFlightsRepo:JpaRepository<TicketFlight, String> {
    fun countByFlightIdAndFareConditions(flightId: Int, fareConditions: String):Int

    @Query("select amount " +
                "from bookings.ticket_flights " +
                "where flight_id = :flightId and fare_conditions = :fareCondition order by amount limit 1",
        nativeQuery = true
    )
    fun knowPrice(@Param("flightId") flightId: Int, @Param("fareCondition") fareCondition: String): Int
}