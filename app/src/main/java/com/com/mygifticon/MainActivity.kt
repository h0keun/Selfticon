package com.com.mygifticon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragment_list by lazy {ListFragment()}
    private val fragment_scan by lazy {ScanFragment()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(1).isEnabled = false

        initNavigationBar()
    }

    private fun initNavigationBar(){
        bottomNavigationView.run{
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
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}


