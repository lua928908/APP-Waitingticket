import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:webview_flutter/webview_flutter.dart';

import 'jy_printer.dart'; // 프린터 플러그인 import

void main() {
  WidgetsFlutterBinding.ensureInitialized();

  print('Flutter 엔진 초기화 시작...');

  SystemChrome.setEnabledSystemUIMode(SystemUiMode.immersiveSticky);
  SystemChrome.setPreferredOrientations([
    DeviceOrientation.landscapeLeft,
    DeviceOrientation.landscapeRight,
  ]);

  print('Flutter 앱 실행 시작...');
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: WebViewPage(),
    );
  }
}

class WebViewPage extends StatefulWidget {
  @override
  _WebViewPageState createState() => _WebViewPageState();
}

class _WebViewPageState extends State<WebViewPage> {
  late final WebViewController _controller;
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();

    String serverUrl = 'http://192.168.0.113:8080/pos/app-waiting/appWaitingTicketMain';
    print('서버 URL: $serverUrl');

    _controller = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setBackgroundColor(Colors.white)
      ..enableZoom(false)
      ..setNavigationDelegate(
        NavigationDelegate(
          onPageStarted: (String url) {
            setState(() => _isLoading = true);
            print('페이지 로딩 시작: $url');
          },
          onPageFinished: (String url) {
            setState(() => _isLoading = false);
            print('페이지 로딩 완료: $url');
          },
          onWebResourceError: (WebResourceError error) {
            setState(() => _isLoading = false);
            print('웹뷰 오류: ${error.description}');
          },
        ),
      )
      ..addJavaScriptChannel(
        'PrintHandler',
        onMessageReceived: (JavaScriptMessage message) {
          print('웹뷰로부터 프린트 요청 받음: ${message.message}');

          if (message.message.isNotEmpty) {
            _handlePrintRequest(message.message);
          } else {
            print('빈 메시지가 전달되었습니다.');
          }
        },
      )
      ..loadRequest(Uri.parse(serverUrl));
  }

  // 프린터 요청 처리 함수
  Future<void> _handlePrintRequest(String message) async {
    try {
      print('프린터 요청 처리 시작: $message');

      // 프린터 열기
      final isOpen = await JyPrinter.openPrinter();
      if (!isOpen) {
        print('프린터 열기 실패');
        return;
      }

      // 프린터 상태 확인
      final isReady = await JyPrinter.isPrinterReady();
      if (!isReady) {
        print('프린터가 준비되지 않았습니다');
        await JyPrinter.closePrinter();
        return;
      }

      // 테스트 출력
      await JyPrinter.printText('=== 웹뷰 프린트 테스트 ===\n');
      await JyPrinter.printText('받은 메시지: $message\n');
      await JyPrinter.printText('시간: ${DateTime.now()}\n');
      await JyPrinter.printText('=====================\n\n');

      // 프린터 닫기
      await JyPrinter.closePrinter();

      print('프린터 출력 완료');

    } catch (e) {
      print('프린터 오류: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          WebViewWidget(controller: _controller),

          if (_isLoading)
            const Center(child: CircularProgressIndicator()),

          Positioned(
            top: 50,
            right: 20,
            child: FloatingActionButton(
              onPressed: () {
                _controller.reload();
                print('페이지 새로고침 실행');
              },
              backgroundColor: Colors.blue,
              child: const Icon(Icons.refresh, color: Colors.white),
            ),
          ),
        ],
      ),
    );
  }

  @override
  void dispose() {
    SystemChrome.setPreferredOrientations([
      DeviceOrientation.landscapeLeft,
      DeviceOrientation.landscapeRight,
      DeviceOrientation.portraitUp,
      DeviceOrientation.portraitDown,
    ]);
    SystemChrome.setEnabledSystemUIMode(
      SystemUiMode.manual,
      overlays: SystemUiOverlay.values,
    );
    super.dispose();
  }
}