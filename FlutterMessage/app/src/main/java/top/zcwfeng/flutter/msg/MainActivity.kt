package top.zcwfeng.flutter.msg

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel


class MainActivity : AppCompatActivity() {
    private lateinit var etName:EditText
    private val CHANNEL_NATIVE = "top.zcwfeng.flutter/native"
    private val CHANNEL_FLUTTER = "top.zcwfeng.flutter/flutter"

    private var flutterEngine: FlutterEngine? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etName = findViewById(R.id.et_name)

        var btnJumpToFlutterUseActivity = findViewById<Button>(R.id.btn_jump_to_flutter_use_activity)
        btnJumpToFlutterUseActivity.setOnClickListener {
            // 创建FlutterEngine对象
             flutterEngine = createFlutterEngine()
            // 创建MethodChannel
            createMethodChannel();
            // 跳转FlutterActivity
            startActivity(
                FlutterActivity
                    .withCachedEngine("my_engine_id")
                    .build(this)
            );        }

        var btnJumpToFlutterUseFragmet = findViewById<Button>(R.id.btn_jump_to_flutter_use_fragment)
        btnJumpToFlutterUseFragmet.setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                FlutterPageActivity::class.java
            )
            intent.putExtra("name", etName.text.toString())
            startActivityForResult(intent, 0)
        }

    }

    private fun createFlutterEngine(): FlutterEngine {
        // 实例化FlutterEngine对象
        val flutterEngine = FlutterEngine(this)
        // 设置初始路由
        flutterEngine.navigationChannel
                .setInitialRoute("route1?{\"name\":\""
                        + etName.text.toString() + "\"}")
        // 开始执行dart代码来pre-warm FlutterEngine
        flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        )
        // 缓存FlutterEngine
        FlutterEngineCache.getInstance()
                .put("my_engine_id", flutterEngine)
        return flutterEngine
    }

    private fun createMethodChannel() {
        val nativeChannel = MethodChannel(flutterEngine?.dartExecutor, CHANNEL_NATIVE)
        nativeChannel.setMethodCallHandler { methodCall: MethodCall, result: MethodChannel.Result ->
            when (methodCall.method) {
                "goBackWithResult" -> {
                    // 返回上一页，携带数据
                    ActivityManager.finishActivity(FlutterActivity::class.java)
                    Toast.makeText(this, methodCall.argument<Any>("message") as String?, Toast.LENGTH_SHORT).show()
                }
                "jumpToNative" -> {
                    // 跳转原生页面
                    val currentActivity: Activity? = ActivityManager.currentActivity()
                    val jumpToNativeIntent = Intent(currentActivity, NativePageActivity::class.java)
                    jumpToNativeIntent.putExtra("name", methodCall.argument<Any>("name") as String?)
                    jumpToNativeIntent.putExtra("isFromFlutterActivity", true)
                    startActivityForResult(jumpToNativeIntent, 1)
                }
                else -> result.notImplemented()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Toast.makeText(this, data?.getStringExtra("message"), Toast.LENGTH_SHORT).show();
        }
    }

    fun setResult(message: String) {
        val result: MutableMap<String, Any> = HashMap()
        result["message"] = message
        // 创建MethodChannel
        val flutterChannel = MethodChannel(flutterEngine?.dartExecutor, CHANNEL_FLUTTER)
        flutterChannel.invokeMethod("onActivityResult", result)
    }

}