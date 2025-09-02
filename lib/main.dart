import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:webview_flutter/webview_flutter.dart';

// 1. 프린터 서비스 파일을 import 합니다.
import 'printer_service.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  SystemChrome.setEnabledSystemUIMode(SystemUiMode.immersiveSticky);
  SystemChrome.setPreferredOrientations([
    DeviceOrientation.landscapeLeft,
    DeviceOrientation.landscapeRight,
  ]);
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
  // 2. PrinterService 인스턴스를 생성합니다.
  final PrinterService _printerService = PrinterService();

  @override
  void initState() {
    super.initState();

    SystemChrome.setPreferredOrientations([
      DeviceOrientation.landscapeLeft,
    ]);

    // 프린터 서비스 초기화 확인
    _checkPrinterStatus();

    String serverUrl = 'http://192.168.0.113:8080/pos/app-waiting/appWaitingTicketMain';
    print('서버 URL: $serverUrl');

    _controller = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setBackgroundColor(Colors.white)
      ..enableZoom(false)
      ..setNavigationDelegate(
        NavigationDelegate(
          onPageStarted: (String url) {
            print('페이지 로딩 시작: $url');
          },
          onPageFinished: (String url) {
            print('페이지 로딩 완료: $url');
          },
          onWebResourceError: (WebResourceError error) {
            print('웹뷰 오류: ${error.description}');
          },
        ),
      )
    // 3. 웹뷰와 플러터가 통신할 수 있는 채널(다리)을 추가합니다.
    // 웹사이트의 자바스크립트에서는 'PrintHandler'라는 이름으로 플러터에 메시지를 보낼 수 있습니다.
      ..addJavaScriptChannel(
        'PrintHandler',
        onMessageReceived: (JavaScriptMessage message) {
          // 웹뷰로부터 메시지를 받으면 프린터 서비스의 printText 함수를 호출합니다.
          print('웹뷰로부터 프린트 요청 받음: ${message.message}');
          _printerService.printText(message.message);
        },
      )
      ..loadRequest(Uri.parse(serverUrl));
  }

  // 프린터 상태 확인 메서드
  void _checkPrinterStatus() {
    _printerService.printText("프린터 테스트").then((_) {
      print('프린터 서비스 초기화 성공');
    }).catchError((error) {
      print('프린터 서비스 초기화 실패: $error');
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          WebViewWidget(controller: _controller),
          Positioned(
            top: 50,
            right: 20,
            child: FloatingActionButton(
              onPressed: () {
                _controller.reload();
                print('페이지 새로고침 실행');
              },
              backgroundColor: Colors.blue,
              child: const Icon(
                Icons.refresh,
                color: Colors.white,
              ),
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
