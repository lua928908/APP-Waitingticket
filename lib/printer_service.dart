import 'package:flutter/services.dart';

class PrinterService {
  // 안드로이드 네이티브 코드와 통신하기 위한 채널입니다.
  // 이 이름은 MainActivity의 채널 이름과 반드시 동일해야 합니다.
  static const MethodChannel _platform = MethodChannel('net.waytalk.didpager/printer');

  // 프린터 상태 확인
  Future<String> checkPrinterStatus() async {
    try {
      print('PrinterService: checkPrinterStatus() 호출됨');
      final int? check = await _platform.invokeMethod('printerStatus');
      print('PrinterService: 네이티브에서 받은 상태값: $check');
      final String result = check == 0 ? "ok" : "fair";

      print('프린터 상태 확인 결과: $result');
      return result ?? 'Unknown status';
    } on PlatformException catch (e) {
      print("프린터 상태 확인 실패: '${e.message}'.");
      return 'Error: ${e.message}';
    }
  }

  // 네이티브 코드로 텍스트 인쇄를 요청하는 함수
  Future<void> printText(String text) async {
    try {
      print('프린터 서비스: 인쇄 요청 시작 - 텍스트 길이: ${text.length}');
      print('프린터 서비스: 전달할 텍스트: $text');
      
      // 먼저 프린터 상태 확인
      final status = await checkPrinterStatus();
      print('프린터 서비스: 현재 프린터 상태: $status');
      
      if (status != "ok") {
        print('프린터 서비스: 프린터 상태가 좋지 않음 - $status');
        return;
      }
      
      // 'printText' 메소드를 호출하고, 인쇄할 텍스트를 Map 형태로 전달합니다.
      print('프린터 서비스: invokeMethod 호출 직전');
      final String? result = await _platform.invokeMethod('printText', {'text': text});
      print('프린터 서비스: 네이티브 프린트 최종 결과: $result');

    } on PlatformException catch (e) {
      // 네이티브 코드 호출 중 에러가 발생하면 여기서 처리합니다.
      print("프린터 서비스: 프린트 실패 - '${e.message}'.");
      print("프린터 서비스: 에러 코드: ${e.code}");
      print("프린터 서비스: 에러 상세: ${e.details}");
    }
  }
}

