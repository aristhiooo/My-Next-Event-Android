package com.aristiyo.mynextevent.view

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aristiyo.mynextevent.databinding.RowEventListBinding
import com.aristiyo.mynextevent.model.Event

class EventListAdapter(private val listEvent: ArrayList<Event>) :
    RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {


    class EventViewHolder(var binding: RowEventListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            RowEventListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val data = listEvent[position]
        holder.binding.apply {
            txtEventName.text = data.name
            txtEventTime.text = "${data.dateFrom} - ${data.timeFrom}"
            txtEventLocation.text = data.location
            imgEvent.setImageBitmap(
                data.image?.size?.let { BitmapFactory.decodeByteArray(data.image, 0, it) }
            )
        }
    }

    override fun getItemCount(): Int = listEvent.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataSet: List<Event>?) {
        listEvent.apply {
            clear()
            if (dataSet != null) addAll(dataSet)
        }
        notifyDataSetChanged()
    }
}