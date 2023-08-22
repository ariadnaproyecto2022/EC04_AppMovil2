package com.oropeza.ec04_asot.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.oropeza.ec04_asot.R
import com.oropeza.ec04_asot.RVTitanDBListAdapter
import com.oropeza.ec04_asot.databinding.FragmentTitanBDListBinding
import com.oropeza.ec04_asot.model.TitanFirebase

class TitanBDListFragment : Fragment() {

    private lateinit var binding: FragmentTitanBDListBinding
    private lateinit var db: FirebaseFirestore
    private var titansDB: MutableList<TitanFirebase> = mutableListOf()
    private lateinit var adapter: RVTitanDBListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTitanBDListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        val collection = db.collection("titan")

        binding.rvTitanDb.adapter = RVTitanDBListAdapter(listOf())
        val linearManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.rvTitanDb.layoutManager = linearManager
        if (::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        }

        adapter = RVTitanDBListAdapter(titansDB)
        binding.rvTitanDb.adapter = adapter

        collection.addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            value?.let {
                titansDB.clear()
                for (document in it) {
                    val img = document.getString("img") ?: ""
                    val name = document.getString("name") ?: ""
                    val gender = document.getString("gender") ?: ""
                    val birthplace = document.getString("birthplace") ?: ""
                    val status = document.getString("status") ?: ""
                    val occupation = document.getString("occupation") ?: ""
                    val titanAdd = TitanFirebase(img,name,gender,birthplace,status,occupation)
                    titansDB.add(titanAdd)
                }
                adapter.notifyDataSetChanged()
            }
        }
    }
}