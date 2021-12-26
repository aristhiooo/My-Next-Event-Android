package com.aristiyo.mynextevent.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Event(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "location")
    var location: String? = null,

    @ColumnInfo(name = "dateFrom")
    var dateFrom: String? = null,

    @ColumnInfo(name = "dateTo")
    var dateTo: String? = null,

    @ColumnInfo(name = "timeFrom")
    var timeFrom: String? = null,

    @ColumnInfo(name = "timeTo")
    var timeTo: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "organizer")
    var organizer: String? = null,

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray? = null
) : Parcelable
