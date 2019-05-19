package top.aleaf.sync.score;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.model.ProjectScoreParseVO;
import top.aleaf.model.SoftwareWork;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.service.ScoreService;
import top.aleaf.service.SoftwareWorkService;
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
public class SoftwareWorkScoreComputer implements ScoreComputer {
    @Resource
    private SoftwareWorkService softwareWorkService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private UserService userService;
    @Resource
    private BaseScoreComputer baseScoreComputer;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean computeProjectScore(int entityId) {
        SoftwareWork softwareWork = this.softwareWorkService.getByPrimaryKey(entityId);
        if (null == softwareWork) {
            return false;
        }
        ProjectScoreParseVO projectScoreParseVO = new ProjectScoreParseVO();
        projectScoreParseVO.setEntityId(entityId);
        projectScoreParseVO.setEntityType(EntityType.SOFTWARE_WORK.getValue());
        projectScoreParseVO.setName(softwareWork.getName());
        projectScoreParseVO.setParseDate(new Date());
        //作者编号列表
        List<String> authorList = this.getAuthorNumberList(entityId);
        projectScoreParseVO.setAuthorList(authorList);

        double projectScore = 4.0;
        projectScoreParseVO.setProjectScore(projectScore);

        //计算每个人的科技分
        Map<String, Double> authorScoreMap = baseScoreComputer.computeEveryAuthorScore(authorList, projectScore, false);
        projectScoreParseVO.setAuthorScoreMap(authorScoreMap);
        //存储项目科技分信息
        softwareWork.setResultScore(JSON.toJSONString(projectScoreParseVO));
        if (!this.softwareWorkService.save(softwareWork)) {
            return false;
        }
        //更新每个人的科技分
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
        SoftwareWork softwareWork = this.softwareWorkService.getByPrimaryKey(entityId);
        if (softwareWork == null) {
            return Collections.emptyList();
        }
        List<String> authorList = Lists.newArrayList();
        String author = softwareWork.getAuthor().trim();
        if (!Strings.isNullOrEmpty(author)) {
            authorList.addAll(Arrays.asList(author.split(ConstantUtil.COMMA)));
        }
        return authorList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    @Override
    public boolean clearAuthorScore(int entityId) {
        return baseScoreComputer.clearAuthorScore(EntityType.SOFTWARE_WORK.getValue(), entityId, getAuthorNumberList(entityId));
    }

    @Override
    public boolean restore(int entityId) {
        return softwareWorkService.save(this.softwareWorkService.getByPrimaryKey(entityId).setResultScore(null));
    }
}