package com.example.wheelassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var loaderPercentageTextView: EditText
    private lateinit var animateTextView: TextView
    private lateinit var customLoaderView: CustomLoaderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customLoaderView = findViewById(R.id.downloadLoader)
        loaderPercentageTextView = findViewById(R.id.loaderPercentageInputView)
        animateTextView = findViewById(R.id.animateButton)
        animateTextView.setOnClickListener {
            loaderPercentageTextView.text?.let {
                try {
                    val text = it.toString().toFloat()
                    if (text in 0..100) {
                        customLoaderView.setDownloadPercentage(text)
                    } else {
                        Toast.makeText(
                            this,
                            "Please enter a number in the range 0-100",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {

                }
            }
        }
    }
}
