
import 'dart:async';

import 'package:flutter/services.dart';

class Zlog {
  static const MethodChannel _channel =
      const MethodChannel('zlog');

  static void i(String tag,String message){
    _channel.invokeMapMethod("LogI",{"tag":tag,"message":message});
  }

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
