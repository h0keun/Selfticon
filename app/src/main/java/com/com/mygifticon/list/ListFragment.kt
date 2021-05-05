package com.com.mygifticon.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.com.mygifticon.R
import com.com.mygifticon.databinding.FragmentListBinding

class ListFragment : Fragment(R.layout.fragment_list) {

    private var binding: FragmentListBinding? = null
    private lateinit var articleAdapter: ArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentListBinding = FragmentListBinding.bind(view)
        binding = fragmentListBinding

        //val model = ArticleModel(title, explain, price, sellerId, "")
        articleAdapter = ArticleAdapter()
        articleAdapter.submitList(mutableListOf<ArticleModel>().apply {
            add(ArticleModel("아메리카노","씁슬한게 맛이 기가멕힘","4000원","",""))
            add(ArticleModel("마끼아또","달달구리 한게 맛이 기똥참","5000원","",""))

        })

        fragmentListBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentListBinding.articleRecyclerView.adapter = articleAdapter
    }
}
