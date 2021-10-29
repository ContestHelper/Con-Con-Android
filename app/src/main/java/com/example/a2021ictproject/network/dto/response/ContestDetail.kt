package com.example.a2021ictproject.network.dto.response

data class ContestDetail(
    val id: Int,
    val title: String,
    val content: String,
    val startdate: String,
    val duedate: String,
    val host: String,
    val prize: List<Prize>
)
