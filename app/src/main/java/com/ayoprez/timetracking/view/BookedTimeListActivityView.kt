package com.ayoprez.timetracking.view

import com.ayoprez.timetracking.model.TrackedTime

interface BookedTimeListActivityView {

    fun showEmptyListScreen()
    fun showList()

    fun updateList(itemList: MutableList<TrackedTime>)
}