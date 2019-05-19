package top.aleaf.sync.score;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.aleaf.model.*;
import top.aleaf.service.ScoreService;
import top.aleaf.service.UserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 〈基础科技分计算工具类〉
 *
 * @create 2019/5/4 0004
 * Author:   郭新晔
 */
@Component
public class BaseScoreComputer {
    @Resource
    private ScoreService scoreService;

    /**
     * 清理项目的已分配给作者的科技分
     *
     * @param entityType 项目类型
     * @param entityId   项目id
     * @param authorList 项目相关作者
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean clearAuthorScore(int entityType, int entityId, List<String> authorList) {
        if (CollectionUtils.isEmpty(authorList)) {
            return true;
        }
        //查询作者的科技分记录
        List<Score> scoreList = this.scoreService.getUserByNumberList(authorList);
        //更新科技分
        scoreList.forEach(score -> {
            UserScoreParseVO userScoreParseVO = JSON.parseObject(score.getScoreSource(), UserScoreParseVO.class);
            Map<ProjectScoreParseVO, Double> projectScoreMap = userScoreParseVO.getProjectScoreMap();
            for (ProjectScoreParseVO item : projectScoreMap.keySet()) {
                if (entityType == item.getEntityType() && entityId == item.getEntityId()) {
                    projectScoreMap.remove(item);
                    break;
                }
            }
            userScoreParseVO.setProjectScoreMap(projectScoreMap);
            score.setScoreSource(JSON.toJSONString(userScoreParseVO));
            this.scoreService.save(score);
        });

        return false;
    }

    /**
     * 获得每个作者的科技分
     *
     * @param authorList   作者列表
     * @param projectScore 项目总分
     * @param isSkipFirst  是否跳过第一个
     * @return
     */
    public Map<String, Double> computeEveryAuthorScore(List<String> authorList, double projectScore, boolean isSkipFirst) {
        if (isSkipFirst) {
            authorList.remove(0);
        }
        Map<String, Double> authorScoreMap = Maps.newHashMap();
        for (int i = 0; i < authorList.size(); i++) {
            if (authorList.size() > 11) {
                //超过11人时，后面的人得不到科技分
                authorList = authorList.subList(0, 11);
            }
            List<Double> scale = InitDataModel.scoreAllocation.get(authorList.size());
            authorScoreMap.put(authorList.get(i), projectScore * scale.get(i));
        }
        return authorScoreMap;
    }

    /**
     * 对每位用户的score进行更新
     *
     * @param authorList          作者列表
     * @param authorScoreMap      作者分数对应Map
     * @param scoreService        分数服务
     * @param userService         用户服务
     * @param projectScoreParseVO 项目分数JSON
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateUserScore(List<String> authorList, Map<String, Double> authorScoreMap,
                                ScoreService scoreService, UserService userService,
                                ProjectScoreParseVO projectScoreParseVO, boolean isSkipFirst) {
        if (isSkipFirst) {
            authorList.remove(0);
        }
        authorList.stream().filter(Objects::nonNull).forEach(item -> {
            Score score = scoreService.getByUserNumber(item);
            // score表没有此用户记录
            if (score == null) {
                score = new Score();
                User user = userService.getByNumber(item);
                score.setName(user.getName());
                score.setUserNumber(item);
                score.setProjectNum(0);
            }
            // 当前用户score的ScoreSource字段没有数据
            if (com.google.common.base.Strings.isNullOrEmpty(score.getScoreSource())) {
                UserScoreParseVO userScoreParseVO = new UserScoreParseVO();
                Map<ProjectScoreParseVO, Double> projectScoreMap = Maps.newHashMap();
                userScoreParseVO.setProjectScoreMap(projectScoreMap);
                score.setScoreSource(JSON.toJSONString(userScoreParseVO));
            }
            UserScoreParseVO userScoreParseVO = JSON.parseObject(score.getScoreSource(), UserScoreParseVO.class);
            userScoreParseVO.getProjectScoreMap().put(projectScoreParseVO, authorScoreMap.get(item));
            userScoreParseVO.setModify(new Date());

            // 科技分求和
            score.setSatScore(userScoreParseVO.getProjectScoreMap().values().stream().mapToDouble(Double::doubleValue).sum());
            score.setScoreSource(JSON.toJSONString(userScoreParseVO));
            score.setProjectNum(userScoreParseVO.getProjectScoreMap().size());
            scoreService.save(score);
        });
    }
}