package com.devstudio.online_booking.service

import com.devstudio.online_booking.model.Bookings
import com.devstudio.online_booking.model.UserDetails
import com.devstudio.online_booking.repository.ServiceBookingRepository
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ServiceBookingService {
    @GET("bookings")
    fun getBookings(): Call<Bookings>

    @Headers("Content-Type: application/json")
    @POST("bookings")
    fun postBookings(@Body userDetails: UserDetails): Call<HashMap<String,Any>>

    companion object {
        private lateinit var bookingService: ServiceBookingService
        private const val BASE_URL = "http://192.168.43.47:3000/"
        fun getInstance(): ServiceBookingService {
            bookingService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(ServiceBookingRepository.client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServiceBookingService::class.java)
            return bookingService
        }
    }
}