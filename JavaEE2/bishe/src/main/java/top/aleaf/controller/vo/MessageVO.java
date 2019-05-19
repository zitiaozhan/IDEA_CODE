package top.aleaf.controller.vo;

import lombok.Data;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/5/5 0005
 * Author:   郭新晔
 */
@Data
public class MessageVO {
    private Integer id;
    private Integer fromId;
    private Integer toId;
    private String conversationId;
    private Integer hasRead;
    private String content;
    private String createdDate;
    private Integer status = 0;
}