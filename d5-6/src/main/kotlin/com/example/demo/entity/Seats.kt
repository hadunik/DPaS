package com.example.demo.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "seats")
class Seats(
    @Id
    val aircraftCode:String,
    @Column
    val seatNo: String,
    @Column
    val fareCondition: String
)