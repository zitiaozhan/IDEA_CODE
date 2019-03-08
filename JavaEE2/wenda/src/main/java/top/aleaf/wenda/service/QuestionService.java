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
import top.aleaf.wenda.mapper.QuestionMapper;
import top.aleaf.wenda.model.Question;
import top.aleaf.wenda.util.GeneralExample;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/1/18 0018
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    public List<Question> getAll(Question question) {
        if (question.getPage() != null && question.getRows() != null) {
            //PageHelper.startPage(question.getPage(), question.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (question.getTitle() != null && question.getTitle().length() > 0) {
            builder.append("and title like %").append(question.getTitle()).append("%");
        }
        if (question.getContent() != null && question.getContent().length() > 0) {
            builder.append("and content like %").append(question.getContent()).append("%");
        }
        if (question.getUserId() != null && question.getUserId() > 0) {
            builder.append("and user_id=").append(question.getUserId());
        }
        return questionMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                Question.class, builder.toString(), true
        ));
    }

    public Question getById(Integer id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        questionMapper.deleteByPrimaryKey(id);
    }

    public void save(Question question) {
        if (question.getId() != null) {
            questionMapper.updateByPrimaryKey(question);
        } else {
            questionMapper.insert(question);
        }
    }

    public void setCommentCount(int id, int commentCount) {
        this.questionMapper.updateCommentCount(id, commentCount);
    }
}