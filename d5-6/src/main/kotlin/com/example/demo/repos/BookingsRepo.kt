package com.example.demo.repos

import com.example.demo.cast.ContactDataPart
import com.example.demo.entity.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookingsRepo : JpaRepository<Booking, String> {
    @Query("select count(*) from bookings.bookings where book_ref = :bookRef ", nativeQuery = true)
    fun checkUniqBookRef(@Param("bookRef") bookRed: String): Int
}