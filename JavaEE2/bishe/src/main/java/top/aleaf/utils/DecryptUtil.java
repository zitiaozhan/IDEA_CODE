package top.aleaf.utils;

import java.io.UnsupportedEncodingException;

/**
 * DES加密 解密算法
 * @author 郭新晔
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
        if (str == null) {
            return null;
        }
        //十六进制转byte数组
        byte[] s = pack(str);
        String gbk;
        try {
            //byte数组转中文字符串
            gbk = new String(s, "gbk");
        } catch (UnsupportedEncodingException ignored) {
            gbk = "/";
        }
        return gbk;
    }

    /**
     * 十六进制转byte数组，模拟php中pack
     */
    private static byte[] pack(String str) {
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
        if (str == null) {
            return null;
        }
        String gbk;
        try {
            //中文字符串转byte数组
            byte[] sss = str.getBytes("GBK");
            //byte数组转十六进制
            gbk = unpack(sss);
        } catch (Exception e) {
            gbk = "/";
        }
        return gbk;
    }

    /**
     * byte数组转十六进制，模拟php中unpack
     */
    private static String unpack(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (byte aByte : bytes) {
            int v = aByte & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}