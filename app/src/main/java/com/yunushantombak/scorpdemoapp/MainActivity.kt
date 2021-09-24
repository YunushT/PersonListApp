package com.yunushantombak.scorpdemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yunushantombak.scorpdemoapp.adapter.IOnResponseErrorListener
import com.yunushantombak.scorpdemoapp.adapter.PersonListAdapter
import com.yunushantombak.scorpdemoapp.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IOnResponseErrorListener {
    lateinit var viewModel: MainActivityViewModel
    val adapter = PersonListAdapter()
    var isInitData: Boolean = true
    var isRefreshList: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val linearLayoutManager =
            object : LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
                override fun onLayoutCompleted(state: RecyclerView.State?) {
                    super.onLayoutCompleted(state)
                    if (isInitData)
                        checkMaxDataCount()
                }
            }

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getPersonList(null, this)
        swipeRefreshLayout.isRefreshing = true

        viewModel.personList.observe(this) {
            if (it != null) {
                adapter.updateList(it,isRefreshList)
                isRefreshList = false
            }
            swipeRefreshLayout.isRefreshing = false
            progressBar.visibility = View.GONE
            if (it.isEmpty()) {
                recyclerView.visibility = View.GONE
                empty_list_textview.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                empty_list_textview.visibility = View.GONE
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            isRefreshList = true
            viewModel.getPersonList(viewModel.nextState, this)
            swipeRefreshLayout.isRefreshing = true
        }


        val scrollListener = object : RecyclerView.OnScrollListener() {
            var isScrolling: Boolean = false
            var scrollingState: Int = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                scrollingState = newState
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !isScrolling) {
                    viewModel.getPersonList(viewModel.nextState, this@MainActivity);
                    progressBar.visibility = View.VISIBLE
                    progressBar.isIndeterminate = false

                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isScrolling = false
                }
                /* RecyclerView.SCROLL_STATE_DRAGGING
                 RecyclerView.SCROLL_STATE_IDLE
                 RecyclerView.SCROLL_STATE_SETTLING*/
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //Scrolling up
                if (dy > 0) {
                    isScrolling = true
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)


    }

    fun checkMaxDataCount() {
        var layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
        var itemCount = layoutManager.itemCount
        var childItemCount = layoutManager.childCount

        if (layoutManager.findLastVisibleItemPosition() != -1 && isInitData) {
            isInitData = false
            if (itemCount == childItemCount && childItemCount > 0) {
                viewModel.getPersonList(viewModel.nextState, this)
            }
        }

    }


    override fun onError() {
        swipeRefreshLayout.isRefreshing = false
        progressBar.visibility = View.GONE
        Snackbar.make(constraint_layout, "An Error Occured.", Snackbar.LENGTH_LONG).let {
            it.setAction("Retry") {
                viewModel.getPersonList(viewModel.nextState, this)
                progressBar.visibility = View.VISIBLE
            }
        }.show()
    }
}