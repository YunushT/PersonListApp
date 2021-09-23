package com.yunushantombak.scorpdemoapp.viewmodel

import com.yunushantombak.scorpdemoapp.repository.DataSource
import com.yunushantombak.scorpdemoapp.repository.FetchCompletionHandler
import com.yunushantombak.scorpdemoapp.repository.FetchError
import com.yunushantombak.scorpdemoapp.repository.FetchResponse
import com.yunushantombak.scorpdemoapp.repository.Person
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainActivityViewModel(var context: Application) : AndroidViewModel(context) {

    lateinit var personList: MutableLiveData<List<Person>>

    fun getPersonList() {
        val repository = DataSource()
        repository.fetch(null, object : FetchCompletionHandler {
            override fun invoke(p1: FetchResponse?, p2: FetchError?) {
                if (p1 != null) {
                    personList.value= p1.people
                }
            }

        })
    }
}