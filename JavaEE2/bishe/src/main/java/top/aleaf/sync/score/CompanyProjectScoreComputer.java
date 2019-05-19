package top.aleaf.sync.score;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.model.CompanyProject;
import top.aleaf.model.ProjectScoreParseVO;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.service.CompanyProjectService;
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
public class CompanyProjectScoreComputer implements ScoreComputer {
    @Resource
    private CompanyProjectService companyProjectService;
    @Resource
    private UserService userService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private BaseScoreComputer baseScoreComputer;

    private static final int WU_QIAN = 5000;
    private static final int YI_WAN = 10000;
    private static final String JIE_TI = "已结题";

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean computeProjectScore(int entityId) {
        ProjectScoreParseVO projectScoreParseVO = new ProjectScoreParseVO();
        double sumScore = 0;
        CompanyProject companyProject = companyProjectService.getByPrimaryKey(entityId);
        if (companyProject == null || !JIE_TI.equals(companyProject.getProjectStatus())) {
            return false;
        }

        // 装填基础数据
        projectScoreParseVO.setEntityId(companyProject.getId());
        projectScoreParseVO.setEntityType(EntityType.COMPANY_PROJECT.getValue());
        projectScoreParseVO.setName(companyProject.getName());
        projectScoreParseVO.setParseDate(new Date());

        //作者编号列表
        List<String> authorList = this.getAuthorNumberList(entityId);
        projectScoreParseVO.setAuthorList(authorList);
        // 横向项目每项8分，合同额低于5000不计分
        double contractAmount = companyProject.getContractMoney();
        if (contractAmount > WU_QIAN) {
            sumScore += 8;
        }
        // 横向项目经费计算科技分，每万元计一分
        /*double arrivalMoney = companyProject.getArrivalMoney();
        if (arrivalMoney > YI_WAN) {
            sumScore += arrivalMoney / YI_WAN;
        }*/
        projectScoreParseVO.setProjectScore(sumScore);

        // 获得每个作者的科技分
        Map<String, Double> authorScoreMap = baseScoreComputer.computeEveryAuthorScore(authorList, sumScore, false);
        projectScoreParseVO.setAuthorScoreMap(authorScoreMap);
        // 保存信息
        companyProject.setResultScore(JSON.toJSONString(projectScoreParseVO));
        if (!this.companyProjectService.save(companyProject)) {
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
        CompanyProject companyProject = companyProjectService.getByPrimaryKey(entityId);
        if (companyProject == null) {
            return Collections.emptyList();
        }
        List<String> authorList = Lists.newArrayList();

        authorList.add(companyProject.getChargePerson());
        String more = companyProject.getParticipant().trim();
        if (!com.google.common.base.Strings.isNullOrEmpty(more)) {
            authorList.addAll(Arrays.asList(more.split(ConstantUtil.COMMA)));
        }
        return authorList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    @Override
    public boolean clearAuthorScore(int entityId) {
        return baseScoreComputer.clearAuthorScore(EntityType.COMPANY_PROJECT.getValue(), entityId, getAuthorNumberList(entityId));
    }

    @Override
    public boolean restore(int entityId) {
        return companyProjectService.save(this.companyProjectService.getByPrimaryKey(entityId).setResultScore(null));
    }
}