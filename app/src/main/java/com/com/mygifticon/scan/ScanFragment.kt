package com.com.mygifticon.scan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.com.mygifticon.list.ListFragment
import com.com.mygifticon.R
import com.google.zxing.EncodeHintType
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScanFragment : Fragment(R.layout.fragment_scan) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //val integrator = IntentIntegrator(activity)
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(true)
        integrator.setPrompt("QR코드를 인증해주세요.")
        integrator.initiateScan()

    }

    // qr코드 주소 반환 시에
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result: IntentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        val hints = HashMap<Any, Any>()
        hints[EncodeHintType.CHARACTER_SET] = "utf-8"
        if (result.contents == null) {
            // qr코드에 주소가 없거나, 뒤로가기 클릭 시
            Toast.makeText(context, "QR코드 인증이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            goFragment(listFragment = ListFragment())
        } else {
            //qr코드에 주소가 있을때 -> 주소에 관한 Toast 띄우는 함수 호출
            Toast.makeText(context, "QR코드 스캔결과 \n" + result.contents, Toast.LENGTH_LONG)
                .show()

        }

    }


    private fun goFragment(listFragment: ListFragment) {

        activity?.supportFragmentManager?.beginTransaction()
            ?.apply {
                replace(R.id.frame, listFragment)
                commit()
            }

    }

}