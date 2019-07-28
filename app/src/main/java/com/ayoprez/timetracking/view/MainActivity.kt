package com.ayoprez.timetracking.view

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.ayoprez.timetracking.R
import com.ayoprez.timetracking.presenter.MainPresenter
import android.widget.Chronometer.OnChronometerTickListener
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.DialogTitle


class MainActivity : AppCompatActivity(), MainActivityView {

    private val presenter = MainPresenter()

    //Global variables
    private var pausedTime = 0L
    private var finalTimeText = ""

    //Global controls
    private lateinit var touchableArea: FrameLayout
    private lateinit var chronometer: Chronometer
    private lateinit var pauseButton: AppCompatButton
    private lateinit var resumeButton: AppCompatButton
    private lateinit var stopButton: AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attachView(this)

        initToolbar()
        initControls()
        initTouchableArea()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun initControls() {
        chronometer = findViewById(R.id.counter)
        pauseButton = findViewById(R.id.pauseButton)
        resumeButton = findViewById(R.id.resumeButton)
        stopButton = findViewById(R.id.stopButton)

        setupControls()
    }

    private fun setupControls() {
        pauseButton.setOnClickListener { presenter.pauseTracking() }
        resumeButton.setOnClickListener { presenter.resumeTracking() }
        stopButton.setOnClickListener { presenter.stopTracking() }

        //Show with hours, minutes and seconds
        chronometer.setOnChronometerTickListener {
            val time = SystemClock.elapsedRealtime() - chronometer.base
            val hours = (time / 3600000).toInt()
            val minutes = (time - hours * 3600000).toInt() / 60000
            val seconds = (time - (hours * 3600000).toLong() - (minutes * 60000).toLong()).toInt() / 1000

            val hourText = (if (hours < 10) "0$hours" else hours)
            val minuteText = (if (minutes < 10) "0$minutes" else minutes)
            val secondText = if (seconds < 10) "0$seconds" else seconds

            val total = "$hourText:$minuteText:$secondText"
            chronometer.text = total
        }

        chronometer.text = getText(R.string.chronometer_initial_state)
    }

    private fun initTouchableArea() {
        touchableArea = findViewById(R.id.touchable_area)
        touchableArea.setOnClickListener {
            presenter.startTracking()
            touchableArea.isEnabled = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_list) {

            startActivity(Intent(this, BookedTimeListActivity::class.java))

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun startCounter() {
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()

        resumeButton.visibility = View.GONE
        pauseButton.visibility = View.VISIBLE
        stopButton.visibility = View.VISIBLE
    }

    override fun pauseCounter() {
        pausedTime = chronometer.base - SystemClock.elapsedRealtime()
        chronometer.stop()

        resumeButton.visibility = View.VISIBLE
        pauseButton.visibility = View.GONE
        stopButton.visibility = View.VISIBLE
    }

    override fun resumeCounter() {
        chronometer.base = SystemClock.elapsedRealtime() + pausedTime
        chronometer.start()

        resumeButton.visibility = View.GONE
        pauseButton.visibility = View.VISIBLE
        stopButton.visibility = View.VISIBLE
    }

    override fun stopCounter() {
        finalTimeText = chronometer.text.toString()
        chronometer.stop()
        touchableArea.isEnabled = true
        chronometer.text = getText(R.string.chronometer_initial_state)

        displayFinishDialog()
    }

    private fun displayFinishDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_book_time)

        val title = dialog.findViewById<TextView>(R.id.dialog_title_book_time_text_view)
        val timeToBook = dialog.findViewById<TextView>(R.id.dialog_book_time_text_view)
        val addNewTime = dialog.findViewById<LinearLayout>(R.id.dialog_add_new_time_linear_layout)
        val description = dialog.findViewById<EditText>(R.id.dialog_description_edit_text)
        val bookButton = dialog.findViewById<AppCompatButton>(R.id.dialog_book_button)
        val discardButton = dialog.findViewById<AppCompatButton>(R.id.dialog_discard_button)

        title.text = getText(R.string.title_dialog_book)
        timeToBook.visibility = View.VISIBLE
        addNewTime.visibility = View.GONE

        timeToBook.text = finalTimeText

        bookButton.setOnClickListener {
            val time = timeToBook.text?.toString() ?: ""
            val desc = description.text.toString()

            presenter.saveTimeTracked(time, desc)

            resumeButton.visibility = View.GONE
            pauseButton.visibility = View.GONE
            stopButton.visibility = View.GONE

            dialog.dismiss()
        }

        discardButton.setOnClickListener {
            finalTimeText = ""
            dialog.dismiss()
        }

        dialog.setCancelable(false)

        dialog.show()
    }
}
