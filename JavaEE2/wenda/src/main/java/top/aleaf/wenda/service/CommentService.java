/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: QuestionService
 * Author:   郭新晔
 * Date:     2019/1/18 0018 16:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.wenda.mapper.CommentMapper;
import top.aleaf.wenda.model.Comment;
import top.aleaf.wenda.util.GeneralExample;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/1/18 0018
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> getAll(Comment comment) {
        if (comment.getPage() != null && comment.getRows() != null) {
            //PageHelper.startPage(question.getPage(), question.getRows());
        }
        if (comment.getEntityId() != null && comment.getEntityId() > 0) {
            return commentMapper.selectByExample(
                    GeneralExample.getBaseAndConditionExample(Comment.class, "and entity_id=" + comment.getEntityId(), true));
        }
        return commentMapper.selectByExample(GeneralExample.getBaseExample(Comment.class));
    }

    public int getCommentCount(Integer entityId, String entityType) {
        return commentMapper.selectCount(
                new Comment().setEntityId(entityId).setEntityType(entityType).setStatus(0));
    }

    public int getUserCommentCount(int userId) {
        return commentMapper.selectCount(
                new Comment().setUserId(userId));
    }

    public Comment getById(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    public void save(Comment comment) {
        if (comment.getId() != null) {
            commentMapper.updateByPrimaryKey(comment);
        } else {
            commentMapper.insert(comment);
        }
    }
}