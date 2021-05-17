package com.com.mygifticon

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.com.mygifticon.DBKey.Companion.DB_ARTICLES
import com.com.mygifticon.databinding.ActivityGifticonBinding
import com.com.mygifticon.make.MakeActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlin.collections.HashMap

class GifticonActivity : AppCompatActivity() {

    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }

    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DBKey.DB_ARTICLES)
    }

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
        val qrId = "상품명 : ${gifticonData.gift_title}\n상품설명 : ${gifticonData.gift_explain}"
        createQRcode(img, qrId)

        Glide
            .with(binding.giftImageUrl.context)
            .load(gifticonData.gift_imageUrl)
            .into(binding.giftImageUrl)

        binding.deleteButton.setOnClickListener {
            val imageName =
                "${gifticonData.gift_title}${gifticonData.gift_price}${gifticonData.gift_sellerId}"
            storage.reference.child("article/photo").child("${imageName}.png").delete()
                .addOnSuccessListener {
                    articleDB.child(imageName).removeValue()
                    val nextIntent = Intent(this, MainActivity::class.java)
                    nextIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(nextIntent)
                }.addOnFailureListener {

                }
        }

        binding.shareButton.setOnClickListener {
            binding.captureLayout
            shareButtonClicked()
        }
    }

    private fun createQRcode(img: ImageView, qrId: String) {
        val multiFormatWriter = MultiFormatWriter()

        try {
            val hints = HashMap<EncodeHintType, Any>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"

            val bitMatrix: BitMatrix =
                multiFormatWriter.encode(qrId, BarcodeFormat.QR_CODE, 200, 200, hints)
            val barcodeEncoder: BarcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
            img.setImageBitmap(bitmap)

        } catch (e: Exception) { }
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