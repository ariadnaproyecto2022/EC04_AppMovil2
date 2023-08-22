package com.oropeza.ec04_asot.view

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.oropeza.ec04_asot.databinding.ActivityAddTitanBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class AddTitanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTitanBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storageRef: StorageReference

    private var selectedImageUri: Uri? = null

    private val PICK_IMAGE_REQUEST = 1
    private val TAKE_PICTURE_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTitanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = Firebase.firestore
        storageRef = FirebaseStorage.getInstance().reference

        binding.btnAddImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        binding.btnTakePhoto.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, TAKE_PICTURE_REQUEST)
            } else {
                Toast.makeText(this, "No se pudo abrir la cámara", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegisterTitan.setOnClickListener {
            val name: String = binding.tilName.editText?.text.toString()
            val gender: String = binding.tilGender.editText?.text.toString()
            val birthplace: String = binding.tilBirthplace.editText?.text.toString()
            val status: String = binding.tilStatus.editText?.text.toString()
            val occupation: String = binding.tilOccupation.editText?.text.toString()

            if (name.isNotEmpty() && gender.isNotEmpty() && birthplace.isNotEmpty() && status.isNotEmpty() && occupation.isNotEmpty()) {
                val titan = hashMapOf(
                    "img" to "",  // Leave it as an empty string for now
                    "name" to name,
                    "gender" to gender,
                    "birthplace" to birthplace,
                    "status" to status,
                    "occupation" to occupation
                )

                if (selectedImageUri != null) {
                    val imageRef = storageRef.child("titan_images/${UUID.randomUUID()}")
                    imageRef.putFile(selectedImageUri!!)
                        .addOnSuccessListener { taskSnapshot ->
                            imageRef.downloadUrl.addOnSuccessListener { uri ->
                                val imageUrl = uri.toString()
                                titan["img"] = imageUrl  // Update the image URL in the HashMap
                                saveTitanToFirestore(titan)
                            }
                        }
                        .addOnFailureListener {
                            // Handle the image upload failure
                        }
                } else {
                    saveTitanToFirestore(titan)
                }
            }
        }
    }

    private fun saveTitanToFirestore(titan: HashMap<String, String>) {
        firestore.collection("titan")
            .add(titan)
            .addOnSuccessListener {
                Toast.makeText(this, "Agregado Correctamente con id: ${it.id}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al Agregar Elemento", Toast.LENGTH_SHORT).show()
            }
    }
    // Foto Galeria
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            binding.ivImage.setImageURI(selectedImageUri)
        } else if (requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageBitmap = data.extras?.get("data") as Bitmap
            // Aquí puedes hacer algo con la imagen capturada, como mostrarla en un ImageView
            binding.ivImage.setImageBitmap(imageBitmap)

            // Convertir el Bitmap a una Uri para guardarla en la base de datos si es necesario
            selectedImageUri = bitmapToUri(imageBitmap)
        }
    }

    private fun bitmapToUri(bitmap: Bitmap): Uri {
        val filesDir = applicationContext.filesDir
        val imageFile = File(filesDir, "image.jpg")

        val outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return FileProvider.getUriForFile(this, "titanpaquete.provider", imageFile)
    }
}