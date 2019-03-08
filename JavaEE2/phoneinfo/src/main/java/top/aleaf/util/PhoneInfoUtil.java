/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PhoneInfoUtil
 * Author:   郭新晔
 * Date:     2018/12/18 0018 16:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 〈〉
 * @create 2018/12/18 0018
 */
public class PhoneInfoUtil {
    public static final Logger logger = LoggerFactory.getLogger(PhoneInfoUtil.class);
    public static final String[] ALLOWED_IMAGE_EXT = new String[]{"jpg", "png", "jpeg", "bmp"};
    public static final String UPLOAD_IMAGE_PATH = "P:/haha/uploadImages/";
    public static final String DOMAIN="http://127.0.0.1:8080/";

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

    public static boolean isImageAllowed(String ext, long imageSize) {
        logger.warn("ImageSize:图片大小：" + imageSize);
        if (imageSize >= 1024 * 1024) {
            return false;
        }
        ext = ext.toLowerCase();
        for (String item : ALLOWED_IMAGE_EXT) {
            if (item.equals(ext)) {
                return true;
            }
        }
        return false;
    }
}