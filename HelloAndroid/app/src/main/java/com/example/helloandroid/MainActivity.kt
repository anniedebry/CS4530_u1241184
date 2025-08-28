package com.example.helloandroid

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //function that takes text data from button and sends it to the second activity when pressed, also changes view
        fun setOnButtonClicked(button: Button) {
            button.setOnClickListener {
                val intent = Intent(this, SecondActivity::class.java)
                val buttonText = button.text.toString()
                intent.putExtra("button_text", buttonText)
                startActivity(intent)
            }
        }

        //adding the setOnButtonClicked method to all the buttons
        setOnButtonClicked(findViewById(R.id.button_fin))
        setOnButtonClicked(findViewById(R.id.button_jake))
        setOnButtonClicked(findViewById(R.id.button_bubblegum))
        setOnButtonClicked(findViewById(R.id.button_marceline))
        setOnButtonClicked(findViewById(R.id.button_king))
    }
}