import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'jy_printer_method_channel.dart'; // 이 import 추가

abstract class JyPrinterPlatform extends PlatformInterface {
  JyPrinterPlatform() : super(token: _token);

  static final Object _token = Object();
  static JyPrinterPlatform _instance = JyPrinterMethodChannel();

  static JyPrinterPlatform get instance => _instance;

  static set instance(JyPrinterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  // 프린터 제어 메서드들
  Future<int> printerOpen();
  Future<int> printerClose();
  Future<int> printerPaperCheck();
  Future<int> printerCoverCheck();
  Future<int> printerOverheatCheck();
  Future<int> printString(List<int> data, int sync);
  Future<int> printRawData(List<int> data);

  // 금전함 제어 메서드들
  Future<int> cashdrawOpen();
  Future<int> cashdrawClose(int hDevice);
  Future<int> cashdrawSignal(int dwMs);
}