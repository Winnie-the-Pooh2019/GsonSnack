package com.example.gsonsnack.domain.objects

data class Page(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: List<Photo>
)