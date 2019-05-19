package top.aleaf.sync.score;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.model.AcademicExchange;
import top.aleaf.model.ProjectScoreParseVO;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.service.AcademicExchangeService;
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
public class AcademicExchangeScoreComputer implements ScoreComputer {
    @Resource
    private AcademicExchangeService academicExchangeService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private UserService userService;
    @Resource
    private BaseScoreComputer baseScoreComputer;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean computeProjectScore(int entityId) {
        AcademicExchange academicExchange = this.academicExchangeService.getByPrimaryKey(entityId);
        if (null == academicExchange) {
            return false;
        }
        ProjectScoreParseVO projectScoreParseVO = new ProjectScoreParseVO();
        projectScoreParseVO.setEntityId(entityId);
        projectScoreParseVO.setEntityType(EntityType.ACADEMIC_EXCHANGE.getValue());
        projectScoreParseVO.setParseDate(new Date());
        projectScoreParseVO.setName(academicExchange.getMeetingName());
        projectScoreParseVO.setProjectScore(0.0);
        //作者编号列表
        List<String> authorList = this.getAuthorNumberList(entityId);
        projectScoreParseVO.setAuthorList(authorList);

        //每个人
        Map<String, Double> authorScoreMap = baseScoreComputer.computeEveryAuthorScore(authorList, 0.0, false);
        projectScoreParseVO.setAuthorScoreMap(authorScoreMap);
        academicExchange.setResultScore(JSON.toJSONString(authorScoreMap));
        boolean save = academicExchangeService.save(academicExchange);
        if (!save) {
            return false;
        }

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
        AcademicExchange academicExchange = this.academicExchangeService.getByPrimaryKey(entityId);
        if (null == academicExchange) {
            return Collections.emptyList();
        }
        List<String> authorList = Lists.newArrayList();
        String more = academicExchange.getMainParticipant().trim();
        if (!Strings.isNullOrEmpty(more)) {
            authorList.addAll(Arrays.asList(more.split(ConstantUtil.COMMA)));
        }
        return authorList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    @Override
    public boolean clearAuthorScore(int entityId) {
        return baseScoreComputer.clearAuthorScore(EntityType.ACADEMIC_EXCHANGE.getValue(), entityId, getAuthorNumberList(entityId));
    }

    @Override
    public boolean restore(int entityId) {
        return academicExchangeService.save(this.academicExchangeService.getByPrimaryKey(entityId).setResultScore(null));
    }
}