package com.yunushantombak.scorpdemoapp.viewmodel

import com.yunushantombak.scorpdemoapp.repository.DataSource
import com.yunushantombak.scorpdemoapp.repository.FetchCompletionHandler
import com.yunushantombak.scorpdemoapp.repository.FetchError
import com.yunushantombak.scorpdemoapp.repository.FetchResponse
import com.yunushantombak.scorpdemoapp.repository.Person
import android.app.Application
import android.content.res.Resources
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.yunushantombak.scorpdemoapp.adapter.IOnResponseErrorListener

class MainActivityViewModel(var context: Application) : AndroidViewModel(context) {

    var personList = MutableLiveData<MutableList<Person>>()
    val repository = DataSource()
    var nextState: String? = null


    fun getPersonList(next: String?, onResponseError: IOnResponseErrorListener) {
        repository.fetch(next, object : FetchCompletionHandler {
            override fun invoke(p1: FetchResponse?, p2: FetchError?) {
                if (p1 != null) {
                    personList.postValue(p1.people as MutableList<Person>)
                    nextState = p1.next
                } else if (p2 != null) {
                    onResponseError.onError()
                    p2.errorDescription
                }
            }
        })
    }

}