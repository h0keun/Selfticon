package com.com.mygifticon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.com.mygifticon.databinding.ActivityGifticonBinding

class GifticonActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGifticonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gifticonData = intent.getSerializableExtra("gifticonModel") as GifticonModel

        binding.giftTitle.text = gifticonData.gift_title
        binding.giftExplain.text = gifticonData.gift_explain
        binding.giftPrice.text = gifticonData.gift_price
        binding.giftSellerId.text = gifticonData.gift_sellerId

        Glide
            .with(binding.giftImageUrl.context)
            .load(gifticonData.gift_imageUrl)
            .into(binding.giftImageUrl)
    }
}