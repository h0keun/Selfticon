package com.com.mygifticon.make

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.com.mygifticon.DBKey.Companion.DB_ARTICLES
import com.com.mygifticon.R
import com.com.mygifticon.databinding.ActivityMakeBinding
import com.com.mygifticon.list.ArticleModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MakeActivity : AppCompatActivity() {

    private var selectedUri: Uri? = null

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
            val title = binding.setTitle.editText?.text.toString()
            val explain = binding.setExplain.editText?.text.toString()
            val price = binding.setPrice.editText?.text.toString()
            val sellerId = binding.setSeller.editText?.text.toString()

            if (title.isNotEmpty() && explain.isNotEmpty() && price.isNotEmpty() && sellerId.isNotEmpty()) {
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
            } else {
                Toast.makeText(this, "??? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

        binding.makeButton.setOnClickListener {
            val title = binding.setTitle.editText?.text.toString()
            val explain = binding.setExplain.editText?.text.toString()
            val price = binding.setPrice.editText?.text.toString()
            val sellerId = binding.setSeller.editText?.text.toString()

            if (title.isNotEmpty() && explain.isNotEmpty() && price.isNotEmpty() && sellerId.isNotEmpty() && selectedUri != null) {
                showProgress()

                val imageName =
                    binding.setTitle.editText?.text.toString() + binding.setPrice.editText?.text.toString() + binding.setSeller.editText?.text.toString()
                val photoUri = selectedUri ?: return@setOnClickListener

                uploadPhoto(imageName, photoUri,
                    successHandler = { uri ->
                        uploadArticle(title, explain, price, sellerId, uri)
                    },
                    errorHandler = {
                        Toast.makeText(this, "?????? ???????????? ??????????????????.", Toast.LENGTH_SHORT).show()
                        hideProgress()
                    }
                )
            } else {
                Toast.makeText(this, "?????? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
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
        articleDB.child("${title}${price}${sellerId}").setValue(model)

        hideProgress()
        finish()
//        val nextIntent = Intent(this, MainActivity::class.java)
//        nextIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(nextIntent)
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
                    Toast.makeText(this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
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

    private fun hideProgress() {
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
                    Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("????????? ???????????????.")
            .setMessage("????????? ???????????? ?????? ???????????????.")
            .setPositiveButton("??????") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1010)
            }
            .create()
            .show()
    }
}
