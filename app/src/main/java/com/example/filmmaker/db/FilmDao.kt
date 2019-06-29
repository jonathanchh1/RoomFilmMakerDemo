package com.example.filmmaker.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FilmDao{

    @Query("Select * from table_film ORDER BY title ASC")
    fun FilmList() : LiveData<List<Films>>

    @Query("Select * from table_film where title = :title LIMIT 1")
  suspend  fun findFilmByTitle(title: String) : Films

     @Insert(onConflict = OnConflictStrategy.IGNORE)
     suspend fun insert(vararg directors: Films)

     @Update(onConflict = OnConflictStrategy.IGNORE)
     suspend fun update(directors: Films)

      @Query("DELETE FROM TABLE_FILM")
       fun deleteAll()

}