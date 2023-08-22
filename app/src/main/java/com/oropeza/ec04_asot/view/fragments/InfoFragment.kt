package com.oropeza.ec04_asot.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.oropeza.ec04_asot.databinding.FragmentInfoBinding
import com.oropeza.ec04_asot.view.LoginActivity
import com.oropeza.ec04_asot.view.SplachScreenActivity

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = requireActivity().getSharedPreferences(LoginActivity.SESSION_PREFERENCE, Context.MODE_PRIVATE)
        val userEmail = sharedPrefs.getString(LoginActivity.EMAIL, "")

        binding.labelCorreo.text = userEmail

        binding.btnCloseSession.setOnClickListener {
            showLogoutConfirmationDialog()
        }
        /*
        val guardarEmail = getEmailFromSharedPreferences()
        binding.labelCorreo.text = guardarEmail

        binding.btnCloseSession.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Cerrar Sesion")
            alertDialogBuilder.setMessage("¿Seguro que quieres Cerrar Sesion?")

            alertDialogBuilder.setPositiveButton("Aceptar") { dialog,  _ ->
                Toast.makeText(requireContext(),"Sesion Cerrada Exitosa", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
                activity?.finish()
            }
            alertDialogBuilder.setNegativeButton("Cancelar") { dialog,  _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }*/
    }

    private fun showLogoutConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Cerrar Sesion")
        alertDialogBuilder.setMessage("¿Seguro que quieres Cerrar Sesion?")

        alertDialogBuilder.setPositiveButton("Si") { dialog,  _ ->
            performLogout()
        }
        alertDialogBuilder.setNegativeButton("Cancelar") { dialog,  _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun performLogout() {
        val sharedPrefs = requireActivity().getSharedPreferences(LoginActivity.SESSION_PREFERENCE, Context.MODE_PRIVATE)
        sharedPrefs.edit().remove(LoginActivity.EMAIL).apply()

        FirebaseAuth.getInstance().signOut()

        val intent = Intent(requireContext(), SplachScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun getEmailFromSharedPreferences(): String {
        val sharedPreferences = requireContext().getSharedPreferences("myPreferences",Context.MODE_PRIVATE)
        return sharedPreferences.getString("email","")?: ""
    }
}