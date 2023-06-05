package com.example.demo.repos

import com.example.demo.entity.Ticket
import com.example.demo.entity.TicketFlight
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TicketRepo: JpaRepository<Ticket, String> {

}