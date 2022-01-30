package com.example.undefined.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.undefined.R
import com.example.undefined.model.Vacancy

class VacancyAdapter(private val context: Context, private val vacancyList: ArrayList<Vacancy>, private val listener: OnItemClickListener):
    RecyclerView.Adapter<VacancyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val title: TextView = view.findViewById(R.id.title)
        val description: TextView = view.findViewById(R.id.description)
        val imageView: ImageView = view.findViewById(R.id.imageView)

        private val item: CardView = view.findViewById(R.id.itemVacancy)

        init {
            item.setOnClickListener(this)

            item.findViewById<ImageView>(R.id.buttonSave).setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onSaveClick(position)
                }
            }

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentVacancy = vacancyList[position]

        holder.title.text = currentVacancy.title
        holder.description.text = currentVacancy.description

        Glide.with(context)
            .load(currentVacancy.imageUrl)
            .into(holder.imageView)

    }

    override fun getItemCount() = vacancyList.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onSaveClick(position: Int)
    }

}