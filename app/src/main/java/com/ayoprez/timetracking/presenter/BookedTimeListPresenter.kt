package com.ayoprez.timetracking.presenter

import com.ayoprez.timetracking.view.BookedTimeListActivityView

class BookedTimeListPresenter: BasePresenter() {

    private lateinit var mainView: BookedTimeListActivityView

    fun attachView(view: BookedTimeListActivityView) {
        this.mainView = view
    }

    fun getAllTimeTrackedData() {
        val allData = getAllData()

        if (allData.isNullOrEmpty()) {
            mainView.showEmptyListScreen()
        } else {
            mainView.updateList(getAllData())
            mainView.showList()
        }
    }

    fun saveTime(time: String, description: String) {
        saveTimeTracked(time, description)
        getAllTimeTrackedData()
    }

    fun filterByQuery(query: CharSequence) {
        val filterList = getAllData().filter { it.description.toLowerCase().contains(query) }
        mainView.updateList(filterList.toMutableList())
    }
}