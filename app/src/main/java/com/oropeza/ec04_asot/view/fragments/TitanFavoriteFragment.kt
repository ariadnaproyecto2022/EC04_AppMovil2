package com.oropeza.ec04_asot.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oropeza.ec04_asot.RVTitanListAdapter
import com.oropeza.ec04_asot.databinding.FragmentTitanFavoriteBinding

class TitanFavoriteFragment : Fragment() {

    private lateinit var binding: FragmentTitanFavoriteBinding
    private  lateinit var viewModel: TitanFavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(TitanFavoriteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTitanFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adarter = RVTitanListAdapter(listOf()) {titan ->
            val titanDetailFragment = TitanFavoriteFragmentDirections.actionTitanFavoriteFragmentToDemonDetailFragment(titan)
            findNavController().navigate(titanDetailFragment)
        }
        binding.rvTitanFavorite.adapter = adarter
        binding.rvTitanFavorite.layoutManager = GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL, false)
        viewModel.favorites.observe(requireActivity()) {
            adarter.titans = it
            adarter.notifyDataSetChanged()
        }
        viewModel.getFavorites()
    }
}