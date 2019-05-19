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
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.mapper.MessageMapper;
import top.aleaf.model.Message;
import top.aleaf.model.User;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.GeneralExample;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/1/18 0018
 */
@Service
public class MessageService {
    @Resource
    private MessageMapper messageMapper;
    @Resource
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

    public List<Message> getByToId(int toId) {
        return messageMapper.selectByExample(
                GeneralExample.getConditionAndOrderExample(
                        Message.class, "created_date DESC",
                        " 1=1 and to_id=" + toId));
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

    /**
     * 批量插入消息
     *
     * @param messageMap    接受者用户ID：消息内容
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendMessageList(Map<Integer, String> messageMap) {
        if (null == messageMap || messageMap.size() < 1) {
            return;
        }
        List<Message> messageList = Lists.newArrayList();
        for (int item : messageMap.keySet()) {
            Message message = new Message();
            message.setHasRead(0);
            message.setCreatedDate(new Date());
            message.setToId(item);
            message.setFromId(ConstantUtil.MESSAGE_SYSTEM);
            message.setConversationId(ConstantUtil.MESSAGE_SYSTEM > item ? ConstantUtil.MESSAGE_SYSTEM + "_" + item : item + "_" + ConstantUtil.MESSAGE_SYSTEM);
            message.setContent(messageMap.get(item));
            messageList.add(message);
        }
        this.messageMapper.insertList(messageList);
    }

    /**
     * 数量
     * @param status
     * @return
     */
    public int getAllCount(int status) {
        if (-1 == status) {
            return this.messageMapper.selectCount(new Message().setStatus(null));
        }
        return this.messageMapper.selectCount(new Message().setStatus(status));
    }
}