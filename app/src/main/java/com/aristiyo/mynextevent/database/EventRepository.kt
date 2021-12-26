package com.aristiyo.mynextevent.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.aristiyo.mynextevent.model.Event
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EventRepository(application: Application) {

    private val mEventDao: EventDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val database = EventRoomDatabase.getDatabase(application)
        mEventDao = database.eventDao()
    }

    fun getAllEvents(): LiveData<List<Event>> = mEventDao.getAllEventList()

    fun getUpcomingEvent(): LiveData<Event> = mEventDao.getUpcomingEvent()

    fun insert(event: Event) {
        executorService.execute { mEventDao.insert(event) }
    }

}