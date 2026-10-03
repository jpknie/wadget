package com.jn.wadget.computeclient

object ApiConstants {
    const val BASE_API_URL = "/api/v1/"
    const val CATEGORIES = BASE_API_URL + "categories"

    fun category(id: Long) = "$BASE_API_URL/categories/$id"
}