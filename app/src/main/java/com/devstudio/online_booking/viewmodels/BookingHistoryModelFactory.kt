package com.devstudio.online_booking.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devstudio.online_booking.repository.ServiceBookingRepository
import com.devstudio.zivame.viewmodels.BookingHistoryViewModel

class BookingHistoryModelFactory constructor(private val repository: ServiceBookingRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BookingHistoryViewModel::class.java)) {
            BookingHistoryViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("Viewmodel not found")
        }
    }
}