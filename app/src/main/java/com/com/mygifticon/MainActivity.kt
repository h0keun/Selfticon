package com.com.mygifticon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.com.mygifticon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val fragment_list by lazy {ListFragment()}
    private val fragment_scan by lazy {ScanFragment()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        binding.fab.setOnClickListener{
            val intent = Intent(this, MakeActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigationView.run{
            setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.list ->{
                        changeFragment(fragment_list)
                    }
                    R.id.scan ->{
                        changeFragment(fragment_scan)
                    }
                }
                true
            }
            selectedItemId = R.id.list
        }

        setContentView(binding.root)
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}


