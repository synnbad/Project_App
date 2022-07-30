package com.example.projectapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapp.R
import com.example.projectapp.model.Advert


class CustomAdapter(private val dataSet: ArrayList<Advert>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val jobTitle: TextView

        init {
            // Define click listener for the ViewHolder's View.
            jobTitle = view.findViewById(R.id.job_title)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.application_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.jobTitle.text = dataSet[position].jobTitle

        viewHolder.itemView.setOnClickListener {
            onClickListener.onClick(dataSet[position])
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


    class OnClickListener(val clickListener: (advert: Advert) -> Unit) {
        fun onClick(advert: Advert) = clickListener(advert)
    }
}
