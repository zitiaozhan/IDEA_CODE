package top.aleaf.sync.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.service.*;
import top.aleaf.sync.EventHandler;
import top.aleaf.sync.EventModel;
import top.aleaf.sync.EventType;
import top.aleaf.utils.ConstantUtil;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 〈删除用户时触发〉
 *
 * @author 郭新晔
 * @create 2019/5/9 0009
 * Author:   郭新晔
 */
@Component
public class DeleteUserHandler implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteUserHandler.class);
    @Resource
    private CompanyProjectService companyProjectService;
    @Resource
    private GovernmentProjectService governmentProjectService;
    @Resource
    private PrizeService prizeService;
    @Resource
    private TeachingMaterialService teachingMaterialService;
    @Resource
    private PatentService patentService;
    @Resource
    private SoftwareWorkService softwareWorkService;
    @Resource
    private LectureService lectureService;
    @Resource
    private AcademicExchangeService academicExchangeService;
    @Resource
    private PaperService paperService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void doHandle(EventModel eventModel) {
        int userId = eventModel.getEntityId();
        try {
            //更新此人发布的项目的状态
            //COMPANY_PROJECT
            this.companyProjectService.getByCreatedId(userId).forEach(item -> {
                item.setStatus(ConstantUtil.PROJECT_STATUS_USER_INVALID);
                companyProjectService.save(item);
            });
            //GOVERNMENT_PROJECT
            this.governmentProjectService.getByCreatedId(userId).forEach(item -> {
                item.setStatus(ConstantUtil.PROJECT_STATUS_USER_INVALID);
                governmentProjectService.save(item);
            });
            //PRIZE
            this.prizeService.getByCreatedId(userId).forEach(item -> {
                item.setStatus(ConstantUtil.PROJECT_STATUS_USER_INVALID);
                prizeService.save(item);
            });
            //TEACHING_MATERIAL
            this.teachingMaterialService.getByCreatedId(userId).forEach(item -> {
                item.setStatus(ConstantUtil.PROJECT_STATUS_USER_INVALID);
                teachingMaterialService.save(item);
            });
            //PATENT
            this.patentService.getByCreatedId(userId).forEach(item -> {
                item.setStatus(ConstantUtil.PROJECT_STATUS_USER_INVALID);
                patentService.save(item);
            });
            //SOFTWARE_WORK
            this.softwareWorkService.getByCreatedId(userId).forEach(item -> {
                item.setStatus(ConstantUtil.PROJECT_STATUS_USER_INVALID);
                softwareWorkService.save(item);
            });
            //LECTURE
            this.lectureService.getByCreatedId(userId).forEach(item -> {
                item.setStatus(ConstantUtil.PROJECT_STATUS_USER_INVALID);
                lectureService.save(item);
            });
            //ACADEMIC_EXCHANGE
            this.academicExchangeService.getByCreatedId(userId).forEach(item -> {
                item.setStatus(ConstantUtil.PROJECT_STATUS_USER_INVALID);
                academicExchangeService.save(item);
            });
            //PAPER
            this.paperService.getByCreatedId(userId).forEach(item -> {
                item.setStatus(ConstantUtil.PROJECT_STATUS_USER_INVALID);
                paperService.save(item);
            });
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("注销用户更新项目状态失败");
        }
    }

    @Override
    public List<EventType> getSupportTypes() {
        return Collections.singletonList(EventType.LOGOUT_USER);
    }
}