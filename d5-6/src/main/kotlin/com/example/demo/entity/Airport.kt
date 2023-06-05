package com.example.demo.entity

import com.example.demo.cast.TranslationEntityPart
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name="airports_data")
class Airport(
    @Id
    val airportCode: String,
    @JdbcTypeCode(SqlTypes.JSON)
    val airportName: TranslationEntityPart,
    @JdbcTypeCode(SqlTypes.JSON)
    val city: TranslationEntityPart,
    @Column
    val timezone: String
) {

}