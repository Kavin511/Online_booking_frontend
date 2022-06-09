package com.devstudio.zivame.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devstudio.online_booking.model.Bookings
import com.devstudio.online_booking.repository.ServiceBookingRepository
import retrofit2.Callback
import retrofit2.Response

class BookingHistoryViewModel constructor(private val repository: ServiceBookingRepository) :
    ViewModel() {
    val bookingList = MutableLiveData<List<Bookings>?>()
    val errorMessage = MutableLiveData<String>()
    fun fetchBookings() {
        val response = repository.getBookings()
        response.enqueue(object : Callback<Bookings> {
            override fun onResponse(call: retrofit2.Call<Bookings>, response: Response<Bookings>) {
                if (response.body() != null) {
                    bookingList.postValue((response.body() as List<Bookings>))
                } else {
                    errorMessage.postValue("No bookings found")
                }
            }

            override fun onFailure(call: retrofit2.Call<Bookings>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }
}