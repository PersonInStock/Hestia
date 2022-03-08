package com.hestia.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.core.Context
import com.hestia.adapters.RecipesAdapter
import com.hestia.database.recipeDatabase
import com.hestia.R
import java.util.*





class ViewAllRecipes : Fragment() {
    var mRecyclerView: RecyclerView? = null
    var myFoodList: MutableList<recipeDatabase?>? = null
    var myAdapter: RecipesAdapter? = null
    var databaseReference: DatabaseReference? = null
    var txtSearch: EditText? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Loading")
        val loading = builder.create()
        mRecyclerView = requireView().findViewById(R.id.recyclerView)
        mRecyclerView!!.layoutManager = GridLayoutManager(context, 1)
        myFoodList = ArrayList()
        myFoodList!!.clear()
        myAdapter = RecipesAdapter(context, myFoodList)
        mRecyclerView!!.adapter = myAdapter
        txtSearch = requireView().findViewById(R.id.txtSearch)
        databaseReference = FirebaseDatabase.getInstance().getReference("Recipe")
        loading.show()
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (itemSnapshot in snapshot.children) {
                    val foodData = itemSnapshot.getValue(recipeDatabase::class.java)
                    foodData!!.key = itemSnapshot.key
                    myFoodList!!.add(foodData)
                }
                myAdapter!!.notifyDataSetChanged()
                loading!!.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                loading!!.dismiss()
            }
        })
        myAdapter!!.notifyDataSetChanged()
        txtSearch!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_view_all_recipes, container, false)
    }

   
    private fun filter(text: String) {
        val filterList = ArrayList<recipeDatabase?>()
        for (item in myFoodList!!) {
            if (item!!.itemName!!.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault())) ||
                item.itemDifficulty!!.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
            ) {
                filterList.add(item)
            }
        }
        myAdapter!!.filteredList(filterList)
    }
}