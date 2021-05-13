package com.com.mygifticon.scan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.ListFragment
import com.com.mygifticon.MainActivity
import com.com.mygifticon.R
import com.com.mygifticon.make.MakeActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScanFragment: Fragment(R.layout.fragment_scan) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val integrator = IntentIntegrator(activity)
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(true)
        integrator.setPrompt("QR코드를 인증해주세요.")
        integrator.initiateScan()

    }

    // qr코드 주소 반환 시에
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                // qr코드에 주소가 없거나, 뒤로가기 클릭 시
                Toast.makeText(context, "QR코드 인증이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                //qr코드에 주소가 있을때 -> 주소에 관한 Toast 띄우는 함수 호출
                Toast.makeText(context, "QR코드 스캔이 완료되었습니다." + result.contents, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

}