package top.zcwfeng.flutterhybirddemo

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor

class SingleFlutterViewActivity : AppCompatActivity() {
    private lateinit var flutterEngine: FlutterEngine


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_flutter_view)
        val flutterView: FlutterView = FlutterView(this)
        val layoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        addContentView(flutterView, layoutParams)
        flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        flutterView.attachToFlutterEngine(flutterEngine)
//        flutterEngine.navigationChannel.setInitialRoute("/route1")
        flutterEngine.navigationChannel
            .setInitialRoute("/route1?{\"name\":\"StephenCurry\"}")

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


}