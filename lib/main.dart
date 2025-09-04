import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:webview_flutter/webview_flutter.dart';

// 1. 프린터 서비스 파일을 import 합니다.
import 'printer_service.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  
  // Flutter 엔진이 완전히 초기화될 때까지 대기
  // print('Flutter 엔진 초기화 시작...');
  
  // 앱 전체화면 모드
  SystemChrome.setEnabledSystemUIMode(SystemUiMode.immersiveSticky);
  // 화면 방향을 가로로 고정
  SystemChrome.setPreferredOrientations([
    DeviceOrientation.landscapeLeft,
    DeviceOrientation.landscapeRight,
  ]);
  
  // print('Flutter 앱 실행 시작...');
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
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();

    String serverUrl = 'http://192.168.0.113:8080/pos/app-waiting/appWaitingTicketMain';
    // print('서버 URL: $serverUrl');

    _controller = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setBackgroundColor(Colors.white)
      ..enableZoom(false)
      ..setNavigationDelegate(
        NavigationDelegate(
          onPageStarted: (String url) {
            setState(() => _isLoading = true);
            // print('페이지 로딩 시작: $url');
          },
          onPageFinished: (String url) {
            setState(() => _isLoading = false);
            // print('페이지 로딩 완료: $url');
          },
          onWebResourceError: (WebResourceError error) {
            setState(() => _isLoading = false);
            // print('웹뷰 오류: ${error.description}');
          },
        ),
      )
    // 3. 웹뷰와 플러터가 통신할 수 있는 채널(다리)을 추가합니다.
    // 웹사이트의 자바스크립트에서는 'PrintHandler'라는 이름으로 플러터에 메시지를 보낼 수 있습니다.
      ..addJavaScriptChannel(
        'PrintHandler',
        onMessageReceived: (JavaScriptMessage message) {
          // 웹뷰로부터 메시지를 받으면 프린터 서비스의 printText 함수를 호출합니다.
          // print('웹뷰로부터 프린트 요청 받음: ${message.message}');
          
          // 메시지가 비어있지 않은지 확인
          if (message.message.isNotEmpty) {
            _printerService.printText(message.message);
          } else {
            print('빈 메시지가 전달되었습니다.');
          }
        },
      )
      ..loadRequest(Uri.parse(serverUrl));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          WebViewWidget(controller: _controller),

          // 로딩 인디케이터
          if (_isLoading)
            const Center(child: CircularProgressIndicator()),

          // 새로고침 버튼 (우상단에 위치)
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

