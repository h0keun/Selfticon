package com.com.mygifticon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.com.mygifticon.databinding.ActivityMakeBinding


class MakeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMakeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}