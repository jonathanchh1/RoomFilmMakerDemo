package com.example.filmmaker.film

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.filmmaker.R
import com.example.filmmaker.db.DatabaseUseCases
import com.example.filmmaker.db.Directors
import com.example.filmmaker.db.Films
import com.example.filmmaker.db.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FilmSaveDialogFragment : DialogFragment(){
    

    private var mContext : Context?=null
    private var movieTitleExtra : String?=null
    private var movieDirectorFullExtra : String?=null
    private var usecase : DatabaseUseCases?=null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mContext = context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arg = arguments
        movieTitleExtra = arg?.getString(EXTRA_MOVIE_TITLE)
        movieDirectorFullExtra = arg?.getString(movieDirectorFullExtra)
        val db = MovieDatabase.getDatabase(mContext!!, MainScope())
        usecase = DatabaseUseCases(db)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(mContext)
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_movie, null)
        val movieEditTextTitle : EditText = view.findViewById(R.id.EditMovieTitle)
        val directorEditTextName : EditText = view.findViewById(R.id.editMovieDirectorFullName)


        if(movieTitleExtra != null){
            movieEditTextTitle.setText(movieTitleExtra)
            movieEditTextTitle.setSelection(movieTitleExtra!!.length)
        }

        if(movieDirectorFullExtra != null){
            directorEditTextName.setText(movieDirectorFullExtra)
            directorEditTextName.setSelection(movieDirectorFullExtra!!.length)
        }


           val alertDialog = alertDialogBuilder.setView(view)
               .setPositiveButton(R.string.save, ({ dialog, which ->
                   saveMovie(movieEditTextTitle.text.toString(), directorEditTextName.text.toString())
               }))
               .setNegativeButton(R.string.cancel, ({ dialog, which ->
                   dialog.cancel()
               }))

        return alertDialog.create()
    }


    private fun insert(films: Films) = MainScope().
        launch(Dispatchers.IO){
            usecase?.insertFilm(films)
        }


    private fun update(film : Films) = MainScope().
        launch(Dispatchers.IO){
            usecase?.update(film)
        }

    private fun updateDir(directors: Directors) = MainScope().launch(Dispatchers.IO){
        usecase?.update(directors)
    }


    private fun saveMovie(movieTitle: String, directorNames: String) = MainScope().launch(Dispatchers.IO){
          savesMovie(movieTitle, directorNames)
    }

   suspend fun savesMovie(movieTitle : String, directorNames : String){
        if(TextUtils.isEmpty(movieTitle) || TextUtils.isEmpty(directorNames)){
            return
        }
         var directorId = -1

        if(movieDirectorFullExtra != null){
            val directorToUpdate = usecase?.findDirectorByNames(directorNames)
            if(directorToUpdate != null){
                directorId = directorToUpdate.id

                if(directorToUpdate.fullname != directorNames){
                    directorToUpdate.fullname = directorNames
                    updateDir(directorToUpdate)
                }
            }
        }else{
            val newDirector = usecase?.findDirectorByNames(directorNames)
            directorId = if(newDirector == null){
                usecase?.insertDir(Directors(directorNames))!!.toInt()
            }else{
                newDirector.id
            }
        }

        if(movieTitleExtra != null){
            val movieToUpdate = usecase?.findFilmByTitles(movieTitle)
            if(movieToUpdate != null){

                if(movieToUpdate.title != movieTitle){
                    movieToUpdate.title = movieTitle
                    if(directorId != -1){
                        movieToUpdate.directorsId = directorId
                    }
                    update(Films(directorId, movieTitle))
                }

            }
        }else{
            val newFilm = usecase?.findFilmByTitles(movieTitle)
            if(newFilm == null){
                insert(Films(directorId, movieTitle))
            }else{

                if(newFilm.directorsId != directorId){
                    newFilm.directorsId = directorId
                    update(newFilm)
                }
            }
        }

    }

    companion object{

        var EXTRA_MOVIE_TITLE = "movie_title"
        const val EXTRA_MOVIE_DIRECTOR_FULL_NAME = "movie_director_full_name"
        const val TAG_DIALOG_MOVIE_SAVE = "dialog_movie_save"

    }
}