package com.gyunni.trackbox

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Delivery(
    val carrierName: String,
    val trackId: String,
    val status: String
)