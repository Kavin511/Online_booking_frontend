package com.devstudio.online_booking.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devstudio.online_booking.R
import com.devstudio.online_booking.listeners.OnItemClickListener
import com.devstudio.online_booking.model.Bookings

class BookingHistoryAdapter(
    private val bookings: List<Bookings>,
    private val context: Context) :
    RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var bookingUserName: TextView = view.findViewById(R.id.booking_user_name) as TextView
        var garageName: TextView = view.findViewById(R.id.garage_status) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.history_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = bookings[position]
        holder.bookingUserName.text = entry.customerName
        holder.garageName.text = if (entry.garageId != 0L) "Garage Booked" else "Pending Allocation of Garage"
    }

    override fun getItemCount(): Int {
        return bookings.size
    }
}