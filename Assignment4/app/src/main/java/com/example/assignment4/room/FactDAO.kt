package com.example.assignment4.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDAO {
    @Query("SELECT * FROM facts ORDER BY id DESC")
    fun getAllFacts(): Flow<List<FactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFact(fact: FactEntity)
}