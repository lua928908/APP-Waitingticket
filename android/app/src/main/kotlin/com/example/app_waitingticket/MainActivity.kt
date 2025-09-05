package com.example.app_waitingticket

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import com.example.app_waitingticket.printer.PrinterHelper
import java.io.File

class MainActivity: FlutterActivity() {
    private val CHANNEL = "net.waytalk.didpager/printer"
    // [수정] PrinterHelper 인스턴스를 미리 생성하지 않고 null로 둡니다.
    private var printerHelper: PrinterHelper? = null

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("=== MainActivity: onCreate START ===")
        verifyStoragePermissions(this)
        requestPermission()

        flutterEngine?.dartExecutor?.binaryMessenger?.let { messenger ->
            println("MainActivity: Setting up method channel...")
            MethodChannel(messenger, CHANNEL).setMethodCallHandler { call, result ->

                // [핵심 수정] 모든 호출을 UI 스레드에서 처리하도록 보장합니다.
                runOnUiThread {
                    // [핵심 수정] 프린터 관련 작업이 필요할 때마다 PrinterHelper를 새로 생성합니다.
                    // 이것이 Factory Test의 동작 방식을 가장 정확하게 모방하는 방법입니다.
                    if (printerHelper == null) {
                        printerHelper = PrinterHelper.getInstance()
                    }

                    when (call.method) {
                        "printText" -> {
                            val text = call.argument<String>("text")
                            println("MainActivity: printText called on UI thread.")
                            if (text != null) {
                                val printResult = printerHelper?.printText(text) ?: "PrinterHelper is null."
                                result.success(printResult)
                            } else {
                                result.error("INVALID_ARGUMENT", "Text to print is null.", null)
                            }
                        }
                        "printerStatus" -> {
                            println("MainActivity: printerStatus called on UI thread.")
                            val status = printerHelper?.printerStatus() ?: -1
                            result.success(status)
                        }
                        "printTest" -> {
                            println("MainActivity: printTest called on UI thread.")
                            val text = call.argument<String>("text")
//                            val printResult = printerHelper?.printSimpleTest("출력하기") ?: "PrinterHelper is null."
//                            val printResult = printerHelper?.printBasedOnDocs() ?: "PrinterHelper is null."
                            val printResult = printerHelper?.printPureTextTest() ?: "PrinterHelper is null."
                            result.success(printResult)
                        }
                    }
                }
            }
        }
    }

    // ... (onDestroy, verifyStoragePermissions, requestPermission 메서드는 기존과 동일) ...
    override fun onDestroy() {
        super.onDestroy()
        // 앱이 종료될 때만 close를 호출합니다.
        printerHelper?.closePrinter()
    }

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

    private fun requestPermission() {
        val tempFolder = File("/sdcard/Temp")
        if (!tempFolder.exists()) {
            tempFolder.mkdir()
        }
    }
}

