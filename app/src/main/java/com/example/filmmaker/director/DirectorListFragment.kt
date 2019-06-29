package com.example.filmmaker.director

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
import com.example.filmmaker.databinding.FragmentDirectorsBinding

class DirectorListFragment : Fragment() {

    private lateinit var adapter : DirectorAdapter
    private lateinit var viewModel: DirectorViewModels
    private var mContext : Context?=null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        adapter = DirectorAdapter(ArrayList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeview()
    }

    fun initializeview(){
        viewModel = ViewModelProviders.of(this).get(DirectorViewModels::class.java)
        loadingData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : FragmentDirectorsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext!!), R.layout.fragment_directors,
            container, false)
        binding.directorViewModel = viewModel
        binding.recyclerviewDirectors.adapter = adapter
        val layoutmanager = LinearLayoutManager(mContext)
        binding.recyclerviewDirectors.layoutManager = layoutmanager
        binding.recyclerviewDirectors.addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        return binding.root
    }

    fun loadingData(){
        viewModel.directorList.observe(this, Observer {
            it?.let {
                adapter.directorList(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    fun removeData(){
        if(viewModel != null){
            viewModel.deleteAll()
        }
    }

    companion object{
        fun newInstance() : DirectorListFragment{
            return DirectorListFragment()
        }
    }
}