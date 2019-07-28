package com.ayoprez.timetracking.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TrackedTime (
    @Id var id: Long = 0,
    var date: Long = 0L,
    var time: String = "",
    var description: String = ""
)