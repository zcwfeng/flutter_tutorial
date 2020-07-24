package top.zcwfeng.flutter.msg

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_native_page.*

class NativePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_page)
        // Flutter页面传过来的参数
        tv_text.setText("Flutter页面传过来的参数：name=" + intent.getStringExtra("name"))

        btn_back.setOnClickListener { v ->
            if (intent.getBooleanExtra("isFromFlutterActivity", false)) {
                // 上一个页面是FlutterActivity
                val mainActivity =
                    ActivityManager.activities.get(0) as MainActivity
                mainActivity.setResult("我从原生页面回来了")
            } else {
                // 上一个页面是FlutterPageActivity
                val intent = Intent()
                intent.putExtra("message", "我从原生页面回来了")
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}