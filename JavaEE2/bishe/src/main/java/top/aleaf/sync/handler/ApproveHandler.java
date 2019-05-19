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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.model.Approval;
import top.aleaf.model.Message;
import top.aleaf.model.User;
import top.aleaf.service.ApprovalService;
import top.aleaf.service.MessageService;
import top.aleaf.service.UserService;
import top.aleaf.sync.EventHandler;
import top.aleaf.sync.EventModel;
import top.aleaf.sync.EventType;
import top.aleaf.sync.score.*;
import top.aleaf.utils.ConstantUtil;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/2/22 0022
 */
@Component
public class ApproveHandler implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApproveHandler.class);
    @Resource
    private UserService userService;
    @Resource
    private ApprovalService approvalService;
    @Resource
    private MessageService messageService;

    @Resource
    private AcademicExchangeScoreComputer academicExchangeScoreComputer;
    @Resource
    private CompanyProjectScoreComputer companyProjectScoreComputer;
    @Resource
    private GovernmentProjectScoreComputer governmentProjectScoreComputer;
    @Resource
    private LectureScoreComputer lectureScoreComputer;
    @Resource
    private PaperScoreComputer paperScoreComputer;
    @Resource
    private PatentScoreComputer patentScoreComputer;
    @Resource
    private PrizeScoreComputer prizeScoreComputer;
    @Resource
    private SoftwareWorkScoreComputer softwareWorkScoreComputer;
    @Resource
    private TeachingMaterialScoreComputer teachingMaterialScoreComputer;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void doHandle(EventModel eventModel) {
        try {
            User approveUser = this.userService.getByPrimaryKey(eventModel.getActorId());
            int status = Integer.parseInt(eventModel.getValue("resultStatus"));
            int entityId = eventModel.getEntityId();

            //发送消息相关信息
            String entityName = eventModel.getEntityType().getDesc();
            String projectName = eventModel.getValue("projectName");
            String projectUrl = eventModel.getValue("projectUrl");
            //对于恢复已删除的项目的处理
            if (status == 0) {
                //生成一个系统通知
                String messageContent = "您的[" + entityName + "]项目【" + projectName + "】从已删除状态恢复, " +
                        "<a href='" + projectUrl + "&status=" + status + "' target='_blank'>查看详情</a>";
                Map<Integer, String> idMessageContent = Maps.newHashMap();
                idMessageContent.put(eventModel.getEventOwnerId(), messageContent);
                this.messageService.sendMessageList(idMessageContent);
                return;
            }

            String option = eventModel.getValue("option");
            if (approveUser == null) {
                LOGGER.error("审批处理器参数异常");
                throw new IllegalArgumentException("审批处理器参数异常");
            }

            //插入到审批表中
            Approval approval = new Approval();
            approval.setEntityId(eventModel.getEntityId());
            approval.setEntityType(eventModel.getEntityType().getValue());
            approval.setUserId(eventModel.getActorId());
            approval.setDate(new Date());
            approval.setResult(status);
            approval.setRemark(option);
            if (!approvalService.save(approval)) {
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
            String content = "您发布的[" + entityName + "]项目【" + projectName + "】由管理员<" + approveUser.getName() + ">审批完成，" +
                    "审批结果出现未知错误," +
                    "<a href='" + projectUrl + "&status=" + status + "' target='_blank'>查看详情</a>";
            if (status == ConstantUtil.PROJECT_STATUS_SUCCESS) {
                content = "您发布的[" + entityName + "]项目【" + projectName + "】由管理员<" + approveUser.getName() + ">审批完成，" +
                        "已审批通过,审批意见<" + option + ">, " +
                        "<a href='" + projectUrl + "&status=" + status + "' target='_blank'>查看详情</a>";
            } else if (status == ConstantUtil.PROJECT_STATUS_REFUSE) {
                content = "您发布的[" + entityName + "]项目【" + projectName + "】由管理员<" + approveUser.getName() + ">审批完成，" +
                        "拒绝通过,审批意见<" + option + ">, " +
                        "<a href='" + projectUrl + "&status=" + status + "' target='_blank'>查看详情</a>";
            }
            message.setContent(content);
            if (!messageService.save(message)) {
                LOGGER.error("提示消息添加失败");
                throw new Exception("提示消息添加失败");
            }

            //审批通过
            if (status == 1) {
                //做科技分统计
                List<String> authorList = Lists.newArrayList();
                switch (eventModel.getEntityType()) {
                    case COMPANY_PROJECT:
                        this.companyProjectScoreComputer.computeProjectScore(entityId);
                        authorList = this.companyProjectScoreComputer.getAuthorNumberList(entityId);
                        break;
                    case GOVERNMENT_PROJECT:
                        this.governmentProjectScoreComputer.computeProjectScore(entityId);
                        authorList = this.governmentProjectScoreComputer.getAuthorNumberList(entityId);
                        break;
                    case PRIZE:
                        this.prizeScoreComputer.computeProjectScore(entityId);
                        authorList = prizeScoreComputer.getAuthorNumberList(entityId);
                        break;
                    case TEACHING_MATERIAL:
                        this.teachingMaterialScoreComputer.computeProjectScore(entityId);
                        authorList = teachingMaterialScoreComputer.getAuthorNumberList(entityId);
                        break;
                    case PATENT:
                        this.patentScoreComputer.computeProjectScore(entityId);
                        authorList = patentScoreComputer.getAuthorNumberList(entityId);
                        break;
                    case SOFTWARE_WORK:
                        this.softwareWorkScoreComputer.computeProjectScore(entityId);
                        authorList = softwareWorkScoreComputer.getAuthorNumberList(entityId);
                        break;
                    case LECTURE:
                        this.lectureScoreComputer.computeProjectScore(entityId);
                        authorList = lectureScoreComputer.getAuthorNumberList(entityId);
                        break;
                    case ACADEMIC_EXCHANGE:
                        this.academicExchangeScoreComputer.computeProjectScore(entityId);
                        authorList = academicExchangeScoreComputer.getAuthorNumberList(entityId);
                        break;
                    case PAPER:
                        this.paperScoreComputer.computeProjectScore(entityId);
                        authorList = paperScoreComputer.getAuthorNumberList(entityId);
                        break;
                    default:
                }
                //消息群发
                String messageContent = "与您有关的[" + entityName + "]项目【" + projectName + "】由管理员<" + approveUser.getName() + ">审批完成，" +
                        "完成审批,审批意见<" + option + ">, " +
                        "<a href='" + projectUrl + "&status=" + status + "&part=true' target='_blank'>查看详情</a>";
                List<Integer> ids = this.userService.getUserByNumberList(authorList).stream().map(User::getId).collect(Collectors.toList());
                Map<Integer, String> idMessageContent = Maps.newHashMap();
                ids.forEach(id -> {
                    idMessageContent.put(id, messageContent);
                });
                this.messageService.sendMessageList(idMessageContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("审批处理器发生错误");
        }
    }

    @Override
    public List<EventType> getSupportTypes() {
        return Collections.singletonList(EventType.APPROVE);
    }
}