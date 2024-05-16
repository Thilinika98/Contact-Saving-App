package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: ContactDatabaseHelper
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ContactDatabaseHelper(this)
        contactAdapter = ContactAdapter(db.getAllContact(),this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = contactAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this,add_details::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        contactAdapter.refreshData(db.getAllContact())
    }
}