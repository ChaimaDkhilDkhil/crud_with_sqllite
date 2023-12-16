package com.example.crud_avec_sql

import kotlin.random.Random

data class StudentModel(
    var id: Int = getAutoId(),
    val name: String = "",
    val email: String = ""
) {
    companion object {
        fun getAutoId(): Int {
            val random = Random
            return random.nextInt(100)
        }
    }
}
