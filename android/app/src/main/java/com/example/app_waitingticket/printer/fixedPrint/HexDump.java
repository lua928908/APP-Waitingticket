package com.example.app_waitingticket.printer.fixedPrint;

import android.util.Log;

import java.util.Arrays;

public class HexDump {

        private final static char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        private final static char[] HEX_LOWER_CASE_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        public static void dumpHexString(String TAG, byte[] array) {
            dumpHexString(TAG, array, 0, array.length);
        }

        public static void dumpHexString(String TAG, byte[] array, int offset, int length) {
            StringBuilder result = new StringBuilder();
            byte[] line = new byte[16];
            int lineIndex = 0;
            result.append("\n\n0x");
            result.append(toHexString(offset));
            for (int i = offset; i < offset + length; i++) {
                if (lineIndex == 16) {
                    result.append(" ");
                    for (int j = 0; j < 16; j++) {
                        if (line[j] > ' ' && line[j] < '~') {
                            result.append(new String(line, j, 1));
                        } else {
                            result.append(".");
                        }
                    }
                    result.append("\n0x");
                    result.append(toHexString(i));
                    lineIndex = 0;
                }
                byte b = array[i];
                result.append(" ");
                result.append(HEX_DIGITS[(b >>> 4) & 0x0F]);
                result.append(HEX_DIGITS[b & 0x0F]);
                line[lineIndex++] = b;
            }
            if (lineIndex != 16) {
                int count = (16 - lineIndex) * 3;
                count++;
                for (int i = 0; i < count; i++) {
                    result.append(" ");
                }
                for (int i = 0; i < lineIndex; i++) {
                    if (line[i] > ' ' && line[i] < '~') {
                        result.append(new String(line, i, 1));
                    } else {
                        result.append(".");
                    }
                }
            }


            Log.e(TAG, result.toString());

            result = null;

            System.gc();

        }



        public static String toHexString(byte b)
        {
            return toHexString(toByteArray(b));
        }
        public static String toHexString(byte[] array)
        {
            return toHexString(array, 0, array.length, true);
        }
        public static String toHexString(byte[] array, boolean upperCase)
        {
            return toHexString(array, 0, array.length, upperCase);
        }
        public static String toHexString(byte[] array, int offset, int length)
        {
            return toHexString(array, offset, length, true);
        }
        public static String toHexString(byte[] array, int offset, int length, boolean upperCase)
        {
            char[] digits = upperCase ? HEX_DIGITS : HEX_LOWER_CASE_DIGITS;
            char[] buf = new char[length * 2];
            int bufIndex = 0;
            for (int i = offset ; i < offset + length; i++)
            {
                byte b = array[i];
                buf[bufIndex++] = digits[(b >>> 4) & 0x0F];
                buf[bufIndex++] = digits[b & 0x0F];
            }

            String hexString = new String(buf);

            // clearBuffer(digits, buf);

            return hexString;
        }
        public static String toHexString(int i)
        {
            return toHexString(toByteArray(i));
        }
        public static byte[] toByteArray(byte b)
        {
            byte[] array = new byte[1];
            array[0] = b;
            return array;
        }
        public static byte[] toByteArray(int i)
        {
            byte[] array = new byte[4];
            array[3] = (byte)(i & 0xFF);
            array[2] = (byte)((i >> 8) & 0xFF);
            array[1] = (byte)((i >> 16) & 0xFF);
            array[0] = (byte)((i >> 24) & 0xFF);
            return array;
        }


        public static void clearBuffer(byte[]... datas) {
            for(int i=0; i<datas.length; i++) {
                if(datas[i] != null) {
                    Arrays.fill(datas[i], (byte) 0x00);
                    Arrays.fill(datas[i], (byte) 0xFF);
                    Arrays.fill(datas[i], (byte) 0x00);
                }
            }
        }

        public static void clearBuffer(char[]... datas) {
            for(int i=0; i<datas.length; i++) {
                if(datas[i] != null) {
                    Arrays.fill(datas[i], (char) 0x00);
                    Arrays.fill(datas[i], (char) 0xFF);
                    Arrays.fill(datas[i], (char) 0x00);
                }
            }
        }
}
