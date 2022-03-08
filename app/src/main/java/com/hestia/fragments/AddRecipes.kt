package com.hestia.fragments


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.hestia.database.recipeDatabase
import com.hestia.databinding.FragmentAddRecipesBinding
import java.text.DateFormat
import java.util.*

class AddRecipes : Fragment() {
    var fileUri: Uri? = null
    var imageUrl: String? = null


    private lateinit var _binding: FragmentAddRecipesBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecipesBinding.inflate(inflater, container, false)
        val view = binding.root
        requireActivity().title = "Upload New Recipe"
        setHasOptionsMenu(true)
        binding.selectImageButton.setOnClickListener { selectImage() }
       binding.uploadRecipeButton.setOnClickListener { uploadImage() }

        return view
    }

    private fun selectImage() {
        val photoPick = Intent(Intent.ACTION_PICK)
        photoPick.type = "image/*"
        startForResult.launch(photoPick)
    }
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            fileUri = result.data!!.data
            Glide.with(requireContext()).load(fileUri).into(binding.ivFoodImage)
        } else Toast.makeText(activity, "You didn't pick an image!", Toast.LENGTH_SHORT)
            .show()
    }

    private fun getFileExtension(mUri: Uri?): String? {
        val contentResolver = requireContext().contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(mUri!!))
    }

    fun uploadImage() {
        val storageReference = FirebaseStorage.getInstance()
            .reference.child("RecipeImage/")
        if (fileUri != null) {
            val storageReference2 = storageReference.child(
                System.currentTimeMillis().toString() + "." + getFileExtension(fileUri)
            )
            storageReference2.putFile(fileUri!!)
                .addOnSuccessListener {
                    storageReference2.downloadUrl.addOnSuccessListener { uri ->
                        imageUrl = uri.toString()
                        uploadRecipe()
                        Toast.makeText(activity, "Image uploaded", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(activity, "Please Select Image or Add Image Name", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun uploadRecipe() {
        val foodData = recipeDatabase(
            binding.txtRecipeName.text.toString(), binding.txtIngredients.text.toString(),
            binding.txtDirections.text.toString(), binding.txtDescription.text.toString(),
            binding.txtDifficulty.text.toString(),
            imageUrl
        )
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Loading")
        val loading = builder.create()
        loading.show()
        val currentDateTime = DateFormat.getDateTimeInstance()
            .format(Calendar.getInstance().time)
        FirebaseDatabase.getInstance().getReference("Recipe")
            .child(currentDateTime).setValue(foodData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Recipe uploaded successfully!", Toast.LENGTH_SHORT)
                        .show()
                    loading!!.dismiss()
                }
            }.addOnFailureListener { e: Exception ->
                Toast.makeText(
                    activity,
                    "Failed " + e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }



}
