package com.example.demo.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinColumns
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.sql.Timestamp

@Entity
@Table(name = "flights")
class FlightWithID(
    @Id
    val flightId: Int,
    @Column
    val flightNo: String,
    @Column
    val scheduledDeparture: Timestamp,
    @Column
    val scheduledArrival: Timestamp,
    @Column
    val departureAirport: String,
    @Column
    val arrivalAirport: String,
    @Column
    val status:String,
    @Column
    val aircraftCode: String
)