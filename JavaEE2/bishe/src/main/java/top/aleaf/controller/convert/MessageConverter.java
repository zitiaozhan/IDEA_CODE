package top.aleaf.controller.convert;

import com.google.common.collect.Lists;
import top.aleaf.controller.vo.MessageVO;
import top.aleaf.model.Message;
import top.aleaf.utils.DateUtils;

import java.util.List;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/5/5 0005
 * Author:   郭新晔
 */
public class MessageConverter {
    public static MessageVO convertMessageModel(Message message) {
        MessageVO messageVO = new MessageVO();
        messageVO.setContent(message.getContent());
        messageVO.setConversationId(message.getConversationId());
        messageVO.setCreatedDate(DateUtils.dateDesc(message.getCreatedDate()));
        messageVO.setFromId(message.getFromId());
        messageVO.setHasRead(message.getHasRead());
        messageVO.setStatus(message.getStatus());
        messageVO.setToId(message.getToId());
        messageVO.setId(message.getId());

        return messageVO;
    }

    public static List<MessageVO> convertMessageModelList(List<Message> messageList) {
        List<MessageVO> messageVOList = Lists.newArrayList();

        messageList.forEach(item -> {
            messageVOList.add(convertMessageModel(item));
        });

        return messageVOList;
    }
}