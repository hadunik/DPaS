package com.example.demo.repos

import com.example.demo.entity.Airport
import com.example.demo.entity.Flight
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FlightRepo : JpaRepository<Flight, Integer> {
}