package com.devstudio.online_booking.repository

import com.devstudio.online_booking.model.UserDetails
import com.devstudio.online_booking.service.ServiceBookingService
import okhttp3.OkHttpClient

class ServiceBookingRepository constructor(private val serviceBooking: ServiceBookingService) {
    fun postBookings(userDetails: UserDetails) = serviceBooking.postBookings(userDetails)
    fun getBookings() = serviceBooking.getBookings()

    companion object {
        val client: OkHttpClient = OkHttpClient.Builder().build();
    }

}