package com.example.filmmaker

import android.app.Application
import android.content.Context


class FilmApp : Application(){

    override fun onCreate() {
        super.onCreate()
        var context = FilmApp
    }

    init {
        instance = this

    }

    companion object{
        var instance : FilmApp?=null
        fun appContext() : Context{
            return instance!!.applicationContext
        }
    }
}