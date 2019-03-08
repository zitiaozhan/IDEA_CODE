/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: NewsService
 * Author:   郭新晔
 * Date:     2018/11/22 0022 23:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.aleaf.toutiao.dao.NewsDAO;
import top.aleaf.toutiao.model.News;
import top.aleaf.toutiao.model.User;
import top.aleaf.toutiao.utils.ToutiaoUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * 〈〉
 *
 * @create 2018/11/22 0022
 */
@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;

    public int addNews(News news) {
        return newsDAO.addNews(news);
    }

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }

    public News getNewsById(int newsId) {
        return newsDAO.selectNewsById(newsId);
    }

    public void setCommentCount(int id, int commentCount) {
        this.newsDAO.updateNewsCommentCount(id, commentCount);
    }
    public void setLikeCount(int id, int likeCount) {
        this.newsDAO.updateNewsLikeCount(id, likeCount);
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
        if (!ToutiaoUtil.isImageAllowed(fileExt, file.getSize())) {
            return null;
        }
        //生成新的文件名，防止上传的文件名重复出现
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        //将文件存放至本地
        Files.copy(file.getInputStream(), new File(ToutiaoUtil.UPLOAD_IMAGE_PATH + fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
        //返回查看图片的链接
        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;
    }

    public User getUserById(int newsId){
        return this.newsDAO.selectUserById(newsId);
    }
}