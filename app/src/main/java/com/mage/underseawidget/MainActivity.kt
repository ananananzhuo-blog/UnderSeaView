package com.mage.underseawidget

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val iv = findViewById<ImageView>(R.id.iv)
//        val animationDrawable = iv.background as AnimationDrawable
//        iv.setOnClickListener {
//            animationDrawable.start()
//        }

    }
}

fun logEE(msg:String){
    Log.e("日志消息",msg)
}