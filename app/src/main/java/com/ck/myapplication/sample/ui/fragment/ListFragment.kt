package com.ck.myapplication.sample.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ck.myapplication.base.utils.observe
import com.ck.myapplication.databinding.ContentMainBinding
import com.ck.myapplication.sample.model.SampleActivityViewStateModel
import com.ck.myapplication.sample.ui.ItemDecorationDivider
import com.ck.myapplication.sample.ui.adapter.SquaresAdapter
import com.ck.myapplication.sample.ui.adapter.TestApiAdapter
import com.ck.myapplication.sample.viewmodel.MyViewModel
import com.ck.myapplication.sample.viewmodel.MyViewModel.Companion.ARGUMENT_PICK_STATE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    companion object {
        val TAG: String = ListFragment::class.java.name
        fun newInstance(
            pickerState: SampleActivityViewStateModel
        ): ListFragment = ListFragment().apply {
            arguments = bundleOf(
                ARGUMENT_PICK_STATE to pickerState
            )
        }
    }

    private val viewModel: MyViewModel by viewModels()
    private var binding: ContentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lists?.apply {
            val state = arguments?.getParcelable<SampleActivityViewStateModel>(ARGUMENT_PICK_STATE)
            val spanCount =
                if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
            val orientation =
                if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) GridLayoutManager.VERTICAL else GridLayoutManager.HORIZONTAL
            layoutManager = GridLayoutManager(requireActivity(), spanCount)
            adapter = when (state) {
                null, is SampleActivityViewStateModel.SquareList -> SquaresAdapter()
                is SampleActivityViewStateModel.ApiPhotoList -> TestApiAdapter(
                    isRounding = state.isRounding
                ) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                else -> SquaresAdapter()
            }
            addItemDecoration(ItemDecorationDivider(requireContext(), orientation))
            viewModel.squaresListLiveData.observe(viewLifecycleOwner) {
                (adapter as SquaresAdapter).submitList(it)
            }
            viewModel.hitListLiveData.observe(lifecycleOwner = viewLifecycleOwner) {
                (adapter as TestApiAdapter).items = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}