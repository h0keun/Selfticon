package com.com.mygifticon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.com.mygifticon.databinding.ActivitySubBinding

class SubActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySubBinding.inflate(layoutInflater)

/*        binding.button.setOnClickListener{
            var where = binding.data11.text.toString()
            var name = binding.data22.text.toString()
            var price = binding.data33.text.toString()

            val intent2 = Intent()
            intent2.putExtra("where", where)
            intent2.putExtra("name", name)
            intent2.putExtra("price", price)

            setResult(RESULT_OK, intent2)
            finish()
        }*/
        setContentView(binding.root)
    }
}