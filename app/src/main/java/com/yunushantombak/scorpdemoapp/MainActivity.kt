package com.yunushantombak.scorpdemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunushantombak.scorpdemoapp.adapter.PersonListAdapter
import com.yunushantombak.scorpdemoapp.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PersonListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.personList.observe(this){
            adapter.personList = it
        }





    }
}