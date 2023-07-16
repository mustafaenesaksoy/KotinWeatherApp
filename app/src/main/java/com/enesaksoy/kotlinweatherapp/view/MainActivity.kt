package com.enesaksoy.kotlinweatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enesaksoy.kotlinweatherapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}