package com.example.a2021ictproject.bind

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.LinearGradient
import android.graphics.Shader
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a2021ictproject.R
import com.example.a2021ictproject.adapter.JoinContestImageRecyclerViewAdapter
import com.example.a2021ictproject.adapter.JoinContestRecyclerViewAdapter
import com.example.a2021ictproject.network.dto.response.Participant
import com.google.android.material.textfield.TextInputLayout
import java.util.jar.Manifest

@BindingAdapter("setVisible")
fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) {
        VISIBLE
    } else INVISIBLE
}

@BindingAdapter("setGone")
fun View.setGone(isVisible: Boolean) {
    visibility = if (isVisible) {
        GONE
    } else VISIBLE
}

// TextInputLayout 에러 메세지를 변경함
@BindingAdapter("setError")
fun TextInputLayout.setError(errorMsg: String?) {
    this.error = errorMsg
}

@BindingAdapter("loadUri")
fun ImageView.setImage(uri: Uri) {
    Glide.with(this.context)
        .load(uri)
        .into(this)
}

@BindingAdapter("loadUri")
fun ImageView.setImage(uri: String) {
    Glide.with(this.context)
        .load(uri)
        .into(this)
}

// ㅈ같고여~~~~
@BindingAdapter("setImageBitmap")
fun ImageView.setImageBitmap(uri: Uri) {
    val contentResolver: ContentResolver = this.context.contentResolver

    val bitmap: Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val source = ImageDecoder.createSource(contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    } else {
        MediaStore.Images.Media.getBitmap(contentResolver, uri)
    }

    this.setImageBitmap(bitmap)
}

@JvmName("submitParticipantList")
@BindingAdapter("submitList")
fun RecyclerView.submitList(list: List<Participant>?) {
    if (list != null) {
        val adapter = JoinContestRecyclerViewAdapter()
        adapter.setList(list)
    }
}

@JvmName("submitJoinImageList")
@BindingAdapter("submitList")
fun RecyclerView.submitList(list: List<Uri>?) {
    if (list != null) {
        val adapter = JoinContestImageRecyclerViewAdapter()
        adapter.setList(list)
    }
}

// 텍스트뷰 그라데이션 적용
@BindingAdapter("setShader")
fun TextView.setShader(b: Boolean) {
    val startColor = ContextCompat.getColor(this.context, R.color.main_gradient_start_color)
    val endColor = ContextCompat.getColor(this.context, R.color.main_gradient_end_color)

    val shader = LinearGradient(
        0f,
        0f,
        this.paint.measureText(this.text.toString()),
        this.textSize,
        intArrayOf(startColor, endColor),
        floatArrayOf(0f, 0.6f),
        Shader.TileMode.CLAMP
    )

    this.paint.shader = shader
}
