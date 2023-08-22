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
import com.oropeza.ec04_asot.databinding.FragmentTitanListBinding

class TitanListFragmnet : Fragment() {

    private lateinit var binding: FragmentTitanListBinding
    private lateinit var viewModel: TitanListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(TitanListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTitanListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RVTitanListAdapter(listOf()) {titan ->
            val titanDetailFragment = TitanListFragmnetDirections.actionTitanListFragmnetToTitanDetailFragment(titan)
            findNavController().navigate(titanDetailFragment)
        }
        binding.rvTitanList.adapter = adapter
        binding.rvTitanList.layoutManager = GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL,false)
        viewModel.titanList.observe(requireActivity()) {
            adapter.titans = it
            adapter.notifyDataSetChanged()
        }
        viewModel.getTitansFromService()
    }
}