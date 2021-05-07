package com.com.mygifticon.make

import android.app.Activity
import android.app.AlertDialog
import android.content.AsyncQueryHandler
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.com.mygifticon.DBKey.Companion.DB_ARTICLES
import com.com.mygifticon.R
import com.com.mygifticon.databinding.ActivityMakeBinding
import com.com.mygifticon.list.ArticleModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MakeActivity : AppCompatActivity() {

    private var selectedUri: Uri? = null

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }

    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DB_ARTICLES)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMakeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.setImageButton.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startContentProvider()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1010
                    )
                }
            }
        }


        binding.makeButton.setOnClickListener {
            val title = binding.setTitle.editText?.text.toString()
            val explain = binding.setExplain.editText?.text.toString()
            val price = binding.setPrice.editText?.text.toString()
            val sellerId = binding.setSeller.editText?.text.toString()

            showProgress()

            if (selectedUri != null) {
                val imageName =
                    binding.setTitle.editText?.text.toString() + binding.setPrice.editText?.text.toString() + binding.setSeller.editText?.text.toString()
                val photoUri = selectedUri ?: return@setOnClickListener
                uploadPhoto(imageName, photoUri,
                    successHandler = { uri ->
                        uploadArticle(title, explain, price, sellerId, uri)
                    },
                    errorHandler = {
                        Toast.makeText(this, "사진 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        hideProgress()
                    }
                )
            } else {
                uploadArticle(title, explain, price, sellerId, "")
            }


        }
    }

    private fun uploadPhoto(
        imageName: String,
        uri: Uri,
        successHandler: (String) -> Unit,
        errorHandler: () -> Unit
    ) {
        val fileName = "${imageName}.png"
        storage.reference.child("article/photo").child(fileName)
            .putFile(uri)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    storage.reference.child("article/photo").child(fileName)
                        .downloadUrl
                        .addOnSuccessListener { uri ->
                            successHandler(uri.toString())
                        }.addOnFailureListener {
                            errorHandler()
                        }
                } else {
                    errorHandler()
                }
            }
    }

    private fun uploadArticle(
        title: String,
        explain: String,
        price: String,
        sellerId: String,
        imageUrl: String
    ) {
        val model = ArticleModel(title, explain, price, sellerId, imageUrl)
        articleDB.push().setValue(model)

        hideProgress()
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1010 ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startContentProvider()
                } else {
                    Toast.makeText(this, "권한을 거부하였습니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun startContentProvider() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2020)
    }

    private fun showProgress() {
        findViewById<LinearLayout>(R.id.mainContentLayout).isVisible = false
        findViewById<ConstraintLayout>(R.id.progressLayout).isVisible = true

    }

    private fun hideProgress(){
        findViewById<LinearLayout>(R.id.mainContentLayout).isVisible = true
        findViewById<ConstraintLayout>(R.id.progressLayout).isVisible = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            2020 -> {
                val uri = data?.data
                if (uri != null) {
                    findViewById<ImageView>(R.id.setImage).setImageURI(uri)
                    selectedUri = uri
                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("사진을 가져오기 위해 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1010)
            }
            .create()
            .show()
    }
}
