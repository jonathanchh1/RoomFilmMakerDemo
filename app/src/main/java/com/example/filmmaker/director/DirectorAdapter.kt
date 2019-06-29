package com.example.filmmaker.director

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.filmmaker.R
import com.example.filmmaker.databinding.ItemListDirectorBinding
import com.example.filmmaker.db.Directors


class DirectorAdapter(private var directorList: List<Directors>) : RecyclerView.Adapter<DirectorAdapter.DirectorViewBinding>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectorViewBinding {
       val binding : ItemListDirectorBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
           R.layout.item_list_director, parent, false)
        return DirectorViewBinding(binding)
    }

    override fun onBindViewHolder(holder: DirectorViewBinding, position: Int) {
        return holder.bind(directorList[position])
    }


    internal fun directorList(list : List<Directors>){
        this.directorList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemCount(): Int {
        return directorList.size
    }

    inner class DirectorViewBinding(var binding : ItemListDirectorBinding) : RecyclerView.ViewHolder(binding.root){
          fun bind(director : Directors){
              if(binding.directorViewModel == null){
                  binding.directorViewModel = PresentViewModel(itemView.context, director)
              }else{
                  binding.directorViewModel!!.director = director
              }
          }
    }
}