package com.example.someshit.domain.objects

import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int
) {
    fun generateDownloadLink(source: String): String {
        var string = source

        for (property in this::class.memberProperties)
            string = string.replace(Regex("\\$\\{${property.name}\\}"),
                readInstanceProperty(property.name))

        return string
    }

    @Suppress("UNCHECKED_CAST")
    private fun readInstanceProperty(propertyName: String): String {
        val property = this::class.members.first {
            it.name == propertyName } as KProperty1<Any, *>
        return  property.get(this).toString()
    }
}