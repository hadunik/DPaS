package com.example.demo.repos

import com.example.demo.entity.BoardingPasses
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CheckInRepo:JpaRepository<BoardingPasses, String> {
    fun existsByFlightIdAndSeatNo(flightId: Int, seatNo : String):Boolean

    fun countByFlightId(flightId : Int) : Int

    @Query("select s.additional_money " +
            "from flights f inner join seats s on f.aircraft_code = s.aircraft_code " +
            "where f.flight_id = :flightId and s.seat_no = :seatNo ",
        nativeQuery = true
    )
    fun checkOnAdditionalMoney(@Param("flightId") flightId : Int,@Param("seatNo") seatNo : String) : Boolean

    @Query("select max(tmp.amount) - min(tmp.amount) " +
            "from d4 tmp " +
            "where tmp.flight_no = (select f.flight_no from flights f where f.flight_id = :flightId) and tmp.fare_conditions = 'Economy' ",
        nativeQuery = true)
    fun getAdditionalMoney(@Param("flightId") flightId : Int) : Int
}