package top.zcwfeng.flutter.msg

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel


class MyFlutterFragment : FlutterFragment() {
    private var mFlutterEngine: FlutterEngine? = null
    private val CHANNEL_NATIVE = "top.zcwfeng.flutter/native"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 这里保证了getView()返回值不为null
        // 这里保证了getView()返回值不为null
        val nativeChannel =
            MethodChannel(flutterEngine!!.dartExecutor, CHANNEL_NATIVE)
        nativeChannel.setMethodCallHandler { methodCall: MethodCall, result: MethodChannel.Result ->
            when (methodCall.method) {
                "goBack" ->                     // 返回上一页
                    activity!!.finish()
                "goBackWithResult" -> {
                    // 返回上一页，携带数据
                    val backIntent = Intent()
                    backIntent.putExtra(
                        "message",
                        methodCall.argument<Any>("message") as String?
                    )
                    activity!!.setResult(Activity.RESULT_OK, backIntent)
                    activity!!.finish()
                }
                "jumpToNative" -> {
                    // 跳转原生页面
                    val jumpToNativeIntent =
                        Intent(activity, NativePageActivity::class.java)
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


    companion object {

        @JvmStatic
        fun newInstance(initialRoute: String, flutterEngine: FlutterEngine) =
            MyFlutterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_INITIAL_ROUTE, initialRoute)
                    mFlutterEngine = flutterEngine
                }
            }
    }

    override fun provideFlutterEngine(context: Context): FlutterEngine? {
        return mFlutterEngine

    }
}