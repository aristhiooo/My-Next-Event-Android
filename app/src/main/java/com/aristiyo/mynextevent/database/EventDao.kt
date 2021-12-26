package com.aristiyo.mynextevent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aristiyo.mynextevent.model.Event

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: Event)

    @Query("SELECT * FROM event ORDER BY dateFrom ASC")
    fun getAllEventList(): LiveData<List<Event>>

    @Query("SELECT * FROM event ORDER BY dateFrom ASC LIMIT 1")
    fun getUpcomingEvent(): LiveData<Event>
}