# 포스기 웹뷰 앱 (Waiting Ticket System)

Android 11 포스기 환경에서 외부 URL을 표시하는 웹뷰 애플리케이션입니다.

## 주요 기능

### 🖥️ 웹뷰 기능
- 외부 URL 로딩 및 표시
- JavaScript 실행 지원
- HTTP/HTTPS 통신 지원
- 자체 서명 인증서 허용
- 파일 업로드/다운로드 지원

### 🎛️ 포스기 최적화
- 전체화면 모드
- 세로 화면 고정
- 터치 인터랙션 최적화
- 확대/축소 비활성화
- 스크롤바 숨김

### 🔧 컨트롤 기능
- 뒤로가기/앞으로가기
- 새로고침
- 홈으로 이동
- 전체화면 토글
- 현재 URL 표시

### 🖨️ 포스기 하드웨어 연동
- 영수증 프린터 연동 (JavaScript 인터페이스)
- 바코드 스캐너 연동
- 결제 시스템 연동

## 설치 및 실행

### 1. 의존성 설치
```bash
flutter pub get
```

### 2. Android 빌드
```bash
flutter build apk --release
```

### 3. 포스기에 설치
```bash
adb install build/app/outputs/flutter-apk/app-release.apk
```

## localhost:8080 연결 설정

### 현재 설정
앱은 기본적으로 `http://localhost:8080/pos/posWaitingRegistView`로 연결되도록 설정되어 있습니다.

### 서버 실행 확인
1. **웹 서버가 8080 포트에서 실행 중인지 확인**
   ```bash
   # 서버 상태 확인
   curl http://localhost:8080/pos/posWaitingRegistView
   ```

2. **브라우저에서 테스트**
   - 브라우저에서 `http://localhost:8080/pos/posWaitingRegistView` 접속
   - 페이지가 정상적으로 로드되는지 확인

### 연결 문제 해결

#### 1. 서버가 실행되지 않는 경우
```bash
# 서버 시작 (예시)
npm start
# 또는
python -m http.server 8080
# 또는
php -S localhost:8080
```

#### 2. 포트가 차단된 경우
```bash
# 포트 확인
netstat -an | grep 8080
# 또는
lsof -i :8080
```

#### 3. 방화벽 설정
- Windows: Windows Defender 방화벽에서 8080 포트 허용
- macOS: 시스템 환경설정 > 보안 및 개인 정보 보호 > 방화벽

### URL 변경 방법

`lib/main.dart` 파일에서 다음 부분을 수정하세요:

```dart
// localhost:8080으로 요청
..loadRequest(Uri.parse('http://localhost:8080/pos/posWaitingRegistView'));

// 다른 포트나 경로로 변경하려면:
// ..loadRequest(Uri.parse('http://localhost:3000/'));
// ..loadRequest(Uri.parse('http://localhost:5000/api/'));
```

## 설정 방법

### URL 변경
`lib/main.dart` 파일에서 다음 부분을 수정하세요:

```dart
// 표시하고 싶은 외부 URL (실제 사용할 URL로 변경하세요)
final Uri _initialUrl = WebUri("https://your-pos-system.com");
```

### 포스기 하드웨어 연동
웹페이지에서 다음 JavaScript 코드를 사용하여 포스기 기능을 호출할 수 있습니다:

```javascript
// 영수증 출력
window.flutter_inappwebview.callHandler('posSystem', 'print', '영수증 데이터');

// 바코드 스캔
window.flutter_inappwebview.callHandler('posSystem', 'scan');

// 결제 처리
window.flutter_inappwebview.callHandler('posSystem', 'payment', '10000');
```

## 네트워크 보안 설정

### HTTP 통신 허용
Android 11에서는 기본적으로 HTTP 통신이 차단됩니다. 이 앱은 다음 설정으로 HTTP 통신을 허용합니다:

- `android:usesCleartextTraffic="true"`
- 네트워크 보안 설정 파일 (`network_security_config.xml`)

### localhost 통신 지원
- `localhost` 및 `127.0.0.1` 도메인 허용
- 개발 환경에서 자주 사용되는 IP 대역 허용
- 자체 서명 인증서 허용

### 자체 서명 인증서 허용
포스기 환경에서 자체 서명 인증서를 사용하는 경우에도 정상적으로 작동합니다.

## 권한 설정

다음 권한들이 포함되어 있습니다:

- `INTERNET`: 인터넷 접근
- `ACCESS_NETWORK_STATE`: 네트워크 상태 확인
- `ACCESS_WIFI_STATE`: WiFi 상태 확인
- `CAMERA`: 카메라 접근 (바코드 스캔용)
- `RECORD_AUDIO`: 마이크 접근 (음성 인식용)
- `ACCESS_FINE_LOCATION`: 정확한 위치 정보
- `ACCESS_COARSE_LOCATION`: 대략적인 위치 정보

## 문제 해결

### 웹페이지가 로드되지 않는 경우
1. 네트워크 연결 확인
2. URL이 올바른지 확인
3. 방화벽 설정 확인
4. 서버가 실행 중인지 확인

### localhost 연결 문제
1. **서버 실행 확인**
   ```bash
   curl http://localhost:8080
   ```

2. **포트 확인**
   ```bash
   netstat -an | grep 8080
   ```

3. **방화벽 설정**
   - Windows: Windows Defender 방화벽
   - macOS: 시스템 환경설정 > 보안 및 개인 정보 보호

4. **앱 재시작**
   - 앱을 완전히 종료하고 다시 시작

### SSL 인증서 오류
- 자체 서명 인증서 사용 시 자동으로 허용됩니다
- 공식 인증서 사용을 권장합니다

### 포스기 하드웨어가 작동하지 않는 경우
1. 하드웨어 드라이버 설치 확인
2. 권한 설정 확인
3. JavaScript 인터페이스 호출 확인

## 개발 환경

- Flutter SDK: ^3.9.0
- Dart SDK: ^3.9.0
- webview_flutter: ^4.13.0
- Android API Level: 21+ (Android 5.0+)

## 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.
