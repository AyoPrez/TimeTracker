package com.ayoprez.timetracking.view

import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayoprez.timetracking.BookedTimeListAdapter
import com.ayoprez.timetracking.R
import com.ayoprez.timetracking.model.TrackedTime
import com.ayoprez.timetracking.presenter.BookedTimeListPresenter

class BookedTimeListActivity : AppCompatActivity(), BookedTimeListActivityView {

    private val presenter = BookedTimeListPresenter()

    // Global controls
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyScreen: LinearLayout
    private lateinit var adapter: BookedTimeListAdapter

    // Global variables
    private var newTimeTracked = "00:00:00"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_time)

        presenter.attachView(this)

        initToolbar()
        initControls()

        presenter.getAllTimeTrackedData()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
    }

    private fun initControls() {
        recyclerView = findViewById(R.id.booked_time_recycler_view)
        emptyScreen = findViewById(R.id.empty_list_layout)
        adapter = BookedTimeListAdapter()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.booked_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val charSequence: CharSequence = query?.toLowerCase() ?: ""

                    presenter.filterByQuery(charSequence)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val charSequence: CharSequence = newText?.toLowerCase() ?: ""

                    presenter.filterByQuery(charSequence)
                    return true
                }
            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_add -> {
                openAddNewTimeTrackedDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showEmptyListScreen() {
        recyclerView.visibility = View.GONE
        emptyScreen.visibility = View.VISIBLE
    }

    override fun showList() {
        recyclerView.visibility = View.VISIBLE
        emptyScreen.visibility = View.GONE
    }

    override fun updateList(itemList: MutableList<TrackedTime>) {
        adapter.updateList(itemList)
    }

    private fun openAddNewTimeTrackedDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_book_time)

        val title = dialog.findViewById<TextView>(R.id.dialog_title_book_time_text_view)
        val timeToBook = dialog.findViewById<TextView>(R.id.dialog_book_time_text_view)
        val addNewTime = dialog.findViewById<LinearLayout>(R.id.dialog_add_new_time_linear_layout)
        val description = dialog.findViewById<EditText>(R.id.dialog_description_edit_text)
        val bookButton = dialog.findViewById<AppCompatButton>(R.id.dialog_book_button)
        val discardButton = dialog.findViewById<AppCompatButton>(R.id.dialog_discard_button)

        title.text = getText(R.string.title_dialog_add_new_book)
        timeToBook.visibility = View.GONE
        addNewTime.visibility = View.VISIBLE

        handleAddNewTimeControls(dialog)

        bookButton.setOnClickListener {
            val time = newTimeTracked
            val desc = description.text.toString()

            presenter.saveTime(time, desc)

            dialog.dismiss()
        }

        discardButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(false)

        dialog.show()
    }

    private fun handleAddNewTimeControls(dialog: Dialog) {
        val addNewTimeSumHoursButton = dialog.findViewById<ImageButton>(R.id.add_hour_button)
        val addNewTimeSubtractHoursButton = dialog.findViewById<ImageButton>(R.id.remove_hour_button)
        val addNewTimeHours = dialog.findViewById<TextView>(R.id.hours_text_view)
        val addNewTimeSumMinutesButton = dialog.findViewById<ImageButton>(R.id.add_minute_button)
        val addNewTimeSubtractMinutesButton = dialog.findViewById<ImageButton>(R.id.remove_minute_button)
        val addNewTimeMinutes = dialog.findViewById<TextView>(R.id.minutes_text_view)
        val addNewTimeSumSecondsButton = dialog.findViewById<ImageButton>(R.id.add_second_button)
        val addNewTimeSubtractSecondsButton = dialog.findViewById<ImageButton>(R.id.remove_second_button)
        val addNewTimeSeconds = dialog.findViewById<TextView>(R.id.seconds_text_view)

        addNewTimeSumHoursButton.setOnClickListener {
            val currentNumber = addNewTimeHours.text.toString().toInt()
            val sum = currentNumber + 1

            val finalNumber = if (sum < 10) {
                "0$sum"
            } else {
                "$sum"
            }

            addNewTimeHours.text = finalNumber
            newTimeTracked = "${addNewTimeHours.text}:${addNewTimeMinutes.text}:${addNewTimeSeconds.text}"
        }
        addNewTimeSubtractHoursButton.setOnClickListener {
            val currentNumber = addNewTimeHours.text.toString().toInt()
            val subtract = if (currentNumber > 0) {
                currentNumber - 1
            } else {
                0
            }

            val finalNumber = if (subtract < 10) {
                "0$subtract"
            } else {
                "$subtract"
            }

            addNewTimeHours.text = finalNumber
            newTimeTracked = "${addNewTimeHours.text}:${addNewTimeMinutes.text}:${addNewTimeSeconds.text}"
        }

        addNewTimeSumMinutesButton.setOnClickListener {
            val currentNumber = addNewTimeMinutes.text.toString().toInt()
            val sum = if (currentNumber < 59) {
                currentNumber + 1
            } else {
               0
            }

            val finalNumber = if (sum < 10) {
                "0$sum"
            } else {
                "$sum"
            }

            addNewTimeMinutes.text = finalNumber
            newTimeTracked = "${addNewTimeHours.text}:${addNewTimeMinutes.text}:${addNewTimeSeconds.text}"
        }
        addNewTimeSubtractMinutesButton.setOnClickListener {
            val currentNumber = addNewTimeMinutes.text.toString().toInt()
            val subtract = if (currentNumber > 0) {
                currentNumber - 1
            } else {
                59
            }

            val finalNumber = if (subtract < 10) {
                "0$subtract"
            } else {
                "$subtract"
            }

            addNewTimeMinutes.text = finalNumber
            newTimeTracked = "${addNewTimeHours.text}:${addNewTimeMinutes.text}:${addNewTimeSeconds.text}"
        }

        addNewTimeSumSecondsButton.setOnClickListener {
            val currentNumber = addNewTimeSeconds.text.toString().toInt()
            val sum = if (currentNumber < 59) {
                currentNumber + 1
            } else {
                0
            }

            val finalNumber = if (sum < 10) {
                "0$sum"
            } else {
                "$sum"
            }

            addNewTimeSeconds.text = finalNumber
            newTimeTracked = "${addNewTimeHours.text}:${addNewTimeMinutes.text}:${addNewTimeSeconds.text}"
        }
        addNewTimeSubtractSecondsButton.setOnClickListener {
            val currentNumber = addNewTimeSeconds.text.toString().toInt()
            val subtract = if (currentNumber > 0) {
                currentNumber - 1
            } else {
                59
            }

            val finalNumber = if (subtract < 10) {
                "0$subtract"
            } else {
                "$subtract"
            }

            addNewTimeSeconds.text = finalNumber
            newTimeTracked = "${addNewTimeHours.text}:${addNewTimeMinutes.text}:${addNewTimeSeconds.text}"
        }

    }
}