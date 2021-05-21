package com.com.mygifticon

import java.io.Serializable

data class GifticonModel(
    val gift_title: String?,
    val gift_explain: String?,
    val gift_price: String?,
    val gift_sellerId: String?,
    val gift_imageUrl: String?
): Serializable

//{
//    constructor(): this("","","","","")
//}

