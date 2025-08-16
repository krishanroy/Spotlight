package com.krishan.spotlight.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(

    @SerialName("status")
    val status: String?,

    @SerialName("code")
    val code: String?,

    @SerialName("message")
    val message: String?
)