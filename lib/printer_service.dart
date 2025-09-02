import 'package:flutter/services.dart';

class PrinterService {
  // 안드로이드 네이티브 코드와 통신하기 위한 채널입니다.
  // 이 이름은 MainActivity의 채널 이름과 반드시 동일해야 합니다.
  static const MethodChannel _platform = MethodChannel('net.waytalk.didpager/printer');

  // 네이티브 코드로 텍스트 인쇄를 요청하는 함수
  Future<void> printText(String text) async {
    try {
      // 'printText' 메소드를 호출하고, 인쇄할 텍스트를 Map 형태로 전달합니다.
      final String? result = await _platform.invokeMethod('printText', {'text': text});
      print('네이티브 프린트 결과: $result');
    } on PlatformException catch (e) {
      // 네이티브 코드 호출 중 에러가 발생하면 여기서 처리합니다.
      print("프린트 실패: '${e.message}'.");
    }
  }

  // (추가) 금전함(Cash Drawer) 열기 함수 예시
  Future<void> openCashDrawer() async {
    try {
      final String? result = await _platform.invokeMethod('openCashDrawer');
      print('네이티브 금전함 열기 결과: $result');
    } on PlatformException catch (e) {
      print("금전함 열기 실패: '${e.message}'.");
    }
  }
}
