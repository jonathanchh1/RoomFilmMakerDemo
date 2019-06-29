package com.example.filmmaker.film

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.filmmaker.FilmApp.Companion.appContext
import com.example.filmmaker.db.DatabaseUseCases
import com.example.filmmaker.db.Films
import com.example.filmmaker.db.MovieDatabase

class FilmViewModel constructor(application: Application) : AndroidViewModel(application){


    val filmList : LiveData<List<Films>>
    private val useCases : DatabaseUseCases
    init {
        val db = MovieDatabase.getDatabase(appContext(), viewModelScope)
        useCases = DatabaseUseCases(db)
        filmList = useCases.filmList

    }

    fun deleteAll(){
        useCases.deleteAllfilm()
    }

    /*
    fun onFilmDialogClick(v: View){
        v.apply{
            val args = Bundle()
            args.putString(FilmSaveDialogFragment.EXTRA_MOVIE_TITLE, films.title)
            args.putString(FilmSaveDialogFragment.EXTRA_MOVIE_DIRECTOR_FULL_NAME, directorFullName)
            val dialogFragment = FilmSaveDialogFragment()
             dialogFragment.arguments = args
            dialogFragment.show((context as AppCompatActivity).supportFragmentManager,
                FilmSaveDialogFragment.TAG_DIALOG_MOVIE_SAVE)
        }
    }

    */

}