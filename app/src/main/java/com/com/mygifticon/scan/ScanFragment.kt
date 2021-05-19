package com.com.mygifticon.scan

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.com.mygifticon.DBKey.Companion.DB_ARTICLES
import com.com.mygifticon.GifticonActivity
import com.com.mygifticon.GifticonModel
import com.com.mygifticon.MainActivity
import com.com.mygifticon.list.ListFragment
import com.com.mygifticon.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScanFragment : Fragment(R.layout.fragment_scan) {

//    private lateinit var sharedPreferences: SharedPreferences
//    private lateinit var editor: SharedPreferences.Editor

    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DB_ARTICLES)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(true)
        integrator.setPrompt("QR코드를 스캔해주세요.")
        integrator.initiateScan()

    }

    // qr코드 주소 반환 시에
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result: IntentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val resultString = result.contents

        if (result.contents == null) {
            val nextIntent = Intent(context, MainActivity::class.java)
            nextIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(nextIntent)
            // goFragment(listFragment = ListFragment()) 에서 위의 방식으로 바꿈!!!
            // 사실 같은 액티비티에서 같은 액티비티를 호출하는게 좋은 방식은 아닌데
            // 기존에 프래그먼트를 이동하던 방식에서 액티비티를 재호출하는 방식으로 바꾼 이유는 프래그먼트를 의도적으로 이동시켰더니
            // 바텀네비게이션 아이콘에 res/drawable/selector_menu_color.xml 이 적용이 안되서 화면은 list 인데 아이콘은 scan 으로 가있는 등
            // UI적 요소가 통일이 안되어있는 점 때문에 액티비티 재호출방식을 적용함
            Toast.makeText(context, "QR코드 인증이 취소되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            val splitArray = resultString.split(" ")
            if (splitArray.size > 1) {
                goActivity(splitArray)
            } else {
                Toast.makeText(context, "QR코드 스캔결과 \n" + result.contents, Toast.LENGTH_SHORT).show()
                // todo 사이트 주소면 webView 액티비티 띄우고, 상품정보면 Retrofit 으로 검색정보 받아오는 액티비티 띄우기
            }
        }
    }

//    private fun goFragment(listFragment: ListFragment) {
//        activity?.supportFragmentManager?.beginTransaction()
//            ?.apply {
//                replace(R.id.frame, listFragment)
//                commit()
//            }
//    }

    private fun goActivity(splitArray: List<String>) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post =
                    dataSnapshot.child("${splitArray[0]}${splitArray[2]}${splitArray[3]}").value.toString()

                if (post != "null") {
                    val getGiftIcon = GifticonModel(
                        gift_title = splitArray[0],
                        gift_explain = splitArray[1],
                        gift_price = splitArray[2],
                        gift_sellerId = splitArray[3],
                        gift_imageUrl = splitArray[4]
                    )

                    val state = "ScanToGiftIcon"
                    val sharedPreferences = requireActivity().getSharedPreferences("StateGiftIcon", 0)
                    val editor = sharedPreferences.edit()
                    editor.putString("state",state)
                    editor.apply()

                    val intent = Intent(requireContext(), GifticonActivity::class.java)
                    intent.putExtra("gifticonModel", getGiftIcon)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    Toast.makeText(context, "QR코드 스캔결과", Toast.LENGTH_SHORT).show()
                } else {
                    //goFragment(listFragment = ListFragment())
                    val nextIntent = Intent(context, MainActivity::class.java)
                    nextIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(nextIntent)
                    Toast.makeText(context, "이미 사용 완료한 기프티콘 입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        articleDB.addValueEventListener(postListener)
    }
}
