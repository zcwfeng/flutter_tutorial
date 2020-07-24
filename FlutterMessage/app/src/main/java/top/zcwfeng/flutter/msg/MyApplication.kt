package top.zcwfeng.flutter.msg

import android.app.Activity
import android.app.Application
import android.os.Bundle
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class MyApplication : Application() {

    lateinit var mFlutterEngine:FlutterEngine
    override fun onCreate() {
        super.onCreate()
        mFlutterEngine = FlutterEngine(this)
        mFlutterEngine.navigationChannel.setInitialRoute("route1")
        mFlutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        FlutterEngineCache
            .getInstance()
            .put("my_engine_id",mFlutterEngine)

        registerActivityLifecycleCallbacks(object:ActivityLifecycleCallbacks{
            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityDestroyed(p0: Activity) {
                ActivityManager.activities.remove(p0)
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                ActivityManager.activities.add(p0)
            }

            override fun onActivityResumed(p0: Activity) {
            }

        })
    }
}