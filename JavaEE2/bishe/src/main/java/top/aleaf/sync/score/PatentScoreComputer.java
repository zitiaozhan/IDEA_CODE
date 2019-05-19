package top.aleaf.sync.score;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.model.Patent;
import top.aleaf.model.ProjectScoreParseVO;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.service.PatentService;
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
public class PatentScoreComputer implements ScoreComputer {
    @Resource
    private PatentService patentService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private UserService userService;
    @Resource
    private BaseScoreComputer baseScoreComputer;

    private static final String PATENT = "发明专利";
    private static final String UTILITY_NEW_PATENT = "实用新型专利";
    private static final String DESIGN_PATENT = "外观设计专利";

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean computeProjectScore(int entityId) {
        Patent patent = this.patentService.getByPrimaryKey(entityId);
        if (patent == null) {
            return false;
        }
        ProjectScoreParseVO projectScoreParseVO = new ProjectScoreParseVO();
        double projectScore = 0.0;
        //装填基础数据
        projectScoreParseVO.setEntityId(entityId);
        projectScoreParseVO.setEntityType(EntityType.PATENT.getValue());
        projectScoreParseVO.setName(patent.getName());
        projectScoreParseVO.setIsSkipFirst(Strings.isNullOrEmpty(patent.getAuthor()));
        projectScoreParseVO.setParseDate(new Date());
        //作者编号列表
        List<String> authorList = this.getAuthorNumberList(entityId);
        projectScoreParseVO.setAuthorList(authorList);

        //计算项目总分
        switch (patent.getType()) {
            //发明专利每项计20分
            case PATENT:
                projectScore += 20;
                break;
            //实用新型专利每项计4分
            //外观设计专利每项计4分
            case UTILITY_NEW_PATENT:
            case DESIGN_PATENT:
                projectScore += 4;
                break;
            default:
                projectScore += 0;
        }
        projectScoreParseVO.setProjectScore(projectScore);

        //计算每个人的科技分
        Map<String, Double> authorScoreMap = baseScoreComputer.computeEveryAuthorScore(authorList, projectScore, projectScoreParseVO.getIsSkipFirst());
        projectScoreParseVO.setAuthorScoreMap(authorScoreMap);
        //存储项目科技分信息
        patent.setResultScore(JSON.toJSONString(projectScoreParseVO));
        if (!this.patentService.save(patent)) {
            return false;
        }
        //更新每个人的科技分
        baseScoreComputer.updateUserScore(authorList, authorScoreMap, scoreService, userService, projectScoreParseVO, projectScoreParseVO.getIsSkipFirst());

        return false;
    }

    /**
     * 获得项目的相关人员编号
     *
     * @param entityId 项目ＩＤ
     * @return 项目的相关人员编号
     */
    @Override
    public List<String> getAuthorNumberList(int entityId) {
        Patent patent = this.patentService.getByPrimaryKey(entityId);
        if (patent == null) {
            return Collections.emptyList();
        }
        List<String> authorList = Lists.newArrayList();
        authorList.add(patent.getAuthor());
        String more = patent.getMoreAuthor().trim();
        if (!Strings.isNullOrEmpty(more)) {
            authorList.addAll(Arrays.asList(more.split(ConstantUtil.COMMA)));
        }
        return authorList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    @Override
    public boolean clearAuthorScore(int entityId) {
        return baseScoreComputer.clearAuthorScore(EntityType.PATENT.getValue(), entityId, getAuthorNumberList(entityId));
    }

    @Override
    public boolean restore(int entityId) {
        return patentService.save(this.patentService.getByPrimaryKey(entityId).setResultScore(null));
    }
}