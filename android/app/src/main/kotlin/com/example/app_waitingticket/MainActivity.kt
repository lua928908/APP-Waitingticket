import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import com.thirteenrain.jyndklib.jyNativeClass
import com.thirteenrain.jyndklib.jyprt

class MainActivity: FlutterActivity() {
    private val CHANNEL = "jy_printer"
    private lateinit var nativeClass: jyNativeClass
    private lateinit var printerCommands: jyprt  // 변수 이름 변경

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        nativeClass = jyNativeClass()
        printerCommands = jyprt()  // 변수 이름 변경

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "printerOpen" -> {
                    val openResult = nativeClass.jyPrinterOpen()
                    result.success(openResult)
                }
                "printerClose" -> {
                    val closeResult = nativeClass.jyPrinterClose()
                    result.success(closeResult)
                }
                "printerPaperCheck" -> {
                    val paperResult = nativeClass.jyPrinter_PaperCheck()
                    result.success(paperResult)
                }
                "printerCoverCheck" -> {
                    val coverResult = nativeClass.jyPrinter_CoverCheck()
                    result.success(coverResult)
                }
                "printerOverheatCheck" -> {
                    val overheatResult = nativeClass.jyPrinter_OverheatCheck()
                    result.success(overheatResult)
                }
                "printString" -> {
                    val data = call.argument<List<Int>>("data")
                    val sync = call.argument<Int>("sync") ?: 0
                    val byteArray = data?.map { it.toByte() }?.toByteArray()
                    val printResult = nativeClass.jyPrintString(byteArray, sync)
                    result.success(printResult)
                }
                "printRawData" -> {
                    val data = call.argument<List<Int>>("data")
                    val byteArray = data?.map { it.toByte() }?.toByteArray()
                    val rawResult = nativeClass.jyPrinterRawData(byteArray)
                    result.success(rawResult)
                }
                "cashdrawOpen" -> {
                    val cashResult = nativeClass.jyCashdraw_Open()
                    result.success(cashResult)
                }
                "cashdrawClose" -> {
                    val hDevice = call.argument<Int>("hDevice") ?: 0
                    val closeResult = nativeClass.jyCashdraw_Close(hDevice)
                    result.success(closeResult)
                }
                "cashdrawSignal" -> {
                    val dwMs = call.argument<Int>("dwMs") ?: 100
                    val signalResult = nativeClass.jyCashdraw_Signal(dwMs)
                    result.success(signalResult)
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }
}