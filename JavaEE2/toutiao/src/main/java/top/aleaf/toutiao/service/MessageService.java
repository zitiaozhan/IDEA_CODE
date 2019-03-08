/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MessageService
 * Author:   郭新晔
 * Date:     2018/12/11 0011 21:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.toutiao.dao.MessageDAO;
import top.aleaf.toutiao.model.Message;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2018/12/11 0011
 */
@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    public boolean addMessage(Message message) {
        return this.messageDAO.addMessage(message) > 0;
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return this.messageDAO.selectConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return this.messageDAO.selectConversationList(userId, offset, limit);
    }

    public int getUnreadByConversationId(int userId, String conversationId) {
        return this.messageDAO.selectUnreadByConversationId(userId, conversationId);
    }

    public void setHasRead(int userId, String conversationId) {
        this.messageDAO.updateHasRead(userId, conversationId);
    }

    public void deleteMessage(int messageId) {
        this.messageDAO.updateStatus(messageId, 1);
    }
}