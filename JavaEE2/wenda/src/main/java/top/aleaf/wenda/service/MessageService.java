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
import top.aleaf.wenda.mapper.MessageMapper;
import top.aleaf.wenda.model.Message;
import top.aleaf.wenda.util.GeneralExample;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/1/18 0018
 */
@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public List<Message> getAll(Message message) {
        if (message.getPage() != null && message.getRows() != null) {
            //PageHelper.startPage(question.getPage(), question.getRows());
        }
        if (message.getConversationId() != null && message.getConversationId().length() > 0) {
            return messageMapper.selectByExample(
                    GeneralExample.getBaseAndConditionExample(Message.class, "and conversation_id='" + message.getConversationId()+"'", true));
        }
        return messageMapper.selectByExample(GeneralExample.getBaseExample(Message.class));
    }

    public Message getById(Integer id) {
        return messageMapper.selectByPrimaryKey(id);
    }

    public List<Message> getConversationList(int userId) {
        return this.messageMapper.selectConversationList(userId);
    }

    public int getUnreadCount(String conversationId,int toId) {
        return this.messageMapper.selectCount(new Message().setConversationId(conversationId).setHasRead(0).setToId(toId));
    }

    public void clearUnreadCount(String conversationId,int toId) {
        this.messageMapper.updateHasRead(conversationId, 1,toId);
    }

    public void deleteById(Integer id) {
        messageMapper.deleteByPrimaryKey(id);
    }

    public void save(Message message) {
        if (message.getId() != null) {
            messageMapper.updateByPrimaryKey(message);
        } else {
            messageMapper.insert(message);
        }
    }
}