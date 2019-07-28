package com.ayoprez.timetracking.model

import io.objectbox.Box

class DatabaseAPI {

    private fun getBox(): Box<TrackedTime>? {
        return ObjectBox.get()?.boxFor(TrackedTime::class.java)
    }

    fun saveData(date: Long, time: String, description: String) {

        val timeTracked = TrackedTime(
            date = date,
            time = time,
            description = description
        )

        getBox()?.put(timeTracked)
    }

    fun getAll(): MutableList<TrackedTime> {
        return getBox()?.all ?: mutableListOf()
    }

}