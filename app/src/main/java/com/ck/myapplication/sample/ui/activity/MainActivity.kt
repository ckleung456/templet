package com.ck.myapplication.sample.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ck.myapplication.sample.ui.fragment.ListFragment
import com.ck.myapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(
            R.id.container,
            ListFragment.newInstance(),
            ListFragment.TAG
        ).commit()
    }
}
