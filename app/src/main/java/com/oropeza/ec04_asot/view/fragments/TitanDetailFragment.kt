package com.oropeza.ec04_asot.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.oropeza.ec04_asot.R
import com.oropeza.ec04_asot.databinding.FragmentTitanDetailBinding
import com.oropeza.ec04_asot.model.AttackOnTitan

class TitanDetailFragment : Fragment() {

    private lateinit var binding: FragmentTitanDetailBinding
    val args: TitanDetailFragmentArgs by navArgs()
    private lateinit var titan: AttackOnTitan
    private lateinit var viewModel: TitanDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titan = args.titan
        viewModel = ViewModelProvider(requireActivity()).get(TitanDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTitanDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(binding.root)
            .load(titan.img)
            .into(binding.imgTitan)
        binding.txtName.text = titan.name
        binding.txtGender.text = "Gender: ${titan.gender}"
        binding.txtAge.text = "Age: ${titan.age} years old"
        binding.txtHeight.text = "Height: ${titan.height}"
        binding.txtBirthplace.text = "Birthplace: ${titan.birthplace}"
        binding.txtResidence.text = "Residence: ${titan.residence}"
        binding.txtStatus.text = "Status: ${titan.status}"
        binding.txtOccupation.text = "Occupation: ${titan.occupation}"
        binding.btnAddFavorite.apply {
            visibility = if (titan.isFavorite) View.GONE else  View.VISIBLE
        }
        binding.btnAddFavorite.setOnClickListener {
            titan.isFavorite = true
            viewModel.addFavorite(titan)

            Toast.makeText(
                requireContext(),
                "Personaje agregado a favoritos",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnRemoveFavorite.apply {
            visibility = if (titan.isFavorite) View.VISIBLE else View.GONE
        }
        binding.btnRemoveFavorite.setOnClickListener {
            titan.isFavorite = false
            viewModel.removeFavorite(titan)

            Toast.makeText(
                requireContext(),
                "Personaje eliminado de favoritos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}