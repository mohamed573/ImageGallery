package com.example.githubapiparsing.overview


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.example.githubapiparsing.R
import com.example.githubapiparsing.databinding.FragmentHomepageBinding

/**
 * A simple [Fragment] subclass.
 */
class Homepage : Fragment() {
    private val viewModel: HomePageViewModel by lazy {
        ViewModelProviders.of(this).get(HomePageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val binding = FragmentHomepageBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        //initializing binding object viewmodel
        binding.viewModel = viewModel

        //setting up the recyclerview adapter
        val adapter = RecyclerViewAdapters(OnClickListener { viewModel.diplayUserDetails(it.users)})

        /**assigning the adapter object to the recyclerview adapter*/
        binding.avatarGrid.adapter = adapter

        viewModel.Users.observe(this, Observer {
            adapter.addAndSubmitNewList(it)
        })

        //configuring the layout manager
        val manager = GridLayoutManager(context, 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
               return when(position) {
                    0 -> 2
                    else -> 1
                }
            }
        }
        binding.avatarGrid.layoutManager = manager

        viewModel.MoveUser.observe(this, Observer {
            if(null != it) {
                findNavController().navigate(HomepageDirections.actionHomepageToDetailFragment(it))
                viewModel.doneDisplayingUser()
            }
        })
        return binding.root
    }


}
