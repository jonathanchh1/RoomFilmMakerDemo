package com.example.filmmaker.film

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmmaker.R
import com.example.filmmaker.databinding.FragmentMoviesBinding

class FilmListFragment : Fragment() {

    private lateinit var adapter : FilmAdapter
    private var mContext : Context?=null
    private lateinit var viewModel : FilmViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
        adapter = FilmAdapter(mContext!!, ArrayList())
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FilmViewModel::class.java)
        viewModel.filmList.observe(this, Observer{
            adapter.updatefilms(it)
            adapter.notifyDataSetChanged()
        })
    }




override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : FragmentMoviesBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
            R.layout.fragment_movies, container, false)
        binding.filmViewModel = viewModel
        binding.recyclerviewMovies.adapter = adapter
        binding.recyclerviewMovies.layoutManager = LinearLayoutManager(mContext)
        binding.recyclerviewMovies.addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        binding.lifecycleOwner = this

        return binding.root
    }

        fun removeData(){
            if(viewModel != null) {
                viewModel.deleteAll()
            }
        }


    companion object{
        fun newInstance() : FilmListFragment{
            return FilmListFragment()
        }
    }
}