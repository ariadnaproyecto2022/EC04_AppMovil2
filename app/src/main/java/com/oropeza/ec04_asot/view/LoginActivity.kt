package com.oropeza.ec04_asot.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.oropeza.ec04_asot.R
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.oropeza.ec04_asot.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googlelauncher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharePreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        firebaseAuth = Firebase.auth
        sharePreferences = getSharedPreferences(SESSION_PREFERENCE, MODE_PRIVATE)
        googlelauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    signInFirebaseWithGoogle(account.idToken)
                } catch (e:Exception) {

                }
            }
        }
    }

    private fun signInFirebaseWithGoogle(idToken: String?) {
        val authCredential = GoogleAuthProvider.getCredential(idToken,null)
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                val user: FirebaseUser = firebaseAuth.currentUser!!
                // navegar a la siguiente pantalla
                // guardar en shared preferents
                sharePreferences.edit().apply {
                    putString(EMAIL,user.email).apply()
                }
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViews() {
        binding.tilEmail.editText?.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateInputs(text.toString(),binding.tilPassword.editText?.text.toString())
        }
        binding.tilPassword.editText?.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateInputs(binding.tilEmail.editText?.text.toString(),text.toString())
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                showEmptyFieldsAlert()
            } else if (isConnectedToInternet()) {
                signInWithEmailPassword()
            } else {
                showNoInternetAlert()
            }
        }
        binding.btnGoogle.setOnClickListener {
            if (isConnectedToInternet()) {
                singInWithGoogle()
            } else {
                showNoInternetAlert()
            }
        }
        binding.btnSignUp.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                showEmptyFieldsAlert()
            } else if (isConnectedToInternet()) {
                signUpWithEmailPassword()
            } else {
                showNoInternetAlert()
            }
        }
    }

    private fun signUpWithEmailPassword() {
        val email = binding.tilEmail.editText?.text.toString()
        val password = binding.tilPassword.editText?.text.toString()
        if (validateInputs(email, password)) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "El usuario no pudo ser creado", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Credenciales no validas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithEmailPassword() {
        val email = binding.tilEmail.editText?.text.toString()
        val password = binding.tilPassword.editText?.text.toString()
        signInFirebaseWithEmail(email, password)
    }

    private fun signInFirebaseWithEmail(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                val user = firebaseAuth.currentUser
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "El usuario se encontro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun singInWithGoogle() {
        val googleSignOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id))
            .requestEmail().build()
        val client: GoogleSignInClient = GoogleSignIn.getClient(this,googleSignOptions)
        val  intent = client.signInIntent
        googlelauncher.launch(intent)
    }

    private fun validateInputs(email:String, password:String): Boolean {
        val isEmailOk = !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordOk = password.length >= 6
        return isPasswordOk && isEmailOk
    }

    companion object {
        const val SESSION_PREFERENCE: String = "SESSION_PREFERENCES"
        const val EMAIL: String = "EMAIL"
    }

    // ------------------------
    // ALERTS
    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
    }

    private fun showNoInternetAlert() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("No hay conexión a Internet")
            .setMessage("Por favor, conecta tu dispositivo a Internet para acceder al sistema.")
            .setPositiveButton("Entendido") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    private fun showEmptyFieldsAlert() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Campos vacíos")
            .setMessage("Por favor, completa todos los campos.")
            .setPositiveButton("Entendido") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }
}