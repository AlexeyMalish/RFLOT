package com.example.bachelordegreeproject.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CheckZoneRequestModel(
    val idReport: String,
    val nameZone: String
)
