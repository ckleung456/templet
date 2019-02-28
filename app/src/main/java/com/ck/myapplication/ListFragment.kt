package com.ck.myapplication

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.content_main.*

class ListFragment: Fragment() {
    companion object {
        val TAG: String = ListFragment::class.java.name
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    private var squareAdapter: SquaresAdapter? = null
    private var viewModel: MyViewModel? = null
    private var presenter: MyPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[MyViewModel::class.java]
        viewModel?.getSquaresList()?.observe(this, Observer {
            squareAdapter?.submitList(it)
        })
        if (viewModel != null) {
            presenter = MyPresenterImpl(viewModel!!)
            lifecycle.addObserver(presenter!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.content_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lists?.apply {
            val spanCount = if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
            val orientation = if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) GridLayoutManager.VERTICAL else GridLayoutManager.HORIZONTAL
            layoutManager = GridLayoutManager(activity!!, spanCount)
            if (squareAdapter == null) {
                squareAdapter = SquaresAdapter()
                adapter = squareAdapter
            }
            addItemDecoration(ItemDecorationDivider(context!!, orientation))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            lifecycle.removeObserver(presenter!!)
            presenter = null
        }
        viewModel?.getSquaresList()?.removeObservers(this)
    }
}