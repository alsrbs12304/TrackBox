package com.gyunni.trackbox.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "delivery")
data class Delivery(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val nickName : String? = null,
    val fromName: String,
    val fromTime: String,
    val toName: String,
    val carrierId: String,
    val carrierName: String,
    val trackId: String,
    val status: String
):Serializable