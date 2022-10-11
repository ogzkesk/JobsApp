package com.example.jobsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jobsapp.models.FavoriteJob

@Dao
interface FavoriteDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteJob(job: FavoriteJob)

    @Query("SELECT * FROM job_table ORDER BY id DESC")
    fun getAllFavJob() : LiveData<List<FavoriteJob>>

    @Delete
    suspend fun deleteFavJob(job: FavoriteJob)
}