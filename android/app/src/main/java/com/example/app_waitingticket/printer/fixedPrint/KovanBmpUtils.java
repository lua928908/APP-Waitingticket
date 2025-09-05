package com.example.app_waitingticket.printer.fixedPrint;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static com.example.app_waitingticket.printer.fixedPrint.KovanPrintDefine.SIGN_HEIGHT;
import static com.example.app_waitingticket.printer.fixedPrint.KovanPrintDefine.SIGN_WIDTH;

public class KovanBmpUtils {

    private static final int MULTIPLES_4 = 4;

    private static final int BYTE_PIXEL = 3;

    private static String HEX_STRING = "0123456789ABCDEF";

    private static String[] BIN_ARRAY = { "0000", "0001", "0010", "0011",
            "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
            "1100", "1101", "1110", "1111" };

    protected static byte[] getBmpRowBytes(Bitmap bitmap) throws KovanPrintException {
        if(bitmap == null){
            throw new KovanPrintException(KovanPrintDefine.ERROR.E020.getCode(), KovanPrintDefine.ERROR.E020.getMsg());
        }

        //image size
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //image dummy data size
        //reason : bmp file's width equals 4's multiple
        int paddingSize = 0;
        byte[] arrPadding = null;
        boolean isPadding = false;
        if(isMultiples4(width)){
            isPadding = true;
            paddingSize = MULTIPLES_4 - (width % MULTIPLES_4);
            arrPadding = new byte[paddingSize * BYTE_PIXEL];
            for(int i = 0; i < arrPadding.length; i++){
                arrPadding[i] = (byte)0xFF;
            }
        }

        int[] pixels = new int[width * height];
        int imageSize = pixels.length * BYTE_PIXEL + (height * paddingSize * BYTE_PIXEL);
        int imageDataOffset = 0x36;
        int fileSize = imageSize + imageDataOffset;

        //Android Bitmap Image Data
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        ByteBuffer buffer = ByteBuffer.allocate(fileSize);

        try {
            /**
             * BITMAP FILE HEADER Write Start
             **/
            buffer.put((byte)0x42);
            buffer.put((byte)0x4D);

            //size
            buffer.put(writeInt(fileSize));

            //reserved
            buffer.put(writeShort((short)0));
            buffer.put(writeShort((short)0));

            //image data start offset
            buffer.put(writeInt(imageDataOffset));

            /** BITMAP FILE HEADER Write End */

            //*******************************************

            /** BITMAP INFO HEADER Write Start */
            //size
            buffer.put(writeInt(0x28));

            //width, height
            buffer.put(writeInt(width));
            buffer.put(writeInt(height));

            //planes
            buffer.put(writeShort((short)1));

            //bit count
            buffer.put(writeShort((short)24));

            //bit compression
            buffer.put(writeInt(0));

            //image data size
            buffer.put(writeInt(imageSize));

            //horizontal resolution in pixels per meter
            buffer.put(writeInt(0));

            //vertical resolution in pixels per meter (unreliable)
            buffer.put(writeInt(0));

            //pallet
            buffer.put(writeInt(0));

            //colors
            buffer.put(writeInt(0));

            /** BITMAP INFO HEADER Write End */

            int row = height;
            int col = width;
            int startPosition = 0;
            int endPosition = 0;

            while( row > 0 ){

                startPosition = (row - 1) * col;
                endPosition = row * col;

                for(int i = startPosition; i < endPosition; i++ ){
                    buffer.put(writePixel(pixels[i]));

                    if(isPadding){
                        if(isWidthEnd(width, i)){
                            buffer.put(arrPadding);
                        }
                    }
                }
                row--;
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new KovanPrintException(KovanPrintDefine.ERROR.E021.getCode(), KovanPrintDefine.ERROR.E021.getMsg());
        }

        return buffer.array();
    }

    /**
     * Is last pixel in Android Bitmap width
     * @param width
     * @param i
     * @return
     */
    private static boolean isWidthEnd(int width, int i) {
        return i > 0 && (i % (width - 1)) == 0;
    }

    /**
     * BMP file is a multiples of 4?
     * @param width
     * @return
     */
    private static boolean isMultiples4(int width) {
        return width % MULTIPLES_4 > 0;
    }

    /**
     * Write integer to little-endian
     * @param value
     * @return
     * @throws IOException
     */
    private static byte[] writeInt(int value) throws IOException {
        byte[] b = new byte[4];

        b[0] = (byte)(value & 0x000000FF);
        b[1] = (byte)((value & 0x0000FF00) >> 8);
        b[2] = (byte)((value & 0x00FF0000) >> 16);
        b[3] = (byte)((value & 0xFF000000) >> 24);

        return b;
    }

    /**
     * Write integer pixel to little-endian byte array
     * @param value
     * @return
     * @throws IOException
     */
    private static byte[] writePixel(int value) throws IOException {
        byte[] b = new byte[3];

        b[0] = (byte)(value & 0x000000FF);
        b[1] = (byte)((value & 0x0000FF00) >> 8);
        b[2] = (byte)((value & 0x00FF0000) >> 16);

        return b;
    }

    /**
     * Write short to little-endian byte array
     * @param value
     * @return
     * @throws IOException
     */
    private static byte[] writeShort(short value) throws IOException {
        byte[] b = new byte[2];

        b[0] = (byte)(value & 0x00FF);
        b[1] = (byte)((value & 0xFF00) >> 8);

        return b;
    }

    //print photo
    protected static byte[] getBmpEpson(@NonNull Bitmap bitmap) throws KovanPrintException {
        byte[] cmd = null;
        Bitmap bmp = bitmap;

        cmd = makeCommand(bmp);

        return cmd;
    }

    protected static byte[] getBmpEpson(@NonNull Bitmap bitmap, @NonNull KovanPrintDefine.PRINT_SORT sort, @NonNull KovanPrintDefine.BMP_OPTION mode) throws KovanPrintException {
        byte[] cmd = null;
        Bitmap bmp = bitmap;

        cmd = makeCommand(bmp, sort, mode);

        return cmd;
    }

    private static byte[] makeCommand(@NonNull Bitmap bmp){
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        List<String> list = new ArrayList<String>(); //binaryString list
        StringBuffer sb;


        int bitLen = width / 8;
        int zeroCount = width % 8;

        String zeroStr = "";
        if (zeroCount > 0) {
            bitLen = width / 8 + 1;
            for (int i = 0; i < (8 - zeroCount); i++) {
                zeroStr = zeroStr + "0";
            }
        }

        for (int i = 0; i < height; i++) {
            sb = new StringBuffer();
            for (int j = 0; j < width; j++) {
                int color = bmp.getPixel(j, i);

                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;

                // if color close to white，bit='0', else bit='1'
                if (r > 160 && g > 160 && b > 160)
                    sb.append("0");
                else
                    sb.append("1");
            }
            if (zeroCount > 0) {
                sb.append(zeroStr);
            }
            list.add(sb.toString());
        }

        List<String> bmpHexList = binListToHexList(list);
        String commandHexString = "1D763000";
        String widthHexString = Integer
                .toHexString(width % 8 == 0 ? width / 8
                        : (width / 8 + 1));
        if (widthHexString.length() > 2) {
            Log.e("decodeBitmap error", " width is too large");
            return null;
        } else if (widthHexString.length() == 1) {
            widthHexString = "0" + widthHexString;
        }
        widthHexString = widthHexString + "00";

        String heightHexString = Integer.toHexString(height);
        if (heightHexString.length() > 2) {
            Log.e("decodeBitmap error", " height is too large");
            return null;
        } else if (heightHexString.length() == 1) {
            heightHexString = "0" + heightHexString;
        }
        heightHexString = heightHexString + "00";

        List<String> commandList = new ArrayList<String>();
        commandList.add(commandHexString+widthHexString+heightHexString);
        commandList.addAll(bmpHexList);

        return hexList2Byte(commandList);
    }

    private static byte[] makeCommand(@NonNull Bitmap bmp, @NonNull KovanPrintDefine.PRINT_SORT sort, @NonNull KovanPrintDefine.BMP_OPTION mode){
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        List<String> list = new ArrayList<String>(); //binaryString list
        StringBuffer sb;

        int bitLen = width / 8;
        int zeroCount = width % 8;

        String zeroStr = "";
        if (zeroCount > 0) {
            bitLen = width / 8 + 1;
            for (int i = 0; i < (8 - zeroCount); i++) {
                zeroStr = zeroStr + "0";
            }
        }

        for (int i = 0; i < height; i++) {
            sb = new StringBuffer();
            for (int j = 0; j < width; j++) {
                int color = bmp.getPixel(j, i);

                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;

                // if color close to white，bit='0', else bit='1'
                if (r > 160 && g > 160 && b > 160)
                    sb.append("0");
                else
                    sb.append("1");
            }
            if (zeroCount > 0) {
                sb.append(zeroStr);
            }
            list.add(sb.toString());
        }

        List<String> bmpHexList = binListToHexList(list);
        String commandHexString;

        if(mode == KovanPrintDefine.BMP_OPTION.NORMAL){
            commandHexString = "1D763000";
        }
        else if(mode == KovanPrintDefine.BMP_OPTION.DOUBLE_WIDTH){
            commandHexString = "1D763031";
        }
        else if(mode == KovanPrintDefine.BMP_OPTION.DOUBLE_HEIGHT){
            commandHexString = "1D763032";
        }
        else{
            commandHexString = "1D763033";
        }

        String widthHexString = Integer
                .toHexString(width % 8 == 0 ? width / 8
                        : (width / 8 + 1));
        if (widthHexString.length() > 2) {
            Log.e("decodeBitmap error", " width is too large");
            return null;
        } else if (widthHexString.length() == 1) {
            widthHexString = "0" + widthHexString;
        }
        widthHexString = widthHexString + "00";

        String heightHexString = Integer.toHexString(height);
        if (heightHexString.length() > 2) {
            Log.e("decodeBitmap error", " height is too large");
            return null;
        } else if (heightHexString.length() == 1) {
            heightHexString = "0" + heightHexString;
        }
        heightHexString = heightHexString + "00";

        List<String> commandList = new ArrayList<String>();


        if(sort == KovanPrintDefine.PRINT_SORT.RIGHT){
            commandList.add("1B6132");
        }
        else if(sort == KovanPrintDefine.PRINT_SORT.LEFT){
            commandList.add("1B6130");
        }
        else{
            commandList.add("1B6131");
        }

        commandList.add(commandHexString+widthHexString+heightHexString);
        commandList.addAll(bmpHexList);

        return hexList2Byte(commandList);
    }

    protected static List<String> binListToHexList(List<String> list) {
        List<String> hexList = new ArrayList<String>();
        for (String s : list) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < s.length(); i += 8) {
                String str = s.substring(i, i + 8);

                String hexString = convBinaryToHex(str);
                sb.append(hexString);
            }
            hexList.add(sb.toString());
        }
        return hexList;

    }

    protected static String convBinaryToHex(String binary) {
        String hex = "";
        String f4 = binary.substring(0, 4);
        String b4 = binary.substring(4, 8);
        for (int i = 0; i < BIN_ARRAY.length; i++) {
            if (f4.equals(BIN_ARRAY[i]))
                hex += HEX_STRING.substring(i, i + 1);
        }
        for (int i = 0; i < BIN_ARRAY.length; i++) {
            if (b4.equals(BIN_ARRAY[i]))
                hex += HEX_STRING.substring(i, i + 1);
        }

        return hex;
    }

    protected static byte[] hexList2Byte(List<String> list) {
        List<byte[]> commandList = new ArrayList<byte[]>();

        for (String hexStr : list) {
            commandList.add(hexStringToBytes(hexStr));
        }
        byte[] bytes = sysCopy(commandList);
        return bytes;
    }

    protected static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    protected static byte[] sysCopy(List<byte[]> srcArrays) {
        int len = 0;
        for (byte[] srcArray : srcArrays) {
            len += srcArray.length;
        }
        byte[] destArray = new byte[len];
        int destLen = 0;
        for (byte[] srcArray : srcArrays) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
            destLen += srcArray.length;
        }
        return destArray;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    protected static boolean isReceiptSize(Bitmap bitmap){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        if(w != SIGN_WIDTH){
            return false;
        }

        if(h != SIGN_HEIGHT){
            return false;
        }

        return true;
    }

    protected static Bitmap reSizeBitmap(Bitmap bitmap){
        Bitmap resize = Bitmap.createScaledBitmap(bitmap, SIGN_WIDTH, SIGN_HEIGHT, true);

        //128 64 / 96 48 /64 32
        //Bitmap resize = Bitmap.createScaledBitmap(bitmap, 96, 48, true);
        return resize;
    }

    protected static void bmpInfo(Bitmap bitmap){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int c = bitmap.getByteCount();
        int ac = bitmap.getAllocationByteCount();
        int rb = bitmap.getRowBytes();
        int d = bitmap.getDensity();

        Log.e("[BITMAP}","\n[W]" + w + "\n[H]" + h + "\n[C]" + c + "\n[ac]" + ac + "\n[rb]" + rb +"\n[d]" + d);
    }
}
