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

        articleAdapter = ArticleAdapter()
        articleAdapter.submitList(mutableListOf<ArticleModel>().apply {
            add(ArticleModel("0","카페라떼",1000000,"4800원",""))
            add(ArticleModel("0","마끼아또",2000000,"5000원",""))

        })

        fragmentListBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentListBinding.articleRecyclerView.adapter = articleAdapter
    }
}
