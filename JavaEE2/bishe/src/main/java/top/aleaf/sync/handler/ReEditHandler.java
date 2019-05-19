package top.aleaf.sync.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import top.aleaf.model.User;
import top.aleaf.service.MessageService;
import top.aleaf.service.UserService;
import top.aleaf.sync.EventHandler;
import top.aleaf.sync.EventModel;
import top.aleaf.sync.EventType;
import top.aleaf.sync.score.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 〈项目被重新编辑时处理〉
 *
 * @create 2019/5/3 0003
 * Author:   郭新晔
 */
@Component
public class ReEditHandler implements EventHandler {
    @Resource
    private UserService userService;
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

    @Override
    public void doHandle(EventModel eventModel) {
        //只有被审核完成的项目被重新编辑时才会触发
        int entityId = eventModel.getEntityId();
        //作者列表
        List<String> authorList = Lists.newArrayList();
        //先删除原来的科技分，再重新计算科技分
        switch (eventModel.getEntityType()) {
            case COMPANY_PROJECT:
                authorList = this.companyProjectScoreComputer.getAuthorNumberList(entityId);
                companyProjectScoreComputer.clearAuthorScore(entityId);
                this.companyProjectScoreComputer.computeProjectScore(entityId);
                break;
            case GOVERNMENT_PROJECT:
                authorList = this.governmentProjectScoreComputer.getAuthorNumberList(entityId);
                governmentProjectScoreComputer.clearAuthorScore(entityId);
                this.governmentProjectScoreComputer.computeProjectScore(entityId);
                break;
            case PRIZE:
                authorList = this.prizeScoreComputer.getAuthorNumberList(entityId);
                prizeScoreComputer.clearAuthorScore(entityId);
                this.prizeScoreComputer.computeProjectScore(entityId);
                break;
            case TEACHING_MATERIAL:
                authorList = this.teachingMaterialScoreComputer.getAuthorNumberList(entityId);
                teachingMaterialScoreComputer.clearAuthorScore(entityId);
                this.teachingMaterialScoreComputer.computeProjectScore(entityId);
                break;
            case PATENT:
                authorList = this.patentScoreComputer.getAuthorNumberList(entityId);
                patentScoreComputer.clearAuthorScore(entityId);
                this.patentScoreComputer.computeProjectScore(entityId);
                break;
            case SOFTWARE_WORK:
                authorList = this.softwareWorkScoreComputer.getAuthorNumberList(entityId);
                softwareWorkScoreComputer.clearAuthorScore(entityId);
                this.softwareWorkScoreComputer.computeProjectScore(entityId);
                break;
            case LECTURE:
                authorList = this.lectureScoreComputer.getAuthorNumberList(entityId);
                lectureScoreComputer.clearAuthorScore(entityId);
                this.lectureScoreComputer.computeProjectScore(entityId);
                break;
            case ACADEMIC_EXCHANGE:
                authorList = this.academicExchangeScoreComputer.getAuthorNumberList(entityId);
                academicExchangeScoreComputer.clearAuthorScore(entityId);
                this.academicExchangeScoreComputer.computeProjectScore(entityId);
                break;
            case PAPER:
                authorList = this.paperScoreComputer.getAuthorNumberList(entityId);
                paperScoreComputer.clearAuthorScore(entityId);
                this.paperScoreComputer.computeProjectScore(entityId);
                break;
            default:
        }
        //通知相关人员项目有变动,内容：与您有关的[]项目【】被用户<>重新编辑，查看详情
        User approveUser = this.userService.getByPrimaryKey(eventModel.getActorId());
        int status = Integer.parseInt(eventModel.getValue("resultStatus"));
        String entityName = eventModel.getEntityType().getDesc();
        String projectName = eventModel.getValue("projectName");
        String projectUrl = eventModel.getValue("projectUrl");
        String messageContent = "与您有关的[" + entityName + "]项目【" + projectName + "】被用户<" + approveUser.getName() + ">审批完成，" +
                "重新编辑," +
                "<a href='" + projectUrl + "&status=" + status + "' target='_blank'>查看详情</a>";
        List<Integer> ids = this.userService.getUserByNumberList(authorList).stream().map(User::getId).collect(Collectors.toList());
        Map<Integer, String> idMessageContent = Maps.newHashMap();
        ids.forEach(id -> {
            idMessageContent.put(id, messageContent);
        });
        this.messageService.sendMessageList(idMessageContent);
    }

    @Override
    public List<EventType> getSupportTypes() {
        return Collections.singletonList(EventType.REEDIT);
    }
}