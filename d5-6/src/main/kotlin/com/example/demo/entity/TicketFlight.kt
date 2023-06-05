package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "ticket_flights")
class TicketFlight(
    @Id
    val ticketNo: String,
    @Column
    val flightId: Int,
    @Column
    val fareConditions: String,
    @Column
    val amount: Int
)