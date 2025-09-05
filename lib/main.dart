import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:webview_flutter/webview_flutter.dart';

// 프린터 서비스 import
import 'printer_service.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();

  // 전체화면 & 가로 고정
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
  final PrinterService _printerService = PrinterService();
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();

    String serverUrl =
        'http://192.168.0.113:8080/pos/app-waiting/appWaitingTicketMain';

    _controller = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setBackgroundColor(Colors.white)
      ..enableZoom(false)
      ..setNavigationDelegate(
        NavigationDelegate(
          onPageStarted: (String url) {
            setState(() => _isLoading = true);
          },
          onPageFinished: (String url) {
            setState(() => _isLoading = false);
          },
          onWebResourceError: (WebResourceError error) {
            setState(() => _isLoading = false);
          },
        ),
      )
      ..addJavaScriptChannel(
        'PrintHandler',
        onMessageReceived: (JavaScriptMessage message) {
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
          if (_isLoading) const Center(child: CircularProgressIndicator()),

          // 우상단 툴 버튼 영역
          Positioned(
            top: 50,
            right: 20,
            child: Column(
              children: [
                // 새로고침 버튼
                FloatingActionButton(
                  onPressed: () {
                    _controller.reload();
                    print('페이지 새로고침 실행');
                  },
                  backgroundColor: Colors.blue,
                  child: const Icon(Icons.refresh, color: Colors.white),
                ),
                const SizedBox(height: 12),
              ],
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
