/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ApproveHandler
 * Author:   郭新晔
 * Date:     2019/2/22 0022 21:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.sync.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aleaf.model.Approval;
import top.aleaf.model.Message;
import top.aleaf.model.User;
import top.aleaf.service.ApprovalService;
import top.aleaf.service.MessageService;
import top.aleaf.service.UserService;
import top.aleaf.sync.EventHandler;
import top.aleaf.sync.EventModel;
import top.aleaf.sync.EventType;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/22 0022
 */
@Component
public class ApproveHandler implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApproveHandler.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private MessageService messageService;

    @Override
    public void doHandle(EventModel eventModel) {
        //插入到审批表中
        //发送已被审批的消息
        //做科技分统计
        try {
            User approveUser = this.userService.getByPrimaryKey(eventModel.getActorId());
            int status = Integer.parseInt(eventModel.getValue("resultStatus"));
            if (approveUser == null) {
                LOGGER.error("审批处理器参数异常");
                throw new IllegalArgumentException("审批处理器参数异常");
            }
            //插入到审批表中
            Approval approval = new Approval();
            approval.setEntityId(eventModel.getEntityId());
            approval.setEntityType(eventModel.getEntityType().getId());
            approval.setUserId(eventModel.getActorId());
            approval.setDate(new Date());
            approval.setResult(status);
            if (!approvalService.save(approval)){
                LOGGER.error("审批记录添加失败");
                throw new Exception("审批记录添加失败");
            }

            //发送已被审批的消息
            Message message = new Message();
            message.setHasRead(0);
            message.setFromId(eventModel.getActorId());
            message.setCreatedDate(new Date());
            message.setToId(eventModel.getEventOwnerId());
            String conversationId = eventModel.getActorId() < eventModel.getEventOwnerId() ?
                    eventModel.getActorId() + "_" + eventModel.getEventOwnerId() :
                    eventModel.getEventOwnerId() + "_" + eventModel.getActorId();
            message.setConversationId(conversationId);
            String entityName = eventModel.getEntityType().getEntityName();
            String projectName = eventModel.getValue("projectName");
            String projectUrl = eventModel.getValue("projectUrl");
            String content = "您发布的[" + entityName + "]项目【" + projectName + "】由管理员<"+approveUser.getName()+">审批完成，" +
                    "审批结果出现未知错误," +
                    "<a href='" + projectUrl + "&status="+status+"' target='_blank'>查看详情</a>";
            if (status == 1) {
                content = "您发布的[" + entityName + "]项目【" + projectName + "】由管理员<"+approveUser.getName()+">审批完成，" +
                        "已审批通过," +
                        "<a href='" + projectUrl + "&status="+status+"' target='_blank'>查看详情</a>";
            }else if (status==2){
                content = "您发布的[" + entityName + "]项目【" + projectName + "】由管理员<"+approveUser.getName()+">审批完成，" +
                        "已审批失败," +
                        "<a href='" + projectUrl + "&status="+status+"' target='_blank'>查看详情</a>";
            }
            message.setContent(content);
            if (!messageService.save(message)){
                LOGGER.error("提示消息添加失败");
                throw new Exception("提示消息添加失败");
            }

            //做科技分统计

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("审批处理器发生错误");
        }
    }

    @Override
    public List<EventType> getSupportTypes() {
        return Arrays.asList(EventType.APPROVE);
    }
}