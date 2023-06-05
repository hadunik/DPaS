package com.example.demo.repos

import com.example.demo.cast.TranslationEntityPart
import com.example.demo.entity.Airport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AirportRepo : JpaRepository<Airport, String>{

    fun findByAirportCode(@Param("airportCode") airpotrCode : String) : Airport

    @Query("select distinct city from Airport")
    fun getDistinctCity():List<TranslationEntityPart>

    @Query("select * from airports_data ad where (ad.city ->> 'ru')\\:\\:bpchar = :cityEN  or (ad.city ->> 'en')\\:\\:bpchar = :cityRU ", nativeQuery = true)
    fun airportsbycity(@Param("cityEN") cityEN: String, @Param("cityRU") cityRU: String):List<Airport>



//    @Query("select airportName " +
//            "from Airport " +
//            "where city.en = 'Novosibirsk' or city.ru = 'Новосибирск'")
//    fun getAirportByCity(@Param("city") cityForSearch : String ):List<TranslationEntityPart>
}