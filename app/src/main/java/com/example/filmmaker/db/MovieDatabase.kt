package com.example.filmmaker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Films::class, Directors::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {


    abstract fun filmDao() : FilmDao
    abstract fun directorDao() : DirectorDao


    companion object{

        @Volatile
        private var INSTANCE : MovieDatabase?=null

        fun getDatabase(context: Context, scope: CoroutineScope) : MovieDatabase{

            return synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "table_room"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(MovieDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }


        class MovieDatabaseCallback(private var scope: CoroutineScope) : RoomDatabase.Callback(){

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let {
                    movieDatabase ->
                    scope.launch(Dispatchers.IO){
                        populateDatabase(movieDatabase.directorDao(), movieDatabase.filmDao())
                    }
                }
            }
        }


        suspend fun populateDatabase(directorDao: DirectorDao, filmDao: FilmDao){
            directorDao.deleteAll()
            filmDao.deleteAll()

            val director1 = Directors("Adam McKay")
            val director2 = Directors("Denis Villineuve")
            val director3 = Directors("Morten Tyldum")

            val movie1 = Films(directorDao.insert(director1).toInt(), "The Big Short" )
            val movieIds = directorDao.insert(director2).toInt()
            val movie2 = Films( movieIds, "Blade Runner 2049")
            val movie3 = Films( movieIds, "Arrival")
            val movie4 = Films( directorDao.insert(director3).toInt(), "Passengers")

            filmDao.insert(movie1, movie2, movie3, movie4)
        }
    }
}