package com.jn.wadget.state

import kotlinx.serialization.Serializable

@Serializable
data class SettingsScreenState(
    val netSalaryCents: Long = 0L,
    val payrollDate: Int = 15
)