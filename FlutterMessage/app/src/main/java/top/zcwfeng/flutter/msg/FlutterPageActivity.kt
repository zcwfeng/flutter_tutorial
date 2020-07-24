package top.zcwfeng.flutter.msg

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel


class FlutterPageActivity : AppCompatActivity() {
    private lateinit var flutterEngine: FlutterEngine
    private val flutterView: FlutterView? = null

    private val CHANNEL_NATIVE = "top.zcwfeng.flutter/native"
    private val CHANNEL_FLUTTER = "top.zcwfeng.flutter/flutter"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flutter_page)
        flutterEngine = createFlutterEngine();

        val nativeChannel =
            MethodChannel(flutterEngine?.dartExecutor, CHANNEL_NATIVE)
        nativeChannel.setMethodCallHandler { methodCall: MethodCall, result: MethodChannel.Result ->
            when (methodCall.method) {
                "goBack" ->                     // 返回上一页
                    finish()
                "goBackWithResult" -> {
                    // 返回上一页，携带数据
                    val backIntent = Intent()
                    backIntent.putExtra(
                        "message",
                        methodCall.argument<Any>("message") as String?
                    )
                    setResult(Activity.RESULT_OK, backIntent)
                    finish()
                }
                "jumpToNative" -> {
                    // 跳转原生页面
                    val jumpToNativeIntent = Intent(this, NativePageActivity::class.java)
                    jumpToNativeIntent.putExtra(
                        "name",
                        methodCall.argument<Any>("name") as String?
                    )
                    startActivityForResult(jumpToNativeIntent, 0)
                }
                else -> result.notImplemented()
            }
        }
    }

    /**
     * 创建可缓存的FlutterEngine
     *
     * @return
     */
    private fun createFlutterEngine(): FlutterEngine {
        // 实例化FlutterEngine对象
        val flutterEngine = FlutterEngine(this)
        // 设置初始路由
        flutterEngine.navigationChannel
            .setInitialRoute("route1?{\"name\":\"" + intent.getStringExtra("name") + "\"}")
        // 开始执行dart代码来pre-warm FlutterEngine
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        // 缓存FlutterEngine
        FlutterEngineCache.getInstance().put("my_engine_id", flutterEngine)
        return flutterEngine
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val message = data.getStringExtra("message")
                val result: MutableMap<String, Any?> = HashMap()
                result["message"] = message
                // 创建MethodChannel
                val flutterChannel =
                    MethodChannel(flutterEngine?.dartExecutor, CHANNEL_FLUTTER)
                flutterChannel.invokeMethod("onActivityResult", result)
            }
        }
    }

    override fun onBackPressed() {
        val flutterChannel =
            MethodChannel(flutterEngine?.dartExecutor, CHANNEL_FLUTTER)
        flutterChannel.invokeMethod("goBack", null)
    }

    override fun onResume() {
        super.onResume()
        flutterEngine.lifecycleChannel.appIsResumed()
    }

    override fun onPause() {
        super.onPause()
        flutterEngine.lifecycleChannel.appIsInactive()
    }

    override fun onStop() {
        super.onStop()
        flutterEngine.lifecycleChannel.appIsPaused()
    }

    override fun onDestroy() {
        flutterView?.detachFromFlutterEngine()
        super.onDestroy()
    }


}