package com.example.assignment4

import com.example.assignment4.network.FactAPI
import com.example.assignment4.room.FactDAO
import com.example.assignment4.room.FactEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FactRepository(private val scope: CoroutineScope, private val dao: FactDAO, private val api: FactAPI) {

    val allFacts: Flow<List<FactEntity>> = dao.getAllFacts()

    fun addFact(fact: String) {
        scope.launch {
            val fact = FactEntity(fact)
            dao.insertFact(fact)
        }
    }

    fun fetchAndStoreFact() {
        scope.launch {
            val response = api.fetchRandomFact()
            val fact = FactEntity(response.text)
            dao.insertFact(fact)
        }
    }

}