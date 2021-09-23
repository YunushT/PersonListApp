package com.yunushantombak.scorpdemoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yunushantombak.scorpdemoapp.R
import com.yunushantombak.scorpdemoapp.databinding.PersonListItemBinding
import com.yunushantombak.scorpdemoapp.repository.Person

class PersonListAdapter : RecyclerView.Adapter<PersonListAdapter.PersonViewHolder>() {
    class PersonViewHolder(var view: PersonListItemBinding) : RecyclerView.ViewHolder(view.root) {

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var personList: List<Person>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<PersonListItemBinding>(
            inflater,
            R.layout.person_list_item,
            parent,
            false
        )
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.view.person = personList[position]
    }

    override fun getItemCount(): Int {
        return personList.size
    }

}