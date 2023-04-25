package com.example.pokebook

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl", "placeHolder")
fun setImage(view: ImageView, url: Any?, placeholder: Drawable) {
    Glide.with(view)
        .load(url)
        .placeholder(placeholder)
        .error(placeholder)
        .into(view)

}

@BindingAdapter("setText")
fun setText(view: TextView, pokemonName: String?) {
    view.text = pokemonName ?: "Whats?"
}