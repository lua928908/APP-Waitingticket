import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'jy_printer_platform_interface.dart';

class JyPrinterMethodChannel extends JyPrinterPlatform {
  @visibleForTesting
  final methodChannel = const MethodChannel('jy_printer');

  @override
  Future<int> printerOpen() async {
    final int result = await methodChannel.invokeMethod('printerOpen');
    return result;
  }

  @override
  Future<int> printerClose() async {
    final int result = await methodChannel.invokeMethod('printerClose');
    return result;
  }

  @override
  Future<int> printerPaperCheck() async {
    final int result = await methodChannel.invokeMethod('printerPaperCheck');
    return result;
  }

  @override
  Future<int> printerCoverCheck() async {
    final int result = await methodChannel.invokeMethod('printerCoverCheck');
    return result;
  }

  @override
  Future<int> printerOverheatCheck() async {
    final int result = await methodChannel.invokeMethod('printerOverheatCheck');
    return result;
  }

  @override
  Future<int> printString(List<int> data, int sync) async {
    final int result = await methodChannel.invokeMethod('printString', {
      'data': data,
      'sync': sync,
    });
    return result;
  }

  @override
  Future<int> printRawData(List<int> data) async {
    final int result = await methodChannel.invokeMethod('printRawData', {
      'data': data,
    });
    return result;
  }

  @override
  Future<int> cashdrawOpen() async {
    final int result = await methodChannel.invokeMethod('cashdrawOpen');
    return result;
  }

  @override
  Future<int> cashdrawClose(int hDevice) async {
    final int result = await methodChannel.invokeMethod('cashdrawClose', {
      'hDevice': hDevice,
    });
    return result;
  }

  @override
  Future<int> cashdrawSignal(int dwMs) async {
    final int result = await methodChannel.invokeMethod('cashdrawSignal', {
      'dwMs': dwMs,
    });
    return result;
  }
}