package com.example.assignment4

import com.example.assignment4.room.FactDAO
import com.example.assignment4.room.FactEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FactRepository(private val scope: CoroutineScope, private val dao: FactDAO) {

    val allFacts: Flow<List<FactEntity>> = dao.getAllFacts()

    fun addCourse(fact: String) {
        scope.launch {
            val fact = FactEntity(fact)
            dao.insertFact(fact)
        }
    }
}