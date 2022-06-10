package com.devstudio.online_booking.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devstudio.online_booking.model.BookingResponse
import com.devstudio.online_booking.repository.ServiceBookingRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingHistoryViewModel constructor(private val repository: ServiceBookingRepository) :
    ViewModel() {
    val bookingList = MutableLiveData<BookingResponse>()
    val errorMessage = MutableLiveData<String>()
    fun fetchBookings() {
        val response = repository.getBookings()
        response.enqueue(object : Callback<BookingResponse> {
            override fun onResponse(call: Call<BookingResponse>, response: Response<BookingResponse>) {
                if (response.body() != null) {
                    bookingList.postValue((response.body() as BookingResponse))
                } else {
                    errorMessage.postValue("No bookings found")
                }
            }

            override fun onFailure(call: Call<BookingResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}