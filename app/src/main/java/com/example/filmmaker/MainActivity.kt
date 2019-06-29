package com.example.filmmaker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.filmmaker.databinding.ActivityMainBinding
import com.example.filmmaker.db.MovieDatabase
import com.example.filmmaker.director.DirectorListFragment
import com.example.filmmaker.director.DirectorSaveFragment
import com.example.filmmaker.director.DirectorSaveFragment.Companion.TAG_DIALOG_DIRECTOR_SAVE
import com.example.filmmaker.film.FilmListFragment
import com.example.filmmaker.film.FilmSaveDialogFragment
import com.example.filmmaker.film.FilmSaveDialogFragment.Companion.TAG_DIALOG_MOVIE_SAVE
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope

class MainActivity : AppCompatActivity() {


    private var ShowFilms = true
    private var shownFragment : Fragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding : ActivityMainBinding =  DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        initializeView()
        if (savedInstanceState == null) {
            showFragment(FilmListFragment.newInstance())
        }
        toolbarsetUp(getString(R.string.filmmakers))
    }


    private fun toolbarsetUp(title : String){
        setSupportActionBar(main_toolbar)
        supportActionBar?.title = title
    }


   private fun initializeView() {
        val navigation: BottomNavigationView = findViewById(R.id.navigations)
        navigation.setOnNavigationItemSelectedListener{
            when (it.itemId){

                R.id.navigation_movies ->{
                    ShowFilms = true
                    showFragment(FilmListFragment.newInstance())
                }
                R.id.navigation_directors ->{
                    ShowFilms = false
                    showFragment(DirectorListFragment.newInstance())
                }
            }
            true

        }

       val fab : FloatingActionButton = findViewById(R.id.fab)
       fab.setOnClickListener {
           showSaveDialog()
       }
    }

    private fun showFragment(fragment : Fragment){
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragmentHolder, fragment)
        fragmentTrans.commitNow()
        shownFragment = fragment
    }

    private fun showSaveDialog(){
        val dialogFragment : DialogFragment
        val tag : String
        if(ShowFilms){
            dialogFragment = FilmSaveDialogFragment()
            tag = TAG_DIALOG_MOVIE_SAVE
        }else{
            dialogFragment  = DirectorSaveFragment()
            tag = TAG_DIALOG_DIRECTOR_SAVE
        }

        dialogFragment.show(supportFragmentManager, tag)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.action_delete_list){
            deletecurrentListData()
            return true
        }else if(id == R.id.action_re_create_database){
            reCreateDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deletecurrentListData(){
        if(ShowFilms){
            (shownFragment as FilmListFragment).removeData()
        }else{
            (shownFragment as DirectorListFragment).removeData()
        }
    }

    private fun reCreateDatabase(){
        MovieDatabase.getDatabase(this, MainScope()).clearAllTables()
    }
}
