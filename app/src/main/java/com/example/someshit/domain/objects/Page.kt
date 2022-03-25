package com.example.someshit.domain.objects

data class Page(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: List<Photo>
) {
//    override fun toString(): String {
//        return "Page(page=$page, pages=$pages, perpage=$perpage, total=$total, photo=${photo})"
//    }
}