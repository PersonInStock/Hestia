package com.hestia.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hestia.Interface.FoodViewHolder
import com.hestia.R
import com.hestia.database.recipeDatabase
import com.hestia.fragments.DetailsFragment.Companion.newInstance

class RecipesAdapter(private val context: Context?, private var myFoodList: MutableList<recipeDatabase?>?) :
    RecyclerView.Adapter<FoodViewHolder>() {
    private var lastPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_item, parent, false)
        return FoodViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, i: Int) {
        Glide.with(context!!).load(myFoodList!![i]!!.itemImage).into(holder.imageView)
        holder.mTitle.text = myFoodList!![i]!!.itemName
        holder.mDescription.text = myFoodList!![i]!!.itemDescription
        holder.mDifficulty.text = myFoodList!![i]!!.itemDifficulty
        holder.imageView.setOnClickListener {
            val title = myFoodList!![holder.adapterPosition]!!.itemName
            val ingredients = myFoodList!![holder.adapterPosition]!!.itemIngredients
            val description = myFoodList!![holder.adapterPosition]!!.itemDescription
            val directions = myFoodList!![holder.adapterPosition]!!.itemDirections
            val keyValue = myFoodList!![holder.adapterPosition]!!.key
            val imgUrl = myFoodList!![i]!!.itemImage
            val detailsFragment = newInstance(
                title, ingredients,
                description, directions, keyValue, imgUrl
            )
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.fl_wrapper, detailsFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        setAnimation(holder.itemView, i)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = ScaleAnimation(
                0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
            )
            animation.duration = 1500
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount(): Int {
        return myFoodList!!.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filteredList(filterList: ArrayList<recipeDatabase?>) {
        myFoodList = filterList
        notifyDataSetChanged()
    }
}