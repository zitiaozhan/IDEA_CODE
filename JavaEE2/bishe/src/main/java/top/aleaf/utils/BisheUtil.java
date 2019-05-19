package top.aleaf.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/2/11 0011
 */
public class BisheUtil {
    public static final Logger logger = LoggerFactory.getLogger(BisheUtil.class);

    /**
     * 保留指定位小数
     *
     * @param num     小数
     * @param pattern 模式
     * @return 结果
     */
    public static String doubleFormat(double num, String pattern) {
        return pattern == null ? null : new DecimalFormat(pattern).format(num);
    }

    public static String getJSONString(int code) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }

    public static String getJSONString(int code, List<?> list) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        for (Object item : list) {
            json.put("properties", item.toString());
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }

    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("生成MD5失败", e);
            return null;
        }
    }

    public static String parseLoginTicket(HttpServletRequest request) {
        String ticket = null;
        if (request.getCookies() != null) {
            for (Cookie it : request.getCookies()) {
                if (it.getName().equals(ConstantUtil.TOKEN)) {
                    ticket = it.getValue();
                    break;
                }
            }
        }
        return ticket;
    }

    public static boolean judgeURL(String url) {
        url = url.trim();
        return url.matches("(https|http|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
    }
}