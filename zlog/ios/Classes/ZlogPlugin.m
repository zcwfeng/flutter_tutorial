#import "ZlogPlugin.h"
#if __has_include(<zlog/zlog-Swift.h>)
#import <zlog/zlog-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "zlog-Swift.h"
#endif

@implementation ZlogPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftZlogPlugin registerWithRegistrar:registrar];
}
@end
