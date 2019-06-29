package com.example.filmmaker.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData


class DatabaseUseCases(var db : MovieDatabase) {


    private var directorDao : DirectorDao = db.directorDao()
    private var filmDao : FilmDao = db.filmDao()

    var filmList : LiveData<List<Films>> = filmDao.FilmList()
    var directorList : LiveData<List<Directors>> = directorDao.directorList()

     fun findDirectorByIds(id : Int) : Directors{
        return directorDao.findDirectorById(id)
     }




    @WorkerThread
    suspend fun findDirectorByNames(fullname : String): Directors{
        return directorDao.findDirectorByName(fullname)
    }

    @WorkerThread
    suspend fun findFilmByTitles(title : String) : Films{
        return filmDao.findFilmByTitle(title)
    }

    @WorkerThread
    suspend fun insertFilm(films: Films){
        return filmDao.insert(films)
    }

     suspend fun insertDir(directors: Directors) : Long{
        return directorDao.insert(directors)
    }

    @WorkerThread
    suspend fun update(films: Films){
        return filmDao.update(films)
    }

    @WorkerThread
    suspend fun update(directors: Directors){
        return directorDao.update(directors)
    }

    fun deleteAllfilm(){
        return filmDao.deleteAll()
    }

    fun deletAlldirectors(){
        return directorDao.deleteAll()
    }

}