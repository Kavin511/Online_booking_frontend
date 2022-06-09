package com.devstudio.online_booking

import android.os.Bundle
import android.view.View.GONE
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devstudio.online_booking.adapters.BookingHistoryAdapter
import com.devstudio.online_booking.repository.ServiceBookingRepository
import com.devstudio.online_booking.service.ServiceBookingService
import com.devstudio.online_booking.viewmodels.BookingHistoryModelFactory
import com.devstudio.zivame.viewmodels.BookingHistoryViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var loadingBooking: TextView
    private lateinit var bookingList: RecyclerView
    private lateinit var adapter: BookingHistoryAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        initialise()
        val viewModel = initialiseBookingHistoryViewModel()
        observeBookingList(viewModel)
        observeError(viewModel)
        initialiseBookingList(viewModel)
    }

    private fun observeError(viewModel: BookingHistoryViewModel) {
        viewModel.errorMessage.observe(this) {
            progressBar.visibility = GONE
            loadingBooking.text="Error while loading"
        }
    }

    private fun initialise() {
        bookingList = findViewById(R.id.booking_list)
        progressBar = findViewById(R.id.progress_circular)
        loadingBooking = findViewById<TextView>(R.id.loading_booking_text)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar?.elevation = 0F
        val actionbarLayout = R.layout.actionbar_layout
        supportActionBar!!.setCustomView(actionbarLayout)
        findViewById<TextView>(R.id.title).text = getString(R.string.booking_history)
        supportActionBar?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.action_bar_background
            )
        )
    }

    private fun initialiseBookingHistoryViewModel(): BookingHistoryViewModel {
        val bookingService = ServiceBookingService.getInstance()
        return ViewModelProvider(
            this,
            BookingHistoryModelFactory(ServiceBookingRepository(bookingService))
        ).get(BookingHistoryViewModel::class.java)
    }

    private fun observeBookingList(viewModel: BookingHistoryViewModel) {
        viewModel.fetchBookings()
        viewModel.bookingList.observe(this) {
            progressBar.visibility = GONE
            adapter = BookingHistoryAdapter(
                viewModel.bookingList.value ?: listOf(),
                applicationContext,
            )
            bookingList.adapter = adapter
        }
    }

    private fun initialiseBookingList(viewModel: BookingHistoryViewModel) {
        adapter = BookingHistoryAdapter(
            viewModel.bookingList.value ?: listOf(),
            applicationContext,
        )
        bookingList.adapter = adapter
        bookingList.layoutManager = GridLayoutManager(this, 1)
    }

}
