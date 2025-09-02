package com.example.app_waitingticket

import io.flutter.embedding.android.FlutterActivity
import androidx.annotation.NonNull
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import printer.PrinterHelper

class MainActivity: FlutterActivity() {
    private val CHANNEL = "net.waytalk.didpager/printer"
    private lateinit var printerHelper: PrinterHelper // PrinterHelper 인스턴스 선언

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // PrinterHelper 인스턴스 생성 및 프린터 열기
        printerHelper = PrinterHelper()
        printerHelper.openPrinter() // 앱 실행 시 프린터 열기

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
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
                else -> {
                    result.notImplemented()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        printerHelper.closePrinter() // 앱 종료 시 프린터 닫기
    }
}

