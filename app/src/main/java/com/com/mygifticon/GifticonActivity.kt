package com.com.mygifticon

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.com.mygifticon.databinding.ActivityGifticonBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class GifticonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGifticonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gifticonData = intent.getSerializableExtra("gifticonModel") as GifticonModel

        binding.giftTitle.text = gifticonData.gift_title
        binding.giftExplain.text = gifticonData.gift_explain
        binding.giftPrice.text = gifticonData.gift_price
        binding.giftSellerId.text = gifticonData.gift_sellerId

        val img = binding.giftQR
        val qrId = "상품명 : ${binding.giftTitle}\n상품설명 : ${binding.giftExplain}"
        createQRcode(img, qrId)


        Glide
            .with(binding.giftImageUrl.context)
            .load(gifticonData.gift_imageUrl)
            .into(binding.giftImageUrl)


        binding.deleteButton.setOnClickListener {
            deleteButtonClicked()
        }

        binding.editButton.setOnClickListener {
            editButtonClicked()
        }


        binding.shareButton.setOnClickListener {
            binding.captureLayout
            shareButtonClicked()
        }
    }

    private fun createQRcode(img: ImageView, text: String) {
        val multiFormatWriter = MultiFormatWriter()

        try {
            val bitMatrix: BitMatrix =
                multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200)
            val barcodeEncoder: BarcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
            img.setImageBitmap(bitmap)

        } catch (e: Exception) {
        }
    }

    private fun deleteButtonClicked() {

    }

    private fun editButtonClicked() {

    }

    private fun shareButtonClicked() {

        val intent = Intent()
            .apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "[공유기능 테스트 지금은 텍스트지만 나중엔 이미지로]"
                )
                type = "text/plain"
            }
        startActivity(Intent.createChooser(intent, null))

    }

}