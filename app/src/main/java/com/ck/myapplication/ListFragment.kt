package com.ck.myapplication

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_main.*

@AndroidEntryPoint
class ListFragment: Fragment() {
    companion object {
        val TAG: String = ListFragment::class.java.name
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    private var squareAdapter: SquaresAdapter? = null
    private val viewModel: MyViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.content_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lists?.apply {
            val spanCount = if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
            val orientation = if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) GridLayoutManager.VERTICAL else GridLayoutManager.HORIZONTAL
            layoutManager = GridLayoutManager(requireActivity(), spanCount)
            if (squareAdapter == null) {
                squareAdapter = SquaresAdapter()
                adapter = squareAdapter
            }
            addItemDecoration(ItemDecorationDivider(requireContext(), orientation))
        }

        viewModel.getSquaresList().observe(viewLifecycleOwner) {
            squareAdapter?.submitList(it)
        }
    }
}