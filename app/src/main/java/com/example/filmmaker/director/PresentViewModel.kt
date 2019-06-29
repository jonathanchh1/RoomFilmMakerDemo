package com.example.filmmaker.director

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.filmmaker.db.Directors

class PresentViewModel constructor(var context: Context, var director : Directors) : ViewModel(){

    fun fullname() = director.fullname

    fun onDialogClick(v: View){
        v.apply {
            val fragment = DirectorSaveFragment()
            val args = Bundle()
            args.putString(DirectorSaveFragment.EXTRA_DIRECTOR_FULL_NAME, director.fullname)
            fragment.arguments = args
            fragment.show((context as AppCompatActivity).supportFragmentManager,
                DirectorSaveFragment.TAG_DIALOG_DIRECTOR_SAVE)
        }
    }

    }
