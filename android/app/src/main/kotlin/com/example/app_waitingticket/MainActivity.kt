package com.example.app_waitingticket

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import printer.PrinterHelper

class MainActivity: FlutterActivity() {
    private val CHANNEL = "net.waytalk.didpager/printer"
    private lateinit var printerHelper: PrinterHelper

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        
        println("MainActivity: configureFlutterEngine called")
        println("MainActivity: Starting printer initialization...")

        // 1. PrinterHelper 인스턴스를 생성하고 프린터를 엽니다.
        try {
            println("MainActivity: Creating PrinterHelper instance...")
            printerHelper = PrinterHelper()
            println("MainActivity: PrinterHelper instance created successfully")
            
            println("MainActivity: Attempting to open printer...")
            val openResult = printerHelper.openPrinter()
            println("MainActivity: Printer open result: $openResult")
            
            // 프린터 열기 실패 시 재시도 로직
            if (openResult.contains("Failed") || openResult.contains("Exception")) {
                println("MainActivity: First attempt failed, retrying after 2 seconds...")
                Thread.sleep(2000)
                val retryResult = printerHelper.openPrinter()
                println("MainActivity: Printer open retry result: $retryResult")
            }
        } catch (e: Throwable) { // UnsatisfiedLinkError 등 모든 오류를 잡기 위해 Throwable 사용
            println("MainActivity: CRITICAL Error during printer initialization: ${e.message}")
            e.printStackTrace()
        }

        println("MainActivity: Setting up method channel...")
        // 2. 메서드 채널을 설정합니다.
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            println("MainActivity: Method call received: ${call.method}")
            
            // printerHelper가 초기화되었는지 확인
            if (!::printerHelper.isInitialized) {
                println("MainActivity: PrinterHelper not initialized")
                result.error("NOT_INITIALIZED", "PrinterHelper is not initialized.", null)
                return@setMethodCallHandler
            }

            when (call.method) {
                "printText" -> {
                    val text = call.argument<String>("text")
                    if (text != null) {
                        println("MainActivity: Printing text: ${text.take(50)}...")
                        val printResult = printerHelper.printText(text)
                        result.success(printResult)
                    } else {
                        result.error("INVALID_ARGUMENT", "Text to print is null.", null)
                    }
                }
            }
        }
        println("MainActivity: Method channel setup completed")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::printerHelper.isInitialized) {
            printerHelper.closePrinter()
        }
    }
}

