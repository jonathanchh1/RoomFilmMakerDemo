package com.example.filmmaker.director

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.filmmaker.FilmApp.Companion.appContext
import com.example.filmmaker.db.DatabaseUseCases
import com.example.filmmaker.db.Directors
import com.example.filmmaker.db.MovieDatabase


class DirectorViewModels constructor(application: Application) : AndroidViewModel(application){

    var directorList : LiveData<List<Directors>>
    private val databaseUseCase : DatabaseUseCases

    init {
        val db = MovieDatabase.getDatabase(appContext(), viewModelScope)
        databaseUseCase = DatabaseUseCases(db)
        directorList = databaseUseCase.directorList
    }


    fun deleteAll(){
        databaseUseCase.deletAlldirectors()
    }
}