/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: QiniuService
 * Author:   郭新晔
 * Date:     2018/12/5 0005 17:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.service;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.aleaf.toutiao.utils.ToutiaoUtil;

import java.io.IOException;
import java.util.UUID;

/**
 * 〈〉
 *
 * @create 2018/12/5 0005
 */
@Service
public class QiniuService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QiniuService.class);
    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "otr_dqWVxMsWfeCEmnEhro9VoVh9hgF5dT9A5xVB";
    String SECRET_KEY = "6y4E33nqelLLdX3usj8J83wM679DIBm6o5Y5gtKF";
    //要上传的空间
    String bucketname = "aleaf";

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();

    private static String QINIU_IMAGE_DOMAIN = "http://pjaviyv9u.bkt.clouddn.com/";

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public String saveImage(MultipartFile file) throws IOException {
        //文件名中有没有.
        int doPos = file.getOriginalFilename().lastIndexOf(".");
        if (doPos < 0) {
            return null;
        }
        //获取文件的扩展名
        String fileExt = file.getOriginalFilename().substring(doPos + 1);
        if (!ToutiaoUtil.isImageAllowed(fileExt, file.getSize())) {
            return null;
        }
        //生成新的文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        try {
            //调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());
            //打印返回的信息
            if (res.isOK() && res.isJson()) {
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key").toString();
            } else {
                LOGGER.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            LOGGER.error("七牛异常：" + e.getMessage());
            return null;
        }
    }
}