package com.com.mygifticon

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.com.mygifticon.databinding.ActivityGifticonBinding
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@Suppress("DEPRECATION")
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


        val sharedPreferencesState =
            getSharedPreferences("StateGiftIcon", 0).getString("state", "ListToGiftIcon")

        if (sharedPreferencesState == "ListToGiftIcon") {
            binding.ClickStateFromList.isVisible = true
            binding.ClickStateFromScan.isVisible = false
        } else {
            binding.ClickStateFromScan.isVisible = true
            binding.ClickStateFromList.isVisible = false
        }

        val gifticonData = intent.getSerializableExtra("gifticonModel") as GifticonModel

        binding.giftTitle.text = gifticonData.gift_title
        binding.giftExplain.text = gifticonData.gift_explain
        binding.giftPrice.text = gifticonData.gift_price
        binding.giftSellerId.text = gifticonData.gift_sellerId

        val img = binding.giftQR
        val qrId =
            "${gifticonData.gift_title},\n${gifticonData.gift_explain},\n${gifticonData.gift_price},\n${gifticonData.gift_sellerId},\n${gifticonData.gift_imageUrl}"
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
                    nextIntent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
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
                    val nextIntent2 = Intent(this, MainActivity::class.java)
                    nextIntent2.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(nextIntent2)
                    Toast.makeText(this, "기프티콘 사용완료.", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {

                }
        }

        binding.shareButton.setOnClickListener {
            val captureView = binding.captureLayout
            screenShareButtonClicked(captureView)
        }
    }

    private fun createQRcode(img: ImageView, qrId: String) {
        val multiFormatWriter = MultiFormatWriter()

        try {
            val hints = HashMap<EncodeHintType, Any>()
            hints[EncodeHintType.CHARACTER_SET] = "UTF-8"

            val bitMatrix: BitMatrix =
                multiFormatWriter.encode(qrId, BarcodeFormat.QR_CODE, 300, 300, hints)
            val barcodeEncoder: BarcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
            img.setImageBitmap(bitmap)

        } catch (e: Exception) {
        }
    }

    private fun screenShareButtonClicked(captureView: CardView) {



//        val bitmap: Bitmap = captureView.drawingCache
//        var fos: FileOutputStream
//        val captureView: View = window.decorView.rootView
        captureView.isDrawingCacheEnabled = true //화면에 뿌릴때 캐시를 사용하게 한다
        //captureView.buildDrawingCache() //캐시 비트 맵 만들기
        val screenBitmap: Bitmap = Bitmap.createBitmap(captureView.drawingCache)

        try {
            val cachePath = File(applicationContext.cacheDir, "images")
            cachePath.mkdirs() // don't forget to make the directory
            val stream =
                FileOutputStream("$cachePath/image.png") // overwrites this image every time
            screenBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
            val newFile = File(cachePath, "image.png")
            val contentUri: Uri = FileProvider.getUriForFile(
                applicationContext,
                "com.com.mygifticon.fileprovider", newFile
            )
            val shareIntent = Intent()
                .apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_STREAM, contentUri
                    )
                    type = "image/png"
                }
            startActivity(Intent.createChooser(shareIntent, "Share image"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val sharedPreferencesState =
            getSharedPreferences("StateGiftIcon", 0).getString("state", "ListToGiftIcon")
        if (sharedPreferencesState == "ListToGiftIcon") {
            finish()
        } else {
            val nextIntent3 = Intent(this, MainActivity::class.java)
            nextIntent3.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(nextIntent3)
            Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}