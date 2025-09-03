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

        // 1. PrinterHelper 인스턴스를 생성하고 프린터를 엽니다.
        try {
            printerHelper = PrinterHelper()
            val openResult = printerHelper.openPrinter()
            println("MainActivity: Printer open result: $openResult")
        } catch (e: Throwable) { // UnsatisfiedLinkError 등 모든 오류를 잡기 위해 Throwable 사용
            println("MainActivity: CRITICAL Error during printer initialization: ${e.message}")
            e.printStackTrace()
        }

        // 2. 메서드 채널을 설정합니다.
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            // printerHelper가 초기화되었는지 확인
            if (!::printerHelper.isInitialized) {
                result.error("NOT_INITIALIZED", "PrinterHelper is not initialized.", null)
                return@setMethodCallHandler
            }

            when (call.method) {
                "printText" -> {
                    val text = call.argument<String>("text")
                    if (text != null) {
                        val printResult = printerHelper.printText(text)
                        result.success(printResult)
                    } else {
                        result.error("INVALID_ARGUMENT", "Text to print is null.", null)
                    }
                }
                "openCashDrawer" -> {
                    val cashDrawerResult = printerHelper.openCashDrawer()
                    result.success(cashDrawerResult)
                }
                else -> result.notImplemented()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::printerHelper.isInitialized) {
            printerHelper.closePrinter()
        }
    }
}

