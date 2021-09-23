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

class MainActivityViewModel(var context: Application) : AndroidViewModel(context) {

    var personList = MutableLiveData<List<Person>>()
    val repository = DataSource()


    fun getPersonList(next: String?) {
        repository.fetch(next, object : FetchCompletionHandler {
            override fun invoke(p1: FetchResponse?, p2: FetchError?) {
                if (p1 != null) {
                    personList.value = p1.people
                    p1.next
                } else if (p2 != null) {
                    Toast.makeText(context,"An Error Occured. Get Data Failed",Toast.LENGTH_SHORT).show()
                    //Snackbar.make(context,"An Error Occured. Get Data Failed")
                    p2.errorDescription
                }
            }

        })
    }

    private fun calcMaxItemCountOnScreen() {
        //var maxItemCount = getHeight() /
    }

    private fun getHeight(): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        return displayMetrics.heightPixels;
    }
}