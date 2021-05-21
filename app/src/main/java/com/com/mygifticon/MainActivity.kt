package com.com.mygifticon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.com.mygifticon.list.ListFragment
import com.com.mygifticon.make.MakeActivity
import com.com.mygifticon.scan.ScanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val goIntent = Intent(this, MakeActivity::class.java)
            startActivity(goIntent)
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(1).isEnabled = false

        val listFragment = ListFragment()
        val scanFragment = ScanFragment()

        changeFragment(listFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.list -> changeFragment(listFragment)
                R.id.scan -> changeFragment(scanFragment)
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.frame, fragment)
                commit()
            }
    }
}



