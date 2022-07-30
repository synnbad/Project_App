package com.example.projectapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapp.R
import com.example.projectapp.model.Advert
import com.example.projectapp.model.User


class CandidatesAdapter(private val dataSet: ArrayList<User>) :
    RecyclerView.Adapter<CandidatesAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val email: TextView
        val phone: TextView

        init {
            // Define click listener for the ViewHolder's View.
            name = view.findViewById(R.id.name)
            email = view.findViewById(R.id.email)
            phone = view.findViewById(R.id.phone)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.applicants_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.text = dataSet[position].name
        viewHolder.email.text = dataSet[position].email
        viewHolder.phone.text = dataSet[position].phone
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
