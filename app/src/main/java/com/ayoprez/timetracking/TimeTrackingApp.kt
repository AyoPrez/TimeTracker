package com.ayoprez.timetracking

import android.app.Application
import com.ayoprez.timetracking.model.ObjectBox

class TimeTrackingApp: Application() {

    override fun onCreate() {
        super.onCreate()

        ObjectBox.init(this)
    }
}