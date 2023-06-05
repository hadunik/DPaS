package com.example.demo.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Timestamp

@Entity
@Table(name = "bookings")
class Booking(
    @Id
    val bookRef:String,
    @Column
    val bookDate:Timestamp,
    @Column
    val totalAmount:Int
)