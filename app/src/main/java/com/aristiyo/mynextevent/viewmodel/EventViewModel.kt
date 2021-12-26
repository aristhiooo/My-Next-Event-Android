package com.aristiyo.mynextevent.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.aristiyo.mynextevent.database.EventRepository
import com.aristiyo.mynextevent.model.Event

class EventViewModel(application: Application) : AndroidViewModel(application) {

    private val mEventRepository: EventRepository = EventRepository(application)

    fun getAllEvents(): LiveData<List<Event>> = mEventRepository.getAllEvents()

    fun getUpcomingEvent(): LiveData<Event> = mEventRepository.getUpcomingEvent()

    fun insert(event: Event) {
        mEventRepository.insert(event)
    }

}