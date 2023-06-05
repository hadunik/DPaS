package com.example.demo.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.type.descriptor.java.IntegerJavaType
import org.hibernate.type.descriptor.jdbc.IntegerJdbcType
import java.sql.Time
import java.time.Instant

@Entity
@Table(name = "for_schedule")
class Flight (
    @Column(name = "days_of_week")
    val daysOfTheWeek: IntArray,
    @Id
    val flightNo: String,
    @ManyToOne
    @JoinColumn(name = "departure_airport")
    val departureAirport: Airport,
    @ManyToOne
    @JoinColumn(name = "arrival_airport")
    val arrivalAirport: Airport,
    @Column
    val departureTime: Time,
    @Column
    val arrivalTime: Time

    )