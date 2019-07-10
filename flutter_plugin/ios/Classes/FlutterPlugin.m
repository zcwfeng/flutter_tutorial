#import "FlutterPlugin.h"
#import "FlutterPluginEvent.h"

@implementation FlutterPlugin
FlutterPluginEvent * incallEvent;

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"flutter_plugin"
            binaryMessenger:[registrar messenger]];
  FlutterPlugin* instance = [[FlutterPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
    
    incallEvent = [[FlutterPluginEvent alloc] init];
    incallEvent.eventChannel = [FlutterEventChannel
                                eventChannelWithName:@"flutter_plugin_event"
                                binaryMessenger:[registrar messenger]];
    [incallEvent.eventChannel setStreamHandler:incallEvent];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getPlatformVersion" isEqualToString:call.method])
  {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  }
  else if ([@"getNative" isEqualToString:call.method])
  {
      result(@"Get iOS Success!");
  }
  else if ([@"sayHello" isEqualToString:call.method])
  {
      NSDictionary * argsMap = call.arguments;
      NSString * message = argsMap[@"message"];
      NSString * info = argsMap[@"info"];
      NSLog(@"%@%@",@"ios info::",info);
      NSLog(@"%@%@",@"ios messge::",message);
//      NSLog(@"%@", [@"iOS message" stringByAppendingString:message]);
//      result(@{@"message":message,@"info":@"info_value"});
      
      
      FlutterEventSink eventSink = incallEvent.eventSink;
      if(eventSink){
          eventSink(@{
                      @"event" : @"demoEvent",
                      @"value" : @"iOS value is 10",
                      });

      }
  }
  else
  {
    result(FlutterMethodNotImplemented);
  }
}

@end
