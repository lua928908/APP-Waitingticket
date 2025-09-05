package com.example.app_waitingticket.printer.fixedPrint;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class UtilManager {

    public static void SaveIntegrityResult(Context context, String target, String result) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = dateFormat.format(new Date());
            JSONObject data = new JSONObject();
            data.put("date", date);
            data.put("target", target);
            data.put("result", result);

            String saveData = data.toString();
            File file = new File(context.getFilesDir().getAbsolutePath() + File.separator + "integrity_" + new Date().getTime() + ".txt");

            FileOutputStream os = new FileOutputStream(file);
            os.write(saveData.getBytes());
            os.close();
        } catch (Exception e) {

        }

        deleteIntegrityMaxCount(context);
    }

    private static void deleteIntegrityMaxCount(Context context) {
        List<File> fileList = new ArrayList<>();
        File directory = new File(context.getFilesDir().getAbsolutePath());
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            String fileName = files[i].getName();
            if(fileName.contains("integrity_")) {
                fileList.add(files[i]);
            }
        }

        if(fileList.size() >= 10) {
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    String fileName1 = file1.getName().replace("integrity_", "").replace(".txt", "");
                    String fileName2 = file2.getName().replace("integrity_", "").replace(".txt", "");
                    return fileName2.compareTo(fileName1);
                }
            });
            for(int i = fileList.size() - 1; i >= 10; i--) {
                fileList.get(i).delete();
            }
        }
    }

    public static JSONArray GetIntegrityList(Context context) {
        JSONArray list = new JSONArray();

        List<File> fileList = new ArrayList<>();
        File directory = new File(context.getFilesDir().getAbsolutePath());
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            String fileName = files[i].getName();
            if(fileName.contains("integrity_")) {
                fileList.add(files[i]);
            }
        }
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                String fileName1 = file1.getName().replace("integrity_", "").replace(".txt", "");
                String fileName2 = file2.getName().replace("integrity_", "").replace(".txt", "");
                return fileName2.compareTo(fileName1);
            }
        });

        for(int i = 0; i < fileList.size(); i++) {
            JSONObject fileData = getIntegrityData(context, fileList.get(i).getName());

            if(fileData != null) {
                try {
                    JSONObject data = new JSONObject();
                    data.put("date", fileData.optString("date").substring(0, 8));
                    data.put("time", fileData.optString("date").substring(8));
                    data.put("type", fileData.optString("target"));
                    data.put("result", fileData.optString("result"));

                    list.put(data);
                } catch (Exception e) {

                }
            }
        }

        return list;
    }

    private static JSONObject getIntegrityData(Context context, String fileName) {
        JSONObject data = null;
        StringBuffer strBuffer = new StringBuffer();
        try{
            InputStream is = new FileInputStream(context.getFilesDir().getAbsolutePath() + File.separator + fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while((line = reader.readLine())!=null){
                strBuffer.append(line+"\n");
            }

            reader.close();
            is.close();

            if(strBuffer.toString().length() > 0) {
                data = new JSONObject(strBuffer.toString());
            }
        }catch (Exception e){

        }

        return data;
    }

    public static String ConvertCurrencyFormat(String currency) {
        String returnValue;
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        if( currency != null && !currency.equals("") && !currency.isEmpty())
            returnValue = decimalFormat.format(Long.parseLong(currency));
        else
            returnValue = "";

        return returnValue;
    }

    public static byte[] MergeArrays(byte[]... arrays) {
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

//    public static boolean isServiceRunning(Context context) {
//        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
//
//        for (ActivityManager.RunningServiceInfo rsi : am.getRunningServices(Integer.MAX_VALUE)) {
//            if (BluetoothService.class.getName().equals(rsi.service.getClassName())) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    public static byte[] GetSignImageDataByZoa(byte[] signByte) {
        if(signByte != null && signByte.length == 1086) {
            byte[] data = new byte[1086];

            byte[] bmpHeader = {
                    (byte) 0x42, (byte) 0x4D, (byte) 0x3E, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x3E, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x28, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
                    (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x00};

            System.arraycopy(bmpHeader, 0, data, 0, bmpHeader.length);

            System.arraycopy(signByte, 62, data, 62, signByte.length - 62);

            return data;
        }

        return null;
    }
//
//    public static byte[] GetSignImageByteArray() {
//        byte[] imageByte = null;
//
//        try {
//            File signFile = new File(Constants.SignImagePath);
//            if(signFile.exists()) {
//                BufferedInputStream in = new BufferedInputStream(new FileInputStream(Constants.SignImagePath));
//                ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
//
//                byte[] temp = new byte[1024];
//                int size = 0;
//
//                while ((size = in.read(temp)) != -1) {
//                    out.write(temp, 0, size);
//                }
//
//                in.close();
//
//                imageByte = out.toByteArray();
//            }
//        } catch (Exception e) {
//
//        }
//
//        return imageByte;
//    }

    private static int makeLRC(byte[] bytes, int length)
    {
        int checksum = 0;
        //STX 1byte 뒤부터  ETX까지
        for (int i = 1; i < length; i++) {
            checksum ^= (bytes[i] & 0xFF);
        }
        return checksum;
    }
}
