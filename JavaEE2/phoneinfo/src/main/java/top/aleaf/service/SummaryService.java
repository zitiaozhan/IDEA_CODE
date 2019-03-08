/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: SummaryService
 * Author:   郭新晔
 * Date:     2018/12/18 0018 15:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.aleaf.dao.SummaryDAO;
import top.aleaf.model.Summary;
import top.aleaf.util.PhoneInfoUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * 〈〉
 *
 * @create 2018/12/18 0018
 */
@Service
public class SummaryService {
    @Autowired
    private SummaryDAO summaryDAO;

    public boolean addPhone(Summary summary) {
        if (summary != null && this.summaryDAO.addPhone(summary) > 0) {
            return true;
        }
        return false;
    }

    public void deletePhone(int id) {
        this.summaryDAO.updateStatus(1, id);
    }

    public void updatePhone(Summary summary) {
        this.summaryDAO.updatePhone(summary);
    }

    public void setDetailId(int detailid, int id) {
        this.summaryDAO.updateDetailId(detailid, id);
    }

    public List<Summary> selectByKeyword(String keyword, int offset, int limit) {
        if (keyword != null && !"".equals(keyword)) {
            return this.summaryDAO.selectByKeyword(keyword, offset, limit);
        }
        return null;
    }

    public Summary getPhoneById(int id) {
        return this.summaryDAO.selectPhoneById(id);
    }

    public List<Summary> selectPhoneByOffsetAndLimit(int offset, int limit) {
        return offset >= 0 ? this.summaryDAO.selectPhoneByOffsetAndLimit(offset, limit) : null;
    }

    public int getPhoneCount() {
        return this.summaryDAO.selectPhoneCount();
    }

    public int getPhoneByKeywordCount(String keyword) {
        return this.summaryDAO.selectByKeywordCount(keyword);
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