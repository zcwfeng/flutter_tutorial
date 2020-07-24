package top.zcwfeng.flutterhybirddemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor


class MultiFlutterViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_flutter_view)

        // 1.FlutterFragment.createDefault()

//        val flutterFragment = FlutterFragment.createDefault()
//        val flutterFragment2 = FlutterFragment.createDefault()
//        supportFragmentManager.beginTransaction().add(R.id.frameLayout1,flutterFragment)
//            .add(R.id.frameLayout2,flutterFragment2)
//            .commitAllowingStateLoss()

        //2.FlutterFragment.withNewEngine()
        val flutterFragment = FlutterFragment.withNewEngine()
            .initialRoute("/route1").build<FlutterFragment>()
        val flutterFragment2 = FlutterFragment.withNewEngine()
            .initialRoute("/route2").build<FlutterFragment>()
        supportFragmentManager.beginTransaction().add(R.id.frameLayout1,flutterFragment)
            .add(R.id.frameLayout2,flutterFragment2)
            .commitAllowingStateLoss()

        //3. FlutterFragment.withCachedEngine
//        val flutterEngine = FlutterEngine(this)
//        flutterEngine.navigationChannel.setInitialRoute("route1")
//        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
//        FlutterEngineCache.getInstance().put("my_cache_id",flutterEngine)
//        val flutterFragment = FlutterFragment.withCachedEngine("my_cache_id")
//            .build<FlutterFragment>()

    }
}