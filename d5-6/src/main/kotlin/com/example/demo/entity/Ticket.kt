package com.example.demo.entity

import com.example.demo.cast.ContactDataPart
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "tickets")
class Ticket(
    @Id
    val ticketNo:String,
    @Column
    val bookRef:String,
    @Column
    val passengerId : String,
    @Column
    val passengerName: String,
    @JdbcTypeCode(SqlTypes.JSON)
    val contactData: ContactDataPart
)