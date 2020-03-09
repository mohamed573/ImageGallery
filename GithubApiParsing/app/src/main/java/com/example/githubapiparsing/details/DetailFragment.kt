package com.example.githubapiparsing.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.githubapiparsing.R
import com.example.githubapiparsing.databinding.FragmentDetailBinding

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {
    private lateinit var binding : FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater,container, false )

        val user = DetailFragmentArgs.fromBundle(arguments!!).selectedUser

        val viewModelFactory = DetailFragmentViewModelFactory(user)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailFragmentViewModel::class.java)

        binding.viewModel = viewModel


        return binding.root
    }


}
