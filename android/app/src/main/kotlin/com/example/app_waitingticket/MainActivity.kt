package com.example.app_waitingticket

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import printer.PrinterHelper

class MainActivity: FlutterActivity() {
    private val CHANNEL = "net.waytalk.didpager/printer"
    private var printerHelper: PrinterHelper? = null

    init {
        println("=== MainActivity: Constructor called ===")
    }

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        
        println("=== MainActivity: configureFlutterEngine START ===")
        println("MainActivity: Flutter engine configured successfully")
        println("MainActivity: Thread: ${Thread.currentThread().name}")
        
        println("MainActivity: Setting up method channel...")
        // 2. 메서드 채널을 설정합니다.
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            println("MainActivity: Method call received: ${call.method}")
            
            // printerHelper가 초기화되었는지 확인하고 필요시 초기화
            if (printerHelper == null) {
                println("MainActivity: PrinterHelper not initialized, initializing now...")
                initializePrinterHelper()
            }

            when (call.method) {
                "printText" -> {
                    val text = call.argument<String>("text")
                    if (text != null) {
                        println("MainActivity: Printing text: ${text.take(50)}...")
                        val printResult = printerHelper?.printText(text) ?: "PrinterHelper not initialized"
                        result.success(printResult)
                    } else {
                        result.error("INVALID_ARGUMENT", "Text to print is null.", null)
                    }
                }
                "printerStatus" -> {
                    // 2. try-catch로 네이티브 오류 발생 시 앱 중단을 방지
                    try {
                        val status = printerHelper?.printerStatus() ?: -1
                        println("MainActivity: Checking printer status: $status")
                        // 3. 순수한 상태 결과("OK", "paper no exist" 등)만 전달
                        result.success(status)
                    } catch (e: Exception) {
                        println("MainActivity: Error checking printer status: ${e.message}")
                        result.error("STATUS_CHECK_FAILED", "Failed to check printer status.", e.message)
                    }
                }
            }
        }
        println("MainActivity: Method channel setup completed")
    }
    
    private fun initializePrinterHelper() {
        try {
            println("MainActivity: Creating PrinterHelper instance...")
            println("MainActivity: About to call PrinterHelper constructor...")
            printerHelper = PrinterHelper()
            println("MainActivity: PrinterHelper instance created successfully")
            
            // nativec 인스턴스가 제대로 생성되었는지 확인
            println("MainActivity: Checking if printerHelper is properly initialized...")
            
            println("MainActivity: Attempting to open printer...")
            val openResult = printerHelper?.openPrinter()
            println("MainActivity: Printer open result: $openResult")
            
            // 프린터 열기 실패 시 재시도 로직
            if (openResult?.contains("Failed") == true || openResult?.contains("Exception") == true) {
                println("MainActivity: First attempt failed, retrying after 2 seconds...")
                Thread.sleep(2000)
                val retryResult = printerHelper?.openPrinter()
                println("MainActivity: Printer open retry result: $retryResult")
            }
        } catch (e: Throwable) { // UnsatisfiedLinkError 등 모든 오류를 잡기 위해 Throwable 사용
            println("MainActivity: CRITICAL Error during printer initialization: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        printerHelper?.closePrinter()
    }
}

