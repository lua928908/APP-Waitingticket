package com.example.app_waitingticket

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import printer.PrinterHelper
import java.io.File

class MainActivity: FlutterActivity() {
    private val CHANNEL = "net.waytalk.didpager/printer"
    private var printerHelper: PrinterHelper? = null

    // [추가] 외부 개발사 코드에서 가져온 권한 요청 관련 상수들
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    // [수정] configureFlutterEngine 대신 onCreate 메서드를 사용해 초기화 로직을 구성합니다.
    // onCreate는 액티비티가 생성될 때 가장 먼저 호출되는 생명주기 메서드입니다.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("=== MainActivity: onCreate START ===")
        // [추가] 외부 개발사 코드의 핵심 기능인 저장소 권한 확인 및 요청 로직을 실행합니다.
        verifyStoragePermissions(this)
        requestPermission()

        // [이동] 기존 configureFlutterEngine에 있던 프린터 및 채널 설정 로직을 onCreate로 이동합니다.
        // flutterEngine이 null이 아닌지 확인하여 안정성을 높입니다.
        flutterEngine?.dartExecutor?.binaryMessenger?.let { messenger ->
            println("MainActivity: Setting up method channel...")
            MethodChannel(messenger, CHANNEL).setMethodCallHandler { call, result ->
                println("MainActivity: Method call received: ${call.method}")

                if (printerHelper == null) {
                    println("MainActivity: PrinterHelper not initialized, initializing now...")
                    initializePrinterHelper()
                }

                when (call.method) {
                    "printText" -> {
                        val text = call.argument<String>("text")
                        if (text != null) {
                            val printResult = printerHelper?.printText(text) ?: "PrinterHelper not initialized"
                            result.success(printResult)
                        } else {
                            result.error("INVALID_ARGUMENT", "Text to print is null.", null)
                        }
                    }
                    "printerStatus" -> {
                        try {
                            val status = printerHelper?.printerStatus() ?: -1
                            println("MainActivity: Checking printer status: $status")
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
    }

    private fun initializePrinterHelper() {
        try {
            println("MainActivity: Creating PrinterHelper instance...")
            printerHelper = PrinterHelper()
            println("MainActivity: PrinterHelper instance created successfully")

            val openResult = printerHelper?.openPrinter()
            println("MainActivity: Printer open result: $openResult")

        } catch (e: Throwable) {
            println("MainActivity: CRITICAL Error during printer initialization: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        printerHelper?.closePrinter()
    }

    // [추가] 외부 개발사 코드에 있던 저장소 권한 확인 메서드
    fun verifyStoragePermissions(activity: FlutterActivity) {
        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    // [추가] 외부 개발사 코드에 있던 임시 폴더 생성 및 권한 요청 메서드
    private fun requestPermission() {
        val tempFolder = File("/sdcard/Temp")
        if (!tempFolder.exists()) {
            println("Temp folder does not exist. Attempting to create.")
            val result = tempFolder.mkdir()
            println("Folder creation result: $result")
        } else {
            println("Temp folder already exists.")
        }
    }
}