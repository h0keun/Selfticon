package com.com.mygifticon.list

data class ArticleModel(
    val title: String,
    val explain: String,
    val price: String,
    val sellerId: String,
    val imageUrl: String
) {
    constructor(): this("","","","","")
}

