package top.aleaf.sync.score;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.model.GovernmentProject;
import top.aleaf.model.ProjectScoreParseVO;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.service.GovernmentProjectService;
import top.aleaf.service.ScoreService;
import top.aleaf.service.UserService;
import top.aleaf.utils.ConstantUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/4/6 0006
 */
@Component
public class GovernmentProjectScoreComputer implements ScoreComputer {
    @Resource
    private GovernmentProjectService governmentProjectService;
    @Resource
    private UserService userService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private BaseScoreComputer baseScoreComputer;

    private static final String JIE_TI = "已结题";
    private static final String PROJECT_TYPE_COUNTRY = "国家级";
    private static final String PROJECT_TYPE_PROVINCE = "省部级";
    private static final String PROJECT_TYPE_CITY = "厅局级";
    private static final String PROJECT_TYPE_SCHOOL = "校级";
    private static final int YI_WAN = 10000;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean computeProjectScore(int entityId) {
        GovernmentProject governmentProject = this.governmentProjectService.getByPrimaryKey(entityId);
        if (governmentProject == null || !JIE_TI.equals(governmentProject.getProjectStatus())) {
            return false;
        }
        ProjectScoreParseVO projectScoreParseVO = new ProjectScoreParseVO();
        double projectScore = 0.0;

        // 装填基础数据
        projectScoreParseVO.setEntityId(governmentProject.getId());
        projectScoreParseVO.setEntityType(EntityType.GOVERNMENT_PROJECT.getValue());
        projectScoreParseVO.setParseDate(new Date());
        projectScoreParseVO.setName(governmentProject.getName());

        //作者编号列表
        List<String> authorList = this.getAuthorNumberList(entityId);
        projectScoreParseVO.setAuthorList(authorList);

        // 国家级项目每个20分；省部级每个16分；地市级每个12分；校级每个3分
        // 国家级项目经费每万元计2分;省、部级项目每万元计1.6分;地、市级项目每万元计1.4分
        int arrivalMoney = governmentProject.getArrivalMoney();
        switch (governmentProject.getProjectType()) {
            case PROJECT_TYPE_COUNTRY:
                projectScore += 20;
                /*if (arrivalMoney > YI_WAN) {
                    projectScore += 2 * arrivalMoney / YI_WAN;
                }*/
                break;
            case PROJECT_TYPE_PROVINCE:
                projectScore += 16;
                /*if (arrivalMoney > YI_WAN) {
                    projectScore += 1.6 * arrivalMoney / YI_WAN;
                }*/
                break;
            case PROJECT_TYPE_CITY:
                projectScore += 12;
                /*if (arrivalMoney > YI_WAN) {
                    projectScore += 1.4 * arrivalMoney / YI_WAN;
                }*/
                break;
            case PROJECT_TYPE_SCHOOL:
                projectScore += 3;
                break;
            default:
                projectScore += 0;
        }
        projectScoreParseVO.setProjectScore(projectScore);

        // 计算每个人科技分
        Map<String, Double> authorScoreMap = baseScoreComputer.computeEveryAuthorScore(authorList, projectScore, false);
        projectScoreParseVO.setAuthorScoreMap(authorScoreMap);
        // 保存信息
        governmentProject.setResultScore(JSON.toJSONString(projectScoreParseVO));
        if (!this.governmentProjectService.save(governmentProject)) {
            return false;
        }

        // 对每位用户的score进行更新
        baseScoreComputer.updateUserScore(authorList, authorScoreMap, scoreService, userService, projectScoreParseVO, false);

        return true;
    }

    /**
     * 获得项目的相关人员编号
     *
     * @param entityId 项目ＩＤ
     * @return 项目的相关人员编号
     */
    @Override
    public List<String> getAuthorNumberList(int entityId) {
        GovernmentProject governmentProject = this.governmentProjectService.getByPrimaryKey(entityId);
        if (governmentProject == null) {
            return Collections.emptyList();
        }
        List<String> authorList = Lists.newArrayList();
        authorList.add(governmentProject.getChargePerson());
        String more = governmentProject.getParticipant().trim();
        if (!Strings.isNullOrEmpty(more)) {
            authorList.addAll(Arrays.asList(more.split(ConstantUtil.COMMA)));
        }
        return authorList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    @Override
    public boolean clearAuthorScore(int entityId) {
        return baseScoreComputer.clearAuthorScore(EntityType.GOVERNMENT_PROJECT.getValue(), entityId, getAuthorNumberList(entityId));
    }

    @Override
    public boolean restore(int entityId) {
        return governmentProjectService.save(this.governmentProjectService.getByPrimaryKey(entityId).setResultScore(null));
    }
}