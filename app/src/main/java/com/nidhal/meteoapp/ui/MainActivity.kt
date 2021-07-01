package com.nidhal.meteoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nidhal.meteoapp.R
import com.nidhal.meteoapp.utils.NetworkHelper
import com.nidhal.meteoapp.utils.loadFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment {
            replace(R.id.fragmentHost, WelcomeFragment(NetworkHelper(applicationContext)))
        }

    }
}