package com.example.app_waitingticket.printer.fixedKichenPrint;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;

//import com.kovan.kovanprintlib.usbSerial.driver.UsbSerialDriver;
//import com.kovan.kovanprintlib.usbSerial.driver.UsbSerialPort;
//import com.kovan.kovanprintlib.usbSerial.driver.UsbSerialProber;
//import com.kovan.kovanprintlib.usbSerial.util.SerialInputOutputManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

public class KovanPrintLib {
//    private static final String TAG = KovanPrintLib.class.getSimpleName();
//    private static final int WRITE_WAIT_MILLIS = 2000;
//    private static final int READ_WAIT_MILLIS = 2000;
//    private static final int SOCK_CONNECT_TIMEOUT = 10000;
//    private static final String INTENT_ACTION_GRANT_USB = KovanPrintLib.class.getSimpleName() + ".GRANT_USB";
//    private static final String DEVICE_NAME = "DEVICE_NAME";
//    private static final String DEVICE_BAUD = "DEVICE_BAUD";
//    private Context context;
//    private UsbSerialPort usbSerialPort;
//    private boolean withIoManager;
//    private SerialInputOutputManager usbIoManager;
//    private boolean usbConnected = false;
//    private final BroadcastReceiver broadcastReceiver;
//    private KovanPrintDefine.UsbPermission usbPermission = KovanPrintDefine.UsbPermission.Unknown;
//
//
//    private Socket socket;
//    private DataOutputStream outputStream = null;
//    private InputStream inputStream = null;
//
//    @Keep
//    public interface sockListener{
//        @Keep
//        void onResult(KovanPrintException e);
//    }
//
//    @Keep
//    public KovanPrintLib(@NonNull Context context) {
//        this.context = context;
//
//        broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if(INTENT_ACTION_GRANT_USB.equals(intent.getAction())) {
//                    usbPermission = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)
//                            ? KovanPrintDefine.UsbPermission.Granted : KovanPrintDefine.UsbPermission.Denied;
//                    /**
//                     * TODO USB Perminnsion Grant Event
//                     */
//
//                    String port = intent.getStringExtra(DEVICE_NAME);
//                    int baud = intent.getIntExtra(DEVICE_BAUD,115200);
//
//                    Log.e(TAG,"broadcastReceiver");
//                    Log.e(TAG,"[port] " + port + " [BAUD]" + baud);
//
//                    //Toast.makeText(context,"[port] " + port + " [BAUD]" + baud, Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
//    }
//
//    @Keep
//    public List<String> getUsbDeviceList(){
//        ArrayList<String> list = (ArrayList<String>) deviceList();
//
//        if(list == null){
//            return null;
//        }
//
//        if(list.size() == 0){
//            return null;
//        }
//
//        return list;
//    }
//
//    @Keep
//    public void sockPrint(String ip, int port, KovanPrintDefine.MAX_LINE_LEN lineLen, int len, byte[] data, sockListener listener){
//        sockProc sockProc = new sockProc(ip, port, lineLen.getLength(), len, data,listener);
//
//        sockProc.start();
//    }
//
//    @Keep
//    public void sockPrint(String ip, int port, int lineLen,int len,byte[] data, sockListener listener){
//        sockProc sockProc = new sockProc(ip, port, lineLen, len, data,listener);
//
//        sockProc.start();
//    }
//
//    /**
//     *
//     * @param port /dev/usb/tty/001/00.....
//     * @param baud 38400 / 115200...
//     * @param lineLen 한줄에 표기 될 최대 글자 수
//     * @throws KovanPrintException
//     */
//    @Keep
//    public void setUsbDevice(String port, int baud, int lineLen) throws KovanPrintException {
//        if(port == null){
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E001.getCode(), KovanPrintDefine.ERROR.E001.getMsg());
//        }
//
//        if(port.isEmpty() || port.equals("")){
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E001.getCode(), KovanPrintDefine.ERROR.E001.getMsg());
//        }
//
//        if(baud < 0){
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E002.getCode(), KovanPrintDefine.ERROR.E002.getMsg());
//        }
//        //SharedPreferenceUtils.remove(context,DEF.FILE_USB_INFO);
//
//        PreferenceUtils.putString(context, KovanPrintDefine.FILE_DEVICE_INFO, KovanPrintDefine.DEVICE_INFO.PORT.getKey(), port);
//        PreferenceUtils.putInteger(context, KovanPrintDefine.FILE_DEVICE_INFO, KovanPrintDefine.DEVICE_INFO.BAUD.getKey(), baud);
//        PreferenceUtils.putInteger(context, KovanPrintDefine.FILE_DEVICE_INFO, KovanPrintDefine.DEVICE_INFO.LEN.getKey(), lineLen);
//
//        UsbDevice device = null;
//        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
//        for(UsbDevice v : usbManager.getDeviceList().values()) {
//            if(v.getDeviceName().equals(port)){
//                device = v;
//            }
//        }
//        if(device == null) {
//            //연결된 장치가 없음
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E005.getCode(), KovanPrintDefine.ERROR.E005.getMsg());
//        }
//        UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
//
//        if(driver == null) {
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E006.getCode(), KovanPrintDefine.ERROR.E006.getMsg());
//        }
//        if(driver.getPorts().size() < 0) {
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E007.getCode(), KovanPrintDefine.ERROR.E007.getMsg());
//        }
//        usbSerialPort = driver.getPorts().get(0);
//        //UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());
//
//        usbPermission = KovanPrintDefine.UsbPermission.Requested;
//        //PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(INTENT_ACTION_GRANT_USB), 0);
//        Intent intent = new Intent(INTENT_ACTION_GRANT_USB);
//        intent.putExtra(DEVICE_NAME,port);
//        intent.putExtra(DEVICE_BAUD,baud);
//
//        PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//        usbManager.requestPermission(driver.getDevice(), usbPermissionIntent);
//    }
//
//    /**
//     *
//     * @param port /dev/usb/tty/001/00.....
//     * @param baud 38400 / 115200...
//     * @param lineLen 한줄에 표기 될 최대 글자 수
//     * @throws KovanPrintException
//     */
//    @Keep
//    public void setUsbDevice(String port, int baud, KovanPrintDefine.MAX_LINE_LEN lineLen) throws KovanPrintException {
//        if(port == null){
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E001.getCode(), KovanPrintDefine.ERROR.E001.getMsg());
//        }
//
//        if(port.isEmpty() || port.equals("")){
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E001.getCode(), KovanPrintDefine.ERROR.E001.getMsg());
//        }
//
//        if(baud < 0){
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E002.getCode(), KovanPrintDefine.ERROR.E002.getMsg());
//        }
//        //SharedPreferenceUtils.remove(context,DEF.FILE_USB_INFO);
//
//        PreferenceUtils.putString(context, KovanPrintDefine.FILE_DEVICE_INFO, KovanPrintDefine.DEVICE_INFO.PORT.getKey(), port);
//        PreferenceUtils.putInteger(context, KovanPrintDefine.FILE_DEVICE_INFO, KovanPrintDefine.DEVICE_INFO.BAUD.getKey(), baud);
//        PreferenceUtils.putInteger(context, KovanPrintDefine.FILE_DEVICE_INFO, KovanPrintDefine.DEVICE_INFO.LEN.getKey(), lineLen.getLength());
//
//        UsbDevice device = null;
//        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
//        for(UsbDevice v : usbManager.getDeviceList().values()) {
//            if(v.getDeviceName().equals(port)){
//                device = v;
//            }
//        }
//        if(device == null) {
//            //연결된 장치가 없음
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E005.getCode(), KovanPrintDefine.ERROR.E005.getMsg());
//        }
//        UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
//
//        if(driver == null) {
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E006.getCode(), KovanPrintDefine.ERROR.E006.getMsg());
//        }
//        if(driver.getPorts().size() < 0) {
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E007.getCode(), KovanPrintDefine.ERROR.E007.getMsg());
//        }
//        usbSerialPort = driver.getPorts().get(0);
//        //UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());
//
//        usbPermission = KovanPrintDefine.UsbPermission.Requested;
//        //PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(INTENT_ACTION_GRANT_USB), 0);
//        Intent intent = new Intent(INTENT_ACTION_GRANT_USB);
//        intent.putExtra(DEVICE_NAME,port);
//        intent.putExtra(DEVICE_BAUD,baud);
//
//        PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//        usbManager.requestPermission(driver.getDevice(), usbPermissionIntent);
//    }
//
//    @Keep
//    public void usbPrint(byte[] data) throws KovanPrintException {
//        String port = PreferenceUtils.getString(context, KovanPrintDefine.FILE_DEVICE_INFO, KovanPrintDefine.DEVICE_INFO.PORT.getKey());
//        int baud = PreferenceUtils.getInteger(context, KovanPrintDefine.FILE_DEVICE_INFO, KovanPrintDefine.DEVICE_INFO.BAUD.getKey(),-1);
//
//        if(port == null){
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E010.getCode(), KovanPrintDefine.ERROR.E010.getMsg());
//        }
//
//        if(port.equals("") || port.isEmpty()){
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E010.getCode(), KovanPrintDefine.ERROR.E010.getMsg());
//        }
//
//        if(baud < 0){
//            throw new KovanPrintException(KovanPrintDefine.ERROR.E011.getCode(), KovanPrintDefine.ERROR.E011.getMsg());
//        }
//
//        KovanPrintException open = usbConnect(port, baud);
//
//        if(open.getCode() != KovanPrintDefine.ERROR.S000.getCode()){
//            throw new KovanPrintException(open.getCode(),open.getMsg());
//        }
//
//        KovanPrintException send = usbSend(data);
//
//        if(send.getCode() != KovanPrintDefine.ERROR.S000.getCode()){
//            throw new KovanPrintException(send.getCode(),send.getMsg());
//        }
//
//        usbDisconnect();
//    }
//
//    /*
//     * Serial
//     */
//    private KovanPrintException usbConnect(String port, int baudRate) {
//        UsbDevice device = null;
//        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
//        for(UsbDevice v : usbManager.getDeviceList().values()) {
//            /*if (v.getDeviceId() == deviceId)
//                device = v;*/
//            if(v.getDeviceName().equals(port)){
//                device = v;
//            }
//        }
//        if(device == null) {
//            //연결된 장치가 없음
//            return new KovanPrintException(KovanPrintDefine.ERROR.E005.getCode(), KovanPrintDefine.ERROR.E005.getMsg());
//        }
//        UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
//
//        if(driver == null) {
//            //사용 가능 드라이버가 없음
//            return new KovanPrintException(KovanPrintDefine.ERROR.E006.getCode(), KovanPrintDefine.ERROR.E006.getMsg());
//        }
//        //if(driver.getPorts().size() < portNum) {
//        if(driver.getPorts().size() < 0) {
//            //사용 가능 포트가 없음
//            return new KovanPrintException(KovanPrintDefine.ERROR.E007.getCode(), KovanPrintDefine.ERROR.E007.getMsg());
//        }
//        //usbSerialPort = driver.getPorts().get(portNum);
//        usbSerialPort = driver.getPorts().get(0);
//        UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());
//        if(usbConnection == null && usbPermission == KovanPrintDefine.UsbPermission.Unknown && !usbManager.hasPermission(driver.getDevice())) {
//            //포트 권한 요청
//            usbPermission = KovanPrintDefine.UsbPermission.Requested;
//            PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(INTENT_ACTION_GRANT_USB), 0);
//            usbManager.requestPermission(driver.getDevice(), usbPermissionIntent);
//            return new KovanPrintException(KovanPrintDefine.ERROR.E008.getCode(), KovanPrintDefine.ERROR.E008.getMsg());
//        }
//        if(usbConnection == null) {
//            if (!usbManager.hasPermission(driver.getDevice())){
//                //포트 권한 거절
//                return new KovanPrintException(KovanPrintDefine.ERROR.E003.getCode(), KovanPrintDefine.ERROR.E003.getMsg());
//            }
//            else{
//                //포트 오픈 실패
//                return new KovanPrintException(KovanPrintDefine.ERROR.E009.getCode(), KovanPrintDefine.ERROR.E009.getMsg());
//            }
//
//        }
//
//        try {
//            usbSerialPort.open(usbConnection);
//            usbSerialPort.setParameters(baudRate, 8, 1, UsbSerialPort.PARITY_NONE);
//            //퍼지
//            usbSerialPort.purgeHwBuffers(true, true);
//
///*            if(withIoManager) {
//                usbIoManager = new SerialInputOutputManager(usbSerialPort, this);
//                usbIoManager.start();
//            }
//            */
//
//            //성공
//            usbConnected = true;
//            return new KovanPrintException(KovanPrintDefine.ERROR.S000.getCode(), KovanPrintDefine.ERROR.S000.getMsg());
//        } catch (Exception e) {
//            //status("connection failed: " + e.getMessage());
//            usbDisconnect();
//            return new KovanPrintException(KovanPrintDefine.ERROR.E999.getCode(),e.getMessage());
//        }
//    }
//
//    private void usbDisconnect() {
//        usbConnected = false;
//        if(usbIoManager != null) {
//            usbIoManager.setListener(null);
//            usbIoManager.stop();
//        }
//        usbIoManager = null;
//        try {
//            usbSerialPort.close();
//        } catch (IOException ignored) {}
//        usbSerialPort = null;
//    }
//
//    private KovanPrintException usbSend(byte[] data) {
//        if(!usbConnected) {
//            //Toast.makeText(getActivity(), "not connected", Toast.LENGTH_SHORT).show();
//            return new KovanPrintException(KovanPrintDefine.ERROR.E012.getCode(), KovanPrintDefine.ERROR.E012.getMsg());
//        }
//
//        if(data == null){
//            return new KovanPrintException(KovanPrintDefine.ERROR.E014.getCode(), KovanPrintDefine.ERROR.E014.getMsg());
//        }
//
//        if(data.length == 0){
//            return new KovanPrintException(KovanPrintDefine.ERROR.E014.getCode(), KovanPrintDefine.ERROR.E014.getMsg());
//        }
//
//        try {
//            usbSerialPort.write(data, WRITE_WAIT_MILLIS);
//            return new KovanPrintException(KovanPrintDefine.ERROR.S000.getCode(), KovanPrintDefine.ERROR.S000.getMsg());
//        } catch (Exception e) {
//            return new KovanPrintException(KovanPrintDefine.ERROR.E012.getCode(), KovanPrintDefine.ERROR.E012.getMsg());
//        }
//    }
//
//    private List<String> deviceList(){
//        ArrayList<String> arrDevice = new ArrayList<>();
//
//        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
//        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
//        Iterator<UsbDevice> it = deviceList.values().iterator();
//
//        if (deviceList.size() == 0)
//            return null;
//
//        while (it.hasNext()) {
//            UsbDevice device = it.next();
//            arrDevice.add(device.getDeviceName());
//        }
//
//        return arrDevice;
//    }
//
//
//    /*
//     * Socket
//     */
//    private KovanPrintException sockConnect(String ip, int port, int timeout) {
//        Log.e(TAG, "SocketEX Connect Request Start " + ip + "|" + port);
//        if(socket == null){
//            socket = new Socket();
//        }
//
//        try {
//            socket.connect(new InetSocketAddress(ip, port), timeout);
//            Log.e(TAG, "Socket Connect");
//            return new KovanPrintException(KovanPrintDefine.ERROR.S000.getCode(), KovanPrintDefine.ERROR.S000.getMsg());
//        } catch (UnknownHostException e) {
//            //IP 표기 방법이 잘못 된 경우
//            Log.e(TAG, "UnKnownHostException");
//            e.printStackTrace();
//           /* try {
//                socket.close();
//            } catch (IOException f) {
//                f.printStackTrace();
//            }*/
//            return new KovanPrintException(KovanPrintDefine.ERROR.E016.getCode(), KovanPrintDefine.ERROR.E016.getMsg());
//        } catch (IOException e) {
//            //해당 서버 포트의 접속이 불가능 한 경우
//            Log.e(TAG, "Server Connect Fail");
//            e.printStackTrace();
//         /*   try {
//                socket.close();
//            } catch (IOException f) {
//                f.printStackTrace();
//            }*/
//            return new KovanPrintException(KovanPrintDefine.ERROR.E017.getCode(), KovanPrintDefine.ERROR.E017.getMsg());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new KovanPrintException(KovanPrintDefine.ERROR.E999.getCode(), e.getMessage());
//        }
//    }
//
//    private void sockDisconnect() {
//        try {
//            if (socket != null) {
//                socket.close();
//                socket = null;
//            }
//            if (outputStream != null) {
//                outputStream.close();
//                outputStream = null;
//            }
//            if (inputStream != null) {
//                inputStream.close();
//                inputStream = null;
//            }
//            Log.e(TAG, "Socket Close");
//
//        } catch (IOException f) {
//            f.printStackTrace();
//            Log.e(TAG, "Socket Close IOException");
//
//        }
//    }
//
//    /**
//     *
//     * @param len
//     * @param data
//     * @return send Length
//     */
//    private int sockSend(int len , byte[] data){
//        int res = 0;
//
//        outputStream = null;
//        try {
//            outputStream = new DataOutputStream(socket.getOutputStream());
//            outputStream.write(data, 0, len);
//            outputStream.flush();
//            Log.e(TAG, "Send Data[" + len + "][" + outputStream.size() + "] : " + KovanEncodingUtils.ByteArrayToHexString(data));
//            Log.e(TAG, "Send Data[" + len + "][" + outputStream.size() + "] : " + KovanEncodingUtils.ByteArrayToString(data));
//            res = outputStream.size();
//        } catch (IOException e) {
//            //File 스트림 헤더의 출력 중 입출력 에러가 발생한 경우.
//            Log.e(TAG, "Write Fail");
//            e.printStackTrace();
//            res = 0;
//        } finally {
//            Log.e(TAG, "Send Finish : " + res);
//
//            return res;
//        }
//    }
//    @Keep
//    private class sockProc extends Thread{
//        private int len, lineLen;
//        private String ip;
//        private int port;
//
//        private byte[] data;
//        private sockListener listener;
//
//        public sockProc(String ip, int port, int lineLen, int len,@NonNull byte[] data,@NonNull sockListener listener) {
//            this.len = len;
//            this.lineLen = lineLen;
//            this.ip = ip;
//            this.port = port;
//            this.data = data;
//            this.listener = listener;
//        }
//
//        @Override
//        public void run() {
///*            String ip = PreferenceUtils.getString(context, PrintDefine.FILE_DEVICE_INFO, PrintDefine.DEVICE_INFO.TCP_IP.getKey());
//            int port = PreferenceUtils.getInteger(context, PrintDefine.FILE_DEVICE_INFO, PrintDefine.DEVICE_INFO.TCP_PORT.getKey(),-1);*/
//            int send = 0;
//
//            if(!KovanEncodingUtils.checkPermission(context, Manifest.permission.INTERNET)){
//                listener.onResult( new KovanPrintException(KovanPrintDefine.ERROR.E015.getCode(), KovanPrintDefine.ERROR.E015.getMsg()));
//                return;
//            }
//
//            if(ip == null){
//                listener.onResult( new KovanPrintException(KovanPrintDefine.ERROR.E018.getCode(), KovanPrintDefine.ERROR.E018.getMsg()));
//                return;
//            }
//
//            if(ip.equals("") || ip.isEmpty()){
//                listener.onResult( new KovanPrintException(KovanPrintDefine.ERROR.E018.getCode(), KovanPrintDefine.ERROR.E018.getMsg()));
//                return;
//            }
//
//            if(port < 0){
//                listener.onResult( new KovanPrintException(KovanPrintDefine.ERROR.E010.getCode(), KovanPrintDefine.ERROR.E010.getMsg()));
//                return;
//            }
//
//            KovanPrintException open = sockConnect(ip, port, SOCK_CONNECT_TIMEOUT);
//
//            if(open.getCode() != KovanPrintDefine.ERROR.S000.getCode()){
//                sockDisconnect();
//                listener.onResult( new KovanPrintException(open.getCode(),open.getMsg()));
//                return;
//            }
//
//            send = sockSend(len,data);
//
//            if(send != len){
//                sockDisconnect();
//                listener.onResult(new KovanPrintException(KovanPrintDefine.ERROR.E019.getCode(), KovanPrintDefine.ERROR.E019.getMsg()));
//                return;
//            }
//
//            sockDisconnect();
//
//            listener.onResult(new KovanPrintException(KovanPrintDefine.ERROR.S000.getCode(), KovanPrintDefine.ERROR.S000.getMsg()));
//            return;
//        }
//    }
//
//    /**
//     * @param bitmap
//     * @return resize 128 * 64 Data
//     * @throws KovanPrintException
//     */


}


