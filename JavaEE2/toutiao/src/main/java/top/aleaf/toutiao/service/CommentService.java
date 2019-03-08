/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CommentService
 * Author:   郭新晔
 * Date:     2018/12/10 0010 20:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.toutiao.dao.CommentDAO;
import top.aleaf.toutiao.model.Comment;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2018/12/10 0010
 */
@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    public boolean addComment(Comment comment) {
        int line = 0;
        line = this.commentDAO.addComment(comment);
        return line > 0;
    }

    public void deleteComment(int id) {
        this.commentDAO.updateStatus(id, 1);
    }

    public List<Comment> getCommentByEntity(int entityId, int entityType) {
        return this.commentDAO.selectByEntity(entityId, entityType);
    }

    public int getCommentCountByEntity(int entityId, int entityType) {
        return this.commentDAO.selectCountByEntity(entityId, entityType);
    }
}