package com.flutter_plugin;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterPlugin */
public class FlutterPlugin implements MethodCallHandler {
    //事件派发对象
  private EventChannel.EventSink eventSink = null;
  //事件派发流
  private EventChannel.StreamHandler streamHandler = new EventChannel.StreamHandler() {
      @Override
      public void onListen(Object o, EventChannel.EventSink sink) {
          eventSink = sink;
      }

      @Override
      public void onCancel(Object o) {
            eventSink = null;
      }
  };

  private FlutterPlugin(Registrar registrar,MethodChannel channel) {
      EventChannel eventChannel = new EventChannel(registrar.messenger(),"flutter_plugin_event");
      eventChannel.setStreamHandler(streamHandler);
  }
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_plugin");
    channel.setMethodCallHandler(new FlutterPlugin(registrar,channel));

  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }if (call.method.equals("getNative")) {
      result.success("Get Android success...");
    } if (call.method.equals("sayHello")) {
        String message = call.argument("message");
        String info = call.argument("info");
        System.out.println("android message::" + message);
        System.out.println("android info::" + info);

//        ConstraintsMap params = new ConstraintsMap();
//        params.putString("message",message);
//        params.putString("info","infoValue");
//        result.success(params.toMap());

        if(eventSink != null) {
            ConstraintsMap map = new ConstraintsMap();
            map.putString("event","demoEvent");
            map.putString("value","android value is 10");
            eventSink.success(map.toMap());
        }


    }else {
      result.notImplemented();
    }
  }
}
