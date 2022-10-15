package com.ck.myapplication.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ck.myapplication.databinding.ContentMainBinding
import com.ck.myapplication.sample.model.SampleActivityViewStateModel
import com.ck.myapplication.sample.ui.adapter.PickerAdapter
import com.ck.myapplication.sample.viewmodel.SampleActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickerListFragment : Fragment() {
    companion object {
        val TAG: String = PickerListFragment::class.java.name

        fun newInstance() = PickerListFragment()
    }

    private var binding: ContentMainBinding? = null
    private val viewModel: SampleActivityViewModel by activityViewModels()

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
            layoutManager = LinearLayoutManager(requireActivity())
            PickerAdapter {
                viewModel.onStateChanged(viewState = it)
            }.apply {
                items = listOf(
                    SampleActivityViewStateModel.SquareList,
                    SampleActivityViewStateModel.ApiPhotoList(isRounding = false),
                    SampleActivityViewStateModel.ApiPhotoList(isRounding = true)
                )
            }.let {
                adapter = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}