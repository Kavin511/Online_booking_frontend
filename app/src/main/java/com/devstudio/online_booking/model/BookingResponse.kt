package com.devstudio.online_booking.model

import com.google.gson.annotations.SerializedName

data class Bookings(
    @SerializedName("BOOKING_ID")
    var bookingId: Long = 0,
    @SerializedName("GARAGE_ID")
    var garageId: Long = 0,
    @SerializedName("CUSTOMER_NAME")
    var customerName: String = "",
    @SerializedName("PHONE_NUMBER")
    var phoneNumber: String = ""
)

data class BookingResponse(
    @SerializedName("bookings")
    var bookings: List<Bookings>
)