/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: DetailService
 * Author:   郭新晔
 * Date:     2018/12/18 0018 15:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.aleaf.dao.DetailDAO;
import top.aleaf.model.Detail;
import top.aleaf.util.PhoneInfoUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 〈〉
 *
 * @create 2018/12/18 0018
 */
@Service
public class DetailService {
    @Autowired
    private DetailDAO detailDAO;

    public boolean addPhoneDetail(Detail detail) {
        return detail != null ? this.detailDAO.addPhoneDetail(detail) > 0 : false;
    }

    public void updateDetail(Detail detail) {
        if (detail != null) {
            this.detailDAO.updateDetail(detail);
        }
    }

    public Detail getDetailById(int id) {
        return this.detailDAO.selectDetailById(id);
    }

    public String saveImage(MultipartFile file) throws IOException {
        //找到文件名中.的索引位置
        int doPos = file.getOriginalFilename().lastIndexOf(".");
        //索引小于0表示不存在
        if (doPos < 0) {
            return null;
        }
        //得到文件的扩展名
        String fileExt = file.getOriginalFilename().substring(doPos + 1);
        //判断文件是否符合要求，包括格式与大小
        if (!PhoneInfoUtil.isImageAllowed(fileExt, file.getSize())) {
            return null;
        }
        //生成新的文件名，防止上传的文件名重复出现
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        //将文件存放至本地
        Files.copy(file.getInputStream(), new File(PhoneInfoUtil.UPLOAD_IMAGE_PATH + fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
        //返回查看图片的链接
        return PhoneInfoUtil.DOMAIN + "image?name=" + fileName;
    }
}