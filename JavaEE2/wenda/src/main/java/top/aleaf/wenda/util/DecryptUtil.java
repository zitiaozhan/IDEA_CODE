package top.aleaf.wenda.util;

import java.io.UnsupportedEncodingException;

/**
 * DES加密 解密算法
 *
 * @author lifq
 * @date 2015-3-17 上午10:12:11
 */
public class DecryptUtil {

    public static void main(String[] args) {
        System.out.println(encrypt("/index"));
        System.out.println(decrypt("2f757365722f3630"));
    }

    /**
     * 十六进制转中文字符串
     */
    public static String decrypt(String str) {
        if ( str == null ) {
            return null;
        }
        byte[] s = pack(str); //十六进制转byte数组
        String gbk;
        try {
            gbk = new String(s, "gbk"); //byte数组转中文字符串
        } catch ( UnsupportedEncodingException ignored ) {
            gbk = "/";
        }
        return gbk;
    }
    /**
     * 十六进制转byte数组，模拟php中pack
     */
    public static byte[] pack(String str) {
        int nibbleshift = 4;
        int position = 0;
        int len = str.length() / 2 + str.length() % 2;
        byte[] output = new byte[len];
        for (char v : str.toCharArray()) {
            byte n = (byte) v;
            if (n >= '0' && n <= '9') {
                n -= '0';
            } else if (n >= 'A' && n <= 'F') {
                n -= ('A' - 10);
            } else if (n >= 'a' && n <= 'f') {
                n -= ('a' - 10);
            } else {
                continue;
            }
            output[position] |= (n << nibbleshift);
            if (nibbleshift == 0) {
                position++;
            }
            nibbleshift = (nibbleshift + 4) & 7;
        }
        return output;
    }
    /**
     * 中文字符串转十六进制
     */
    public static String encrypt(String str) {
        if ( str == null ) {
            return null;
        }
        String gbk;
        try {
            byte[] sss = str.getBytes("GBK");  //中文字符串转byte数组
            gbk = unpack(sss); // byte数组转十六进制
        } catch ( Exception E ) {
            gbk = "/";
        }
        return gbk;
    }
    /**
     * byte数组转十六进制，模拟php中unpack
     */
    public static String unpack(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}