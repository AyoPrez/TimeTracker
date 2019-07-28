package com.ayoprez.timetracking.presenter

import com.ayoprez.timetracking.model.DatabaseAPI
import com.ayoprez.timetracking.model.TrackedTime

open class BasePresenter {

    private val databaseAPI = DatabaseAPI()

    fun saveTimeTracked(time: String, description: String) {
        databaseAPI.saveData(getCurrentDate(), time, description)
    }

    fun getAllData(): MutableList<TrackedTime> {
        return databaseAPI.getAll()
    }

    private fun getCurrentDate() : Long {
        return System.currentTimeMillis()
    }

}