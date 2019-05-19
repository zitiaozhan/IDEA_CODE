/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LectureScoreComputer
 * Author:   郭新晔
 * Date:     2019/4/6 0006 14:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.sync.score;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.model.Lecture;
import top.aleaf.model.ProjectScoreParseVO;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.service.LectureService;
import top.aleaf.service.ScoreService;
import top.aleaf.service.UserService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/4/6 0006
 */
@Component
public class LectureScoreComputer implements ScoreComputer {
    @Resource
    private LectureService lectureService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private UserService userService;
    @Resource
    private BaseScoreComputer baseScoreComputer;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean computeProjectScore(int entityId) {
        Lecture lecture = lectureService.getByPrimaryKey(entityId);
        if (null == lecture) {
            return false;
        }
        ProjectScoreParseVO projectScoreParseVO = new ProjectScoreParseVO();
        projectScoreParseVO.setEntityId(entityId);
        projectScoreParseVO.setEntityType(EntityType.LECTURE.getValue());
        projectScoreParseVO.setName(lecture.getName());
        projectScoreParseVO.setParseDate(new Date());
        projectScoreParseVO.setProjectScore(0.5);
        projectScoreParseVO.setAuthorList(getAuthorNumberList(entityId));
        Map<String, Double> authorScoreMap = Maps.newHashMap();
        authorScoreMap.put(lecture.getRapporteur(), 0.5);
        projectScoreParseVO.setAuthorScoreMap(authorScoreMap);

        lecture.setResultScore(JSON.toJSONString(projectScoreParseVO));
        if (!this.lectureService.save(lecture)) {
            return false;
        }

        //更新作者科技分信息
        baseScoreComputer.updateUserScore(Collections.singletonList(lecture.getRapporteur()), authorScoreMap, scoreService, userService, projectScoreParseVO, false);

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
        Lecture lecture = lectureService.getByPrimaryKey(entityId);
        if (lecture == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(lecture.getRapporteur());
    }

    @Override
    public boolean clearAuthorScore(int entityId) {
        return baseScoreComputer.clearAuthorScore(EntityType.LECTURE.getValue(), entityId, getAuthorNumberList(entityId));
    }

    @Override
    public boolean restore(int entityId) {
        return lectureService.save(this.lectureService.getByPrimaryKey(entityId).setResultScore(null));
    }
}