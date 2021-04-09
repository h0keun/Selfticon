package com.com.mygifticon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.com.mygifticon.databinding.ActivityMainBinding

class TestData(
    private var data0: String? =null,
    private var data1: String? = null,
    private var data2: String? = null,
    private var data3: String? = null )
{
    fun getData0(): String? { return data0 }
    fun setData0(image: String) { this.data0 = data0 }
    fun getData1(): String? { return data1 }
    fun setData1(name: String) { this.data1 = data1 }
    fun getData2(): String? { return data2 }
    fun setData2(address: String) { this.data2 = data2 }
    fun getData3(): String? { return data3 }
    fun setData3(type: String) { this.data3 = data3 }
}

class MainActivity : AppCompatActivity() {

    private val fragment_list by lazy {ListFragment()}
    private val fragment_scan by lazy {ScanFragment()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.fab.setOnClickListener {

        }
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

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
    var dataList: ArrayList<TestData> = arrayListOf(
        TestData("dd","업체명1", "상품정보1", "가격1"),
        TestData("dd","업체명2", "상품정보2", "가격2"),
        TestData("dd","업체명3", "상품정보3", "가격3"),
        TestData("dd","업체명4", "상품정보4", "가격4"),
            TestData("dd","업체명1", "상품정보1", "가격1"),
            TestData("dd","업체명2", "상품정보2", "가격2"),
            TestData("dd","업체명3", "상품정보3", "가격3"),
            TestData("dd","업체명4", "상품정보4", "가격4"),
            TestData("dd","업체명1", "상품정보1", "가격1"),
            TestData("dd","업체명2", "상품정보2", "가격2"),
            TestData("dd","업체명3", "상품정보3", "가격3"),
            TestData("dd","업체명4", "상품정보4", "가격4"),
            TestData("dd","업체명1", "상품정보1", "가격1"),
            TestData("dd","업체명2", "상품정보2", "가격2"),
            TestData("dd","업체명3", "상품정보3", "가격3"),
            TestData("dd","업체명4", "상품정보4", "가격4"),

        )
    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
        intent.putExtra("DataList",dataList)
    }
}


