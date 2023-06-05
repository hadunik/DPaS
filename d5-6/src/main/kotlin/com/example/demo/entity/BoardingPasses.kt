package com.example.demo.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "boarding_passes")
class BoardingPasses (
    @Id
    val ticketNo: String,
    @Column
    val flightId : Int,
    @Column
    val boardingNo: Int,
    @Column
    val seatNo: String
)