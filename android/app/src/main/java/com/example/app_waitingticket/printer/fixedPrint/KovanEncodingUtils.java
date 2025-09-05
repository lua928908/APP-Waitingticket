package com.example.app_waitingticket.printer.fixedPrint;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class KovanEncodingUtils {
    /***
     * Base 64 Util
     ***/
    @Keep
    public static String getBase64Encode(byte[] data){
        return Base64.encodeToString(data,Base64.NO_WRAP);
    }
    @Keep
    public static String getBase64Encode(String data){
        return Base64.encodeToString(data.getBytes(),Base64.NO_WRAP);
    }
    @Keep
    public static String getBase64Encode(byte[] data, int mode){
        return Base64.encodeToString(data,mode);
    }
    @Keep
    public static String getBase64Encode(String data, int mode){
        return Base64.encodeToString(data.getBytes(),mode);
    }
    @Keep
    public static String getBase64Decode(String data){
        return new String(Base64.decode(data,Base64.NO_WRAP));
    }
    @Keep
    public static String getBase64Decode(String data, int mode){
        return new String(Base64.decode(data, mode));
    }
    @Keep
    public static byte[] getBase64DecodeByte(String data) {
        try {
            return Base64.decode(data, Base64.NO_WRAP);
        }
        catch(IllegalArgumentException e){
            return null;
        }
    }

    protected static int getByteLength(String s){
        int i = 0;
        byte[] b;

        try{
            b = s.getBytes("euc-kr");
        }
        catch (UnsupportedEncodingException e){
            b = s.getBytes();
        }

        if(b != null){
            i = b.length;
        }

        return i;
    }

    protected static byte[] StringToByteArray(String s){
        byte[] b;

        try{
            b = s.getBytes("euc-kr");
        }
        catch (UnsupportedEncodingException e){
            b = s.getBytes();
        }
        return b;
    }

    public static String ByteArrayToString(byte[] p){
        String s;

        try{
            s = new String(p,"euc-kr");
        }
        catch (UnsupportedEncodingException e){
            s = new String(p);
        }
        return s;
    }

    public static String ByteArrayToHexString(byte[] p){
        StringBuilder sb = new StringBuilder();

        for(byte b : p){
            sb.append(String.format("%02X ",b&0xff));
        }

        return sb.toString();
    }
    public static String ByteArrayToHexString(byte[] _pSrc, int _iLen){
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for(byte b : _pSrc){
            if(i >= _iLen)
                break;
            sb.append(String.format("%02X ",b&0xff));
            i++;
        }

        return sb.toString();
    }

    protected static char[] ByteArrayToCharArray(byte[] _pSrc){
        char[] pRet = new char[_pSrc.length/2];

        for(int i = 0; i <pRet.length; i++){
            pRet[i] = (char)(((_pSrc[i*2] & 0xFF) <<8) + (_pSrc[i*2+1] & 0xFF));
        }

        return pRet;
    }

    @Keep
    public static Bitmap ByteArrayToBitmap(@NonNull byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length) ;
        return bitmap ;
    }

    protected static void clearArray(byte[]... arr){
        for(byte[] b : arr){
            Arrays.fill(b,(byte)0x00);
            Arrays.fill(b,(byte)0xFF);
            Arrays.fill(b,(byte)0x00);
            b = null;
        }
    }
    @Keep
    public static String getBmpBase64(@NonNull Bitmap bitmap)  throws KovanPrintException {
        byte[] res = null;

        Bitmap conv = KovanBmpUtils.reSizeBitmap(bitmap);

        try{
            res = KovanBmpUtils.getBmpRowBytes(conv);
        }
        catch (KovanPrintException e){
            throw e;
        }

        if(res == null){
            throw new KovanPrintException(KovanPrintDefine.ERROR.E021.getCode(), KovanPrintDefine.ERROR.E021.getMsg());
        }
        //Log.e(TAG, "[getBmpBase64]" + EncodingUtils.ByteArrayToHexString(res));
        return KovanEncodingUtils.getBase64Encode(res);
    }

    protected static boolean checkPermission(Context context, String permission) {
        boolean isCheck = false;

        int check = ContextCompat.checkSelfPermission(context, permission);

        if (check == PackageManager.PERMISSION_GRANTED) {
            isCheck = true;
        } else {
            isCheck = false;
        }

        return isCheck;
    }

    protected static byte[] mergeArrays(byte[]... arrays) {
        int totalLength = 0;
        for (byte[] arr : arrays) {
            if (arr != null && arr.length > 0) {
                totalLength += arr.length;
            }
        }
        byte[] mergedArrays = new byte[totalLength];
        int destPos = 0;
        for (byte[] arr : arrays) {
            if (arr != null && arr.length > 0) {
                System.arraycopy(arr, 0, mergedArrays, destPos, arr.length);
                destPos += arr.length;
            }
        }
        return mergedArrays;
    }
}
