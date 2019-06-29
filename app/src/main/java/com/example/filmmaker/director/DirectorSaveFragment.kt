package com.example.filmmaker.director

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
import com.example.filmmaker.db.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DirectorSaveFragment : DialogFragment() {
    private var directorFullNameExtra : String?= null
    private var mContext : Context?=null
    private var db : MovieDatabase?=null
    private var usecase : DatabaseUseCases?=null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments
        directorFullNameExtra = args?.getString(EXTRA_DIRECTOR_FULL_NAME)
        db = MovieDatabase.getDatabase(mContext!!, MainScope())
        usecase = DatabaseUseCases(db!!)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val alertDialog = AlertDialog.Builder(mContext)
        val view = layoutInflater.inflate(R.layout.dialog_director, null)
        val directorEdittext : EditText = view.findViewById(R.id.editDirectorFullName)
        if(directorEdittext != null){
            directorEdittext.setText(directorFullNameExtra)
            directorEdittext.setSelection(directorFullNameExtra!!.length)
        }

        val alerts = alertDialog.setView(view)
            .setTitle(R.string.dialog_director_title)
            .setPositiveButton(R.string.save, ({dialog, which ->
                saveDirector(directorEdittext.text.toString())
            }))
            .setNegativeButton(
                R.string.cancel, ({ dialog, _ ->
                    dialog.cancel()}))
        return alerts.create()
    }


    private fun update(directors: Directors) = MainScope().
        launch(Dispatchers.IO){
        usecase?.update(directors)
    }

    private fun insert(directors: Directors) = MainScope().
        launch(Dispatchers.IO){
        usecase?.insertDir(directors)
    }


    fun saveDirector(fullName: String) = MainScope().launch(Dispatchers.IO){
        saveDirectors(fullName)
    }

     suspend fun saveDirectors(fullName : String){
        if(TextUtils.isEmpty(fullName)){
            return
        }
        if(directorFullNameExtra != null){
            val directortoUpdate = usecase?.findDirectorByNames(directorFullNameExtra!!)
            if(directortoUpdate != null){
                if(directortoUpdate.fullname != fullName){
                    directortoUpdate.fullname = fullName
                    update(directortoUpdate)
                }
            }
        }else{
            insert(Directors(fullName))
        }
    }


    companion object{

        const val EXTRA_DIRECTOR_FULL_NAME = "director_full_name"
        const val TAG_DIALOG_DIRECTOR_SAVE = "dialog_director_save"

        /*
        fun newInstance(directorFullName : String) : DirectorSaveFragment{
            val fragment = DirectorSaveFragment()
            var args = Bundle()
            args.putString(EXTRA_DIRECTOR_FULL_NAME, directorFullName)
            fragment.arguments = args
            return fragment
        }

        */
    }
}