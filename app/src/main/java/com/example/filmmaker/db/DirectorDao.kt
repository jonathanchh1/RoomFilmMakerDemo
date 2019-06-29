package com.example.filmmaker.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DirectorDao {


    @Query("Select * from table_director order by did asc")
    fun directorList() : LiveData<List<Directors>>

    @Query("Select * from table_director where did = :id LIMIT 1")
    fun findDirectorById(id : Int) : Directors

    @Query("Select * from table_director where full_name = :fullname LIMIT 1")
    suspend fun findDirectorByName(fullname : String) : Directors

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(directors: Directors) : Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(directors: Directors)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg directors: Directors)

    @Query("Delete from table_director")
    fun deleteAll()

}