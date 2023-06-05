package com.example.demo.repos

import com.example.demo.entity.FlightWithID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FlightWithIdRepo : JpaRepository<FlightWithID, Int> {
    fun findByFlightId(flightId : Int): FlightWithID?

    fun findAllByFlightNo(flightNo : String): List<FlightWithID>

    @Query(value = "SELECT * " +
            "from flights f " +
            "where to_date(:dateInString, 'YYYY-MM-DD') = f.scheduled_departure \\:\\:timestamp \\:\\:date  ", nativeQuery = true)
    fun findAllByDateInString(@Param("dateInString") dateInString: String):List<FlightWithID>

    @Query(
        "select count(*) - " +
                " (select count(*) from bookings.ticket_flights tf where tf.fare_conditions = :fareCondition and tf.flight_id = :flightId) " +
                " from bookings.seats s2 " +
                " where s2.aircraft_code = (select aircraft_code from bookings.flights f where f.flight_id = :flightId)",
        nativeQuery = true
    )
    fun countAvailableSeats(@Param("fareCondition") fareCondition : String, @Param("flightId") flightId: Int):Int

}