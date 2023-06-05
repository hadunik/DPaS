package com.example.demo.cast

class JSONForBookingBody(
    var passportData: String,
    var name: String,
    var contactData: ContactDataPart,
    var flights: List<ForInputFlights>
)