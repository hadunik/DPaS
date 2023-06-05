package com.example.demo.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.sql.Time

@Entity
@Table(name = "for_find_routes")
class AvailableRoutes (
    @Id
    val flightNo: String,
    @Column
    val scheduledDeparture: Time,
    @Column
    val scheduledArrival: Time,
    @Column
    val departureAirport: String,
    @Column
    val arrivalAirport: String,
    @JdbcTypeCode(SqlTypes.ARRAY)
    val fareCondition: Array<String>,
    @Column
    val daysOfWeek: IntArray
    ){
}