package com.com.mygifticon

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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

        val sharedPreferencesState = getSharedPreferences("StateGiftIcon", 0).getString("state","")

        if(sharedPreferencesState == "ListToGiftIcon"){
            binding.ClickStateFromList.isVisible = true
            binding.ClickStateFromScan.isVisible = false
        }else{
            binding.ClickStateFromScan.isVisible = true
            binding.ClickStateFromList.isVisible = false
        }

        val gifticonData = intent.getSerializableExtra("gifticonModel") as GifticonModel

        binding.giftTitle.text = gifticonData.gift_title
        binding.giftExplain.text = gifticonData.gift_explain
        binding.giftPrice.text = gifticonData.gift_price
        binding.giftSellerId.text = gifticonData.gift_sellerId

        val img = binding.giftQR
        val qrId = "${gifticonData.gift_title} ${gifticonData.gift_explain} ${gifticonData.gift_price} ${gifticonData.gift_sellerId} ${gifticonData.gift_imageUrl}"
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
                    Toast.makeText(this, "삭제하였습니다.", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {

                }
        }

        binding.useButton.setOnClickListener {
            val imageName2 =
                "${gifticonData.gift_title}${gifticonData.gift_price}${gifticonData.gift_sellerId}"
            storage.reference.child("article/photo").child("${imageName2}.png").delete()
                .addOnSuccessListener {
                    articleDB.child(imageName2).removeValue()
                    val nextIntent = Intent(this, MainActivity::class.java)
                    nextIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(nextIntent)
                    Toast.makeText(this, "기프티콘 사용완료.", Toast.LENGTH_SHORT).show()
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

    override fun onBackPressed() {
        super.onBackPressed()
        val nextIntent = Intent(this, MainActivity::class.java)
        nextIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(nextIntent)
        // ScanFragment 에서 기프티콘을 스캔한후 기프티콘의 정보를 보여주기위해
        // 이 엑티비티로 넘어오게 되는데, 여기서 취소버튼을 눌럿을 경우
        // 기존에 zxing 의 CaptureActivity 를 담고있는 ScanFragment 를 띄우는게 아니라
        // CaptureActivity 는 종료된 상태로 비어있는 ScanFragment 만 보여지게 되어서 사용성이 저하되기 때문에
        // 이방식을 썼는데, 사실 이 부분은 layout.xml 에서 list 에서 호출했을 때와 scan 에서 호출했을 때 다른 화면을 보여줘서 해결가능할지도
    }
}