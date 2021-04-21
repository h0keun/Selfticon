package com.com.mygifticon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.com.mygifticon.databinding.ActivityMainBinding
import com.com.mygifticon.scan.ScanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val nextIntent = Intent(this, SubActivity::class.java)
            startActivity(nextIntent)
        }
        val listFragment = ListFragment()
        val scanFragment = ScanFragment()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(1).isEnabled = false

        bottomNavigationView.run{
            setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.list -> changeFragment(listFragment)
                    R.id.scan -> changeFragment(scanFragment)
                }
                true
            }
            selectedItemId = R.id.list
        }
    }
/*

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1234 && resultCode == RESULT_OK){
            var data11 = data?.getStringExtra("where")
            var data22 = data?.getStringExtra("name")
            var data33 = data?.getStringExtra("price")
        }
    }

    var dataList: ArrayList<TestData> = arrayListOf(
            TestData("업체명1", "상품정보1", "가격1"),
            TestData("업체명2", "상품정보2", "가격2"),
            TestData("업체명3", "상품정보3", "가격3"),
            TestData("업체명4", "상품정보4", "가격4")
    )
*/

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .apply{
                replace(R.id.frame, fragment)
                commit()
                //intent.putExtra("DataList",dataList)
            }
    }
}



