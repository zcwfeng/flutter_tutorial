
//event dispatcher header file
#import <Flutter/Flutter.h>

@interface FlutterPluginEvent : NSObject<FlutterStreamHandler>
@property (nonatomic, strong) FlutterEventSink eventSink;
@property (nonatomic, strong) FlutterEventChannel* eventChannel;
@end
