package com.com.mygifticon.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.com.mygifticon.DBKey.Companion.DB_ARTICLES
import com.com.mygifticon.GifticonActivity
import com.com.mygifticon.GifticonModel
import com.com.mygifticon.R
import com.com.mygifticon.databinding.FragmentListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListFragment : Fragment(R.layout.fragment_list) {

    //private val articleListInFirebase = mutableListOf<String>()

    private lateinit var articleDB: DatabaseReference
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleModelName : ArticleModel

    private val articleList = mutableListOf<ArticleModel>()
    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return

            articleList.add(articleModel)
            //snapshot.key?.let { articleListInFirebase.add(it) }
            articleAdapter.submitList(articleList)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val imageName = "${articleModelName.title}${articleModelName.price}${articleModelName.sellerId}"
            articleDB.child(DB_ARTICLES).child(imageName).removeValue()
            articleAdapter.notifyDataSetChanged()
        }
        override fun onChildRemoved(snapshot: DataSnapshot) {
            val imageName = "${articleModelName.title}${articleModelName.price}${articleModelName.sellerId}"
            articleDB.child(DB_ARTICLES).child(imageName).removeValue()
            articleAdapter.notifyDataSetChanged()
        }
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    }

    private var binding: FragmentListBinding? = null
//    private val auth: FirebaseAuth by lazy {
//        Firebase.auth
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentListBinding = FragmentListBinding.bind(view)
        binding = fragmentListBinding

        articleList.clear() // 다른 프래그먼트 들어갓다 다시 오면 리스트가 중복생성되어서 여기서 초기화 시킴
        articleDB = Firebase.database.reference.child(DB_ARTICLES)

        articleAdapter = ArticleAdapter(onItemClicked = { articleModel ->
            val Gifticon = GifticonModel(
                gift_title = articleModel.title,
                gift_explain = articleModel.explain,
                gift_price = articleModel.price,
                gift_sellerId = articleModel.sellerId,
                gift_imageUrl = articleModel.imageUrl
            )

            val intent = Intent(requireContext(), GifticonActivity::class.java)
            intent.putExtra("gifticonModel", Gifticon)
            startActivity(intent)
        })

        fragmentListBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentListBinding.articleRecyclerView.adapter = articleAdapter

        articleDB.addChildEventListener(listener)
    }

    override fun onResume() {
        super.onResume()

        articleAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        articleDB.removeEventListener(listener)
    }
}
