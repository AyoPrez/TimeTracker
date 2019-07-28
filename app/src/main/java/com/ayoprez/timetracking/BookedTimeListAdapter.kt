package com.ayoprez.timetracking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayoprez.timetracking.model.TrackedTime
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class BookedTimeListAdapter: RecyclerView.Adapter<BookedTimeListAdapter.BookedTimeViewHolder>() {

    private var timeTrackedList = mutableListOf<TrackedTime>()

    fun updateList(mutableList: MutableList<TrackedTime>) {
        timeTrackedList = mutableList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookedTimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booked_time, parent, false)
        return BookedTimeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return timeTrackedList.size
    }

    override fun onBindViewHolder(holder: BookedTimeViewHolder, position: Int) {
        holder.cardDate.text = formatDate(timeTrackedList[position].date)
        holder.cardTime.text = timeTrackedList[position].time
        holder.cardDescription.text = timeTrackedList[position].description
    }

    private fun formatDate(dateInMillis: Long): String {
        val date = Date(dateInMillis)
        val format = SimpleDateFormat("dd/M/yyyy hh:mm", Locale.getDefault())
        return format.format(date)
    }

    class BookedTimeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cardDate: TextView = itemView.findViewById(R.id.card_date)
        val cardTime: TextView = itemView.findViewById(R.id.card_time)
        val cardDescription: TextView = itemView.findViewById(R.id.card_description)
    }
}