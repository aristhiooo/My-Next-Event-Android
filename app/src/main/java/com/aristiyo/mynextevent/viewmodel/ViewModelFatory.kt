package com.aristiyo.mynextevent.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFatory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFatory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFatory {
            if (INSTANCE == null) {
                synchronized(ViewModelFatory::class.java) {
                    INSTANCE = ViewModelFatory(application)
                }
            }
            return INSTANCE as ViewModelFatory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}