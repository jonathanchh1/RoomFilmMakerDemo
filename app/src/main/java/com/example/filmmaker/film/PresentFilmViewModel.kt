package com.example.filmmaker.film

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmmaker.db.DatabaseUseCases
import com.example.filmmaker.db.Directors
import com.example.filmmaker.db.Films
import com.example.filmmaker.db.MovieDatabase


class PresentFilmViewModel(var context: Context, var films : Films) : ViewModel(){


    fun title() = films.title
    var useCase : DatabaseUseCases
    var directorName : String
    var director : Directors
    init {
        val db = MovieDatabase.getDatabase(context, viewModelScope)
         useCase = DatabaseUseCases(db)

    }



    fun directorName() = directorName
    fun onFilmDialogClick(v: View) {
        v.apply {
            val args = Bundle()
            args.putString(FilmSaveDialogFragment.EXTRA_MOVIE_TITLE, films.title)
            args.putString(FilmSaveDialogFragment.EXTRA_MOVIE_DIRECTOR_FULL_NAME, directorName)
            val dialogSave = FilmSaveDialogFragment()
            dialogSave.arguments = args
            dialogSave.show(
                (context as AppCompatActivity).supportFragmentManager,
                FilmSaveDialogFragment.TAG_DIALOG_MOVIE_SAVE
            )
        }
    }
}
