package com.ayoprez.timetracking.presenter

import com.ayoprez.timetracking.view.MainActivityView

class MainPresenter: BasePresenter() {

    private lateinit var mainView: MainActivityView

    fun attachView(view: MainActivityView) {
        this.mainView = view
    }

    fun startTracking() {
        mainView.startCounter()
    }

    fun stopTracking() {
        mainView.stopCounter()
    }

    fun resumeTracking() {
        mainView.resumeCounter()
    }

    fun pauseTracking() {
        mainView.pauseCounter()
    }
}