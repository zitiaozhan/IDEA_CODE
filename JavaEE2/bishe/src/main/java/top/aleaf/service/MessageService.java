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
package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.MessageMapper;
import top.aleaf.model.Message;
import top.aleaf.model.User;
import top.aleaf.utils.GeneralExample;

import java.util.ArrayList;
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
    @Autowired
    private UserService userService;

    public List<Message> getAll(Message message, int userId) {
        if (message.getPage() != null && message.getRows() != null) {
            PageHelper.startPage(message.getPage(), message.getRows());
        }
        return getConversationList(userId);
    }

    public List<Message> searchByName(String userName, Integer myId) {
        List<User> userList = this.userService.getAll(new User().setName(userName));
        int sum = this.userService.getUserCount();
        Message message = new Message();
        PageHelper.startPage(message.getPage(), message.getRows());

        if (userList.size() > 0 && userList.size() < sum) {
            List<Integer> ids = new ArrayList<>();
            for (User item : userList) {
                ids.add(item.getId());
            }
            return this.messageMapper.selectSearchByName(ids, myId);
        }
        return new ArrayList<>();
    }

    public Message getById(Integer id) {
        return messageMapper.selectByPrimaryKey(id);
    }

    public List<Message> getByConversationId(String conversationId) {
        return messageMapper.selectByExample(
                GeneralExample.getConditionAndOrderExample(
                        Message.class, "created_date ASC",
                        " 1=1 and conversation_id='" + conversationId + "' "));
    }

    public List<Message> getConversationList(int userId) {
        return this.messageMapper.selectConversationList(userId);
    }

    public int getUnreadCount(String conversationId, int toId) {
        return this.messageMapper.selectCount(new Message().setConversationId(conversationId).setHasRead(0).setToId(toId));
    }

    public int getAllUnreadCount(int toId) {
        return this.messageMapper.selectCount(new Message().setHasRead(0).setToId(toId));
    }

    public void clearUnreadCount(String conversationId, int toId) {
        this.messageMapper.updateHasRead(conversationId, 1, toId);
    }

    public boolean deleteById(Integer id) {
        Message message = new Message();
        message.setId(id);
        message.setStatus(1);
        return messageMapper.updateByPrimaryKeySelective(message) > 0;
    }

    public boolean delete(Message message) {
        message.setStatus(1);
        return messageMapper.updateByPrimaryKeySelective(message) > 0;
    }

    public boolean save(Message message) {
        if (message.getId() != null) {
            return messageMapper.updateByPrimaryKeySelective(message) > 0;
        } else {
            return messageMapper.insert(message) > 0;
        }
    }
}