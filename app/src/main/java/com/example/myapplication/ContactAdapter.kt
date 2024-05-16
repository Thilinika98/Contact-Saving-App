package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private var contacts: List<Contact>, context: Context) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val db: ContactDatabaseHelper = ContactDatabaseHelper(context)

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val fName : TextView = itemView.findViewById(R.id.fNameView)
        val lName : TextView = itemView.findViewById(R.id.lNameView)
        val phone : TextView = itemView.findViewById(R.id.phoneView)
        val disc : TextView = itemView.findViewById(R.id.discView)

        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val note = contacts[position]
        holder.fName.text = note.fName
        holder.lName.text = note.lName
        holder.phone.text = note.phone.toString()
        holder.disc.text = note.disc

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateContactActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteNote(note.id)
            refreshData(db.getAllContact())
            Toast.makeText(holder.itemView.context, "Delete Contact!",Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newContacts: List<Contact>){
        contacts = newContacts
        notifyDataSetChanged()
    }

}