import 'dart:async';

import 'package:flutter/services.dart';

class FlutterPlugin {
  static const MethodChannel _channel =
      const MethodChannel('flutter_plugin');
  StreamSubscription<dynamic> _eventSubscription;

  FlutterPlugin(){
    initEvent();
  }

  initEvent(){
    _eventSubscription = _eventChannelFor()
        .receiveBroadcastStream()
        .listen(eventListener,onError: errorListener);
  }

  EventChannel _eventChannelFor(){
    return EventChannel("flutter_plugin_event");

  }

  void eventListener(dynamic event) {
    final Map<dynamic,dynamic> map =  event;
    switch(map['event']) {
      case 'demoEvent':
        String value = map['value'];
        print('demo event data :$value');

        break;
    }
  }
  void errorListener(Object obj){
    final PlatformException e = obj;
    throw e;
  }

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> get getNative async {
    final String version = await _channel.invokeMethod('getNative');
    return version;
  }

  static Future<String> sayHello(String message) async {
//    final  String res= await _channel.invokeMethod('sayHello',<String,dynamic>{'message':message});
//    return res;

    final  Map<dynamic,dynamic> response
      = await _channel.invokeMethod('sayHello',
          <String,dynamic>{'message':message,'info':'infoValue'});
    String res_message = response['message'];
    String res_info= response['info'];
    print("info:" + res_info);
    return res_message;
  }
}
