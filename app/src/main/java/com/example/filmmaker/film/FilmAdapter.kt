package com.example.filmmaker.film

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.filmmaker.R
import com.example.filmmaker.databinding.ItemListMovieBinding
import com.example.filmmaker.db.Films


class FilmAdapter constructor(var context: Context, var list : List<Films>) : RecyclerView.Adapter<FilmAdapter.FilmViewBinding>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewBinding {
        val binding : ItemListMovieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_list_movie, parent, false)
        return FilmViewBinding(binding)
    }


    override fun onBindViewHolder(holder: FilmViewBinding, position: Int) {
        return holder.bind(list[position])
    }


    internal fun updatefilms(filmList : List<Films>){
        this.list = filmList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemCount(): Int {
        return list.size
    }

    inner class FilmViewBinding(val binding : ItemListMovieBinding) : RecyclerView.ViewHolder(binding.root){


        fun bind(films: Films){
            if(binding.movieViewModel == null){
                binding.movieViewModel = PresentFilmViewModel(itemView.context, films)
            }else{
                binding.movieViewModel!!.films = films
            }
        }
    }
}