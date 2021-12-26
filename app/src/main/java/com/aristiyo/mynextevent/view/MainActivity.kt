package com.aristiyo.mynextevent.view

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristiyo.mynextevent.databinding.ActivityMainBinding
import com.aristiyo.mynextevent.helpers.HelperFunction
import com.aristiyo.mynextevent.viewmodel.EventViewModel
import com.aristiyo.mynextevent.viewmodel.ViewModelFatory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var eventViewModel: EventViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        binding.topAppBar.setNavigationOnClickListener {

        }

        eventViewModel = obtainViewModel(this@MainActivity)
        eventViewModel.getAllEvents().observe(this, { dataSet ->
            if (dataSet.isNullOrEmpty()) {
                binding.eventsContainer.visibility = View.GONE
                binding.txtNoEvents.visibility = View.VISIBLE
            } else {
                binding.eventsContainer.visibility = View.VISIBLE
                binding.txtNoEvents.visibility = View.GONE
                binding.rvEventList.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = EventListAdapter(arrayListOf()).also { it.setData(dataSet) }
                }
            }

        })

        eventViewModel.getUpcomingEvent().observe(this, { dataUpcomingEvent ->
            if (dataUpcomingEvent != null) {
                binding.imgUpcomingEvent.setImageBitmap(dataUpcomingEvent.image?.size?.let {
                    BitmapFactory.decodeByteArray(
                        dataUpcomingEvent.image,
                        0,
                        it
                    )
                })
                binding.txtNameUpcomingEvent.text = dataUpcomingEvent.name
                binding.txtLocationUpcomingEvent.text = dataUpcomingEvent.location
                binding.txtDateUpcomingEvent.text =
                    HelperFunction.getDateDay(dataUpcomingEvent.dateFrom ?: "-")
                binding.txtMonthUpcomingEvent.text =
                    HelperFunction.getDateMonth(dataUpcomingEvent.dateFrom ?: "-")
            }
        })

        binding.btnCreateEvent.setOnClickListener {
            CreateEventFragment.display(supportFragmentManager)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): EventViewModel {
        val factory = ViewModelFatory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[EventViewModel::class.java]
    }
}