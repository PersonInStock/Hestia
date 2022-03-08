package com.hestia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.hestia.R

class DetailsFragment : Fragment() {
    lateinit var foodTitle: TextView
    lateinit var foodDescription: TextView
    lateinit var foodIngredients: TextView
    lateinit var foodDirections: TextView
    lateinit var foodImage: ImageView
    private var imageUrl: String? = ""
    private var recipeTitle: String? = null
    private var recipeDescription: String? = null
    private var recipeIngredients: String? = null
    private var recipeDirections: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        requireActivity().title = "Recipe Details"
        setHasOptionsMenu(true)
        foodTitle = view.findViewById(R.id.txtTitle)
        foodDescription = view.findViewById(R.id.txtDescription)
        foodIngredients = view.findViewById(R.id.txtIngredients)
        foodDirections = view.findViewById(R.id.txtDirections)
        foodImage = view.findViewById(R.id.ivImage2)
        recipeTitle = requireArguments().getString("Title")
        recipeDescription = requireArguments().getString("Description")
        recipeIngredients = requireArguments().getString("Ingredients")
        recipeDirections = requireArguments().getString("Directions")
        imageUrl = requireArguments().getString("Image")
        requireArguments().getString("keyValue")
        foodTitle.text = recipeTitle
        foodDescription.text = recipeDescription
        foodDirections.text = recipeDirections
        foodIngredients.text = recipeIngredients
        Glide.with(this).load(imageUrl).into(foodImage)
        recipeTitle = foodTitle.text.toString()
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(
            title: String?, ingredients: String?, description: String?,
            directions: String?, keyValue: String?, imgUrl: String?
        ): DetailsFragment {
            val detailsFragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putString("Title", title)
            bundle.putString("Ingredients", ingredients)
            bundle.putString("Description", description)
            bundle.putString("Directions", directions)
            bundle.putString("Image", imgUrl)
            bundle.putString("keyValue", keyValue)
            detailsFragment.arguments = bundle
            return detailsFragment
        }
    }
}