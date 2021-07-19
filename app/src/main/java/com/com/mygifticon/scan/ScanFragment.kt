package com.com.mygifticon.scan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.com.mygifticon.*
import com.com.mygifticon.DBKey.Companion.DB_ARTICLES
import com.com.mygifticon.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScanFragment : Fragment(R.layout.fragment_scan) {

    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DB_ARTICLES)
    }

    companion object {
        private var titleDB: String? = null
        private var explainDB: String? = null
        private var priceDB: String? = null
        private var sellerDB: String? = null
        private var imageDB: String? = null

    }

    private val postListenerCheck = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val post =
                snapshot.child(titleDB + priceDB + sellerDB).value.toString()

            when {
                post != "null" -> {
                    val getGiftIcon = GifticonModel(
                        gift_title = titleDB,
                        gift_explain = explainDB,
                        gift_price = priceDB,
                        gift_sellerId = sellerDB,
                        gift_imageUrl = imageDB
                    )

                    val state = "ScanToGiftIcon"
                    val sharedPreferences =
                        requireActivity().getSharedPreferences("StateGiftIcon", 0)
                    val editor = sharedPreferences.edit()
                    editor.putString("state", state)
                    editor.apply()

                    val intent = Intent(
                        this@ScanFragment.requireContext(),
                        GifticonActivity::class.java
                    ).apply {
                        putExtra("gifticonModel", getGiftIcon)
                    }
                    startActivity(intent)
                    Toast.makeText(context, "QR코드 스캔결과", Toast.LENGTH_SHORT).show()

                }
                post == "null" -> {
                    val intent = Intent(
                        this@ScanFragment.requireContext(),
                        MainActivity::class.java
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                    Toast.makeText(context, "이미 사용 완료한 기프티콘 입니다.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }

        }

        override fun onCancelled(error: DatabaseError) {}
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(true)
        integrator.setPrompt("QR코드를 스캔해주세요.")
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result: IntentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val resultString = result.contents


        if (result.contents == null) {
            val nextIntent = Intent(context, MainActivity::class.java)
            nextIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(nextIntent)
            Toast.makeText(context, "QR코드 인증이 취소되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            val splitArray = resultString.split(",\n")
            if (splitArray.size > 2) {
                titleDB = splitArray[0]
                explainDB = splitArray[1]
                priceDB = splitArray[2]
                sellerDB = splitArray[3]
                imageDB = splitArray[4]
                articleDB.addValueEventListener(postListenerCheck)
            } else {
                Toast.makeText(context, "QR코드 스캔결과 \n" + result.contents, Toast.LENGTH_SHORT).show()
                
                val webIntent = Intent(context, WebViewActivity::class.java)
                webIntent.putExtra("QRtoWeb", result.contents)
                webIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(webIntent)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        articleDB.removeEventListener(postListenerCheck)
    }
}
