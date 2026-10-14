package com.jn.wadget.computeclient

object ApiConstants {
    const val BASE_API_URL = "/api/v1"
    const val TAGS = BASE_API_URL + "/tags"
    const val COMPUTATIONS = BASE_API_URL + "/computations"
    fun tag(id: String) = "$TAGS/$id"
}