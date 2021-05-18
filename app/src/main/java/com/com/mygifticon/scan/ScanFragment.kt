package com.com.mygifticon.scan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.com.mygifticon.DBKey.Companion.DB_ARTICLES
import com.com.mygifticon.GifticonActivity
import com.com.mygifticon.GifticonModel
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
        val splitArray = resultString.split(" ")

        if (result.contents == null) {
            // qr코드에 주소가 없거나, 뒤로가기 클릭 시
            Toast.makeText(context, "QR코드 인증이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            goFragment(listFragment = ListFragment())
        } else {
            //qr코드에 주소가 있을때 -> 주소에 관한 Toast 띄우는 함수 호출
            if (splitArray.size > 1) {
                Toast.makeText(context, "QR코드 스캔결과", Toast.LENGTH_SHORT).show()
                goActivity(splitArray)
            } else {
                Toast.makeText(context, "QR코드 스캔결과 \n" + result.contents, Toast.LENGTH_SHORT).show()
                // todo 사이트 주소면 webView 액티비티 띄우고, 상품정보면 Retrofit 으로 검색정보 받아오는 액티비티 띄우기
            }
        }
    }

    private fun goFragment(listFragment: ListFragment) {

        activity?.supportFragmentManager?.beginTransaction()
            ?.apply {
                replace(R.id.frame, listFragment)
                commit()
            }
    }

    private fun goActivity(splitArray: List<String>) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.child("${splitArray[0]}${splitArray[2]}${splitArray[3]}").value.toString()

                if(post != "null"){
                    val getGiftIcon = GifticonModel(
                        gift_title = splitArray[0],
                        gift_explain = splitArray[1],
                        gift_price = splitArray[2],
                        gift_sellerId = splitArray[3],
                        gift_imageUrl = splitArray[4]
                    )

                    val intent = Intent(requireContext(), GifticonActivity::class.java)
                    intent.putExtra("gifticonModel", getGiftIcon)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "이미 사용 완료한 기프티콘 입니다.", Toast.LENGTH_SHORT).show()
                    goFragment(listFragment = ListFragment())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        articleDB.addValueEventListener(postListener)
    }
}
