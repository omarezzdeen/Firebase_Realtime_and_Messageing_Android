package com.example.firebaserealtimeandmessageingandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaserealtimeandmessageingandroid.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvData.text = "مرحبا عمر"

        // see you man, hhhh

    }
}