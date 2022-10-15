package com.ck.myapplication.sample.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ck.myapplication.R
import com.ck.myapplication.base.utils.observeEvent
import com.ck.myapplication.databinding.ActivityMainBinding
import com.ck.myapplication.sample.model.SampleActivityViewStateModel
import com.ck.myapplication.sample.ui.fragment.ListFragment
import com.ck.myapplication.sample.ui.fragment.PickerListFragment
import com.ck.myapplication.sample.viewmodel.SampleActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: SampleActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.viewLiveState.observeEvent(lifecycleOwner = this) { state ->
            when (state) {
                is SampleActivityViewStateModel.Picker -> supportFragmentManager.beginTransaction()
                    .add(
                        R.id.container,
                        PickerListFragment.newInstance(),
                        PickerListFragment.TAG
                    ).commit()
                else -> supportFragmentManager.beginTransaction().add(
                    R.id.container,
                    ListFragment.newInstance(pickerState = state),
                    ListFragment.TAG
                ).addToBackStack(ListFragment.TAG).commit()
            }
        }
    }
}
