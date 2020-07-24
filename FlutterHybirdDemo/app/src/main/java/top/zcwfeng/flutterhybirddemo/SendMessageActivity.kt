package top.zcwfeng.flutterhybirddemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SendMessageActivity : AppCompatActivity() {
    private val CHANNEL_NATIVE = "top.zcwfeng.flutter/native"
    private val CHANNEL_FLUTTER = "top.zcwfeng.flutter/flutter"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)
    }
}