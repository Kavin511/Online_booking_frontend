package com.devstudio.online_booking

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devstudio.online_booking.databinding.ActivityMainBinding
import com.devstudio.online_booking.model.UserDetails
import com.devstudio.online_booking.repository.ServiceBookingRepository
import com.devstudio.online_booking.service.ServiceBookingService
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var serviceBookingRepository: ServiceBookingRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initialiseActionBar()
        initialise()
    }

    private fun initialiseActionBar() {
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar?.elevation = 0F
        val actionbarLayout = R.layout.actionbar_layout
        supportActionBar!!.setCustomView(actionbarLayout)
        supportActionBar?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.action_bar_background
            )
        )
        val bookingHistory = findViewById<ImageView>(R.id.booking_history)
        bookingHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initialise() {
        val bookingService = ServiceBookingService.getInstance()
        serviceBookingRepository = ServiceBookingRepository(bookingService)
    }

    fun postBookingDetails(view: View) {
        val userDetails = UserDetails()
        val isAllFieldsAreValid = binding.name.text.isNotEmpty() && binding.phone.text.isNotEmpty()
        if (isAllFieldsAreValid) {
            userDetails.name = binding.name.text.toString()
            userDetails.phoneNumber = binding.phone.text.toString()
            val postBookings = serviceBookingRepository.postBookings(userDetails)
            postBookings.enqueue(object : retrofit2.Callback<HashMap<String, Any>> {
                override fun onResponse(
                    call: retrofit2.Call<HashMap<String, Any>>,
                    response: Response<HashMap<String, Any>>
                ) {
                    if ((response.body() as HashMap<String, Any>)["success"] as Boolean) {
                        Toast.makeText(
                            applicationContext,
                            ((response.body() as HashMap<String, Any>)["message"] as LinkedTreeMap<String, String>)["garage"],
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.name.setText("")
                        binding.phone.setText("")
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "${(response.body() as HashMap<String, Any>)["message"]}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                override fun onFailure(call: retrofit2.Call<HashMap<String, Any>>, t: Throwable) {
                    Toast.makeText(applicationContext, "" + t.message + "", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        } else {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
        }
    }
}