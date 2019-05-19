package top.aleaf.sync.score;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.model.ProjectScoreParseVO;
import top.aleaf.model.TeachingMaterial;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.service.ScoreService;
import top.aleaf.service.TeachingMaterialService;
import top.aleaf.service.UserService;
import top.aleaf.utils.ConstantUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 郭新晔
 * @create 2019/4/6 0006
 */
@Component
public class TeachingMaterialScoreComputer implements ScoreComputer {
    @Resource
    private TeachingMaterialService teachingMaterialService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private UserService userService;
    @Resource
    private BaseScoreComputer baseScoreComputer;

    private static final String AREA_INTERNAL = "国内";
    private static final String AREA_FOREIGN = "国外";

    private static final String TYPE_ZHUANZHU = "专著";
    private static final String TYPE_TRANSLATION = "译著";
    private static final String TYPE_EDITED = "编著";
    private static final String TYPE_POPULAR_SCIENCE_BOOKS = "科普读物";
    private static final String TYPE_REFERENCE_BOOK = "工具书";

    private static final String ONE_LEVEL_PUBLISHER = "一类出版社";
    private static final String TWO_LEVEL_PUBLISHER = "二类出版社";
    private static final String OTHER_PUBLISHER = "其它出版社";

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean computeProjectScore(int entityId) {
        TeachingMaterial teachingMaterial = this.teachingMaterialService.getByPrimaryKey(entityId);
        if (null == teachingMaterial) {
            return false;
        }
        double projectScore = 0.0;
        ProjectScoreParseVO projectScoreParseVO = new ProjectScoreParseVO();
        projectScoreParseVO.setEntityId(entityId);
        projectScoreParseVO.setEntityType(EntityType.TEACHING_MATERIAL.getValue());
        projectScoreParseVO.setName(teachingMaterial.getName());
        projectScoreParseVO.setParseDate(new Date());
        //作者编号列表
        List<String> authorList = this.getAuthorNumberList(entityId);
        projectScoreParseVO.setAuthorList(authorList);

        //计算科技总分
        int wordCount = teachingMaterial.getWordNumber();
        //国外公开出版的科技专著每万字计5分
        if (AREA_FOREIGN.equals(teachingMaterial.getArea()) && TYPE_ZHUANZHU.equals(teachingMaterial.getType())) {
            projectScore += 5 * wordCount;
        }
        if (AREA_INTERNAL.equals(teachingMaterial.getArea())) {
            //国内公开出版的科技专著、译著每万字计3分
            if (TYPE_TRANSLATION.equals(teachingMaterial.getType()) || TYPE_ZHUANZHU.equals(teachingMaterial.getType())) {
                projectScore += 3 * wordCount;
            }
            //国内公开出版的科技编著每万字计1分
            if (TYPE_EDITED.equals(teachingMaterial.getType())) {
                projectScore += wordCount;
            }
            //国内公开出版的科普读物每万字计0.2分
            if (TYPE_POPULAR_SCIENCE_BOOKS.equals(teachingMaterial.getType())) {
                projectScore += 0.2 * wordCount;
            }
            //国内公开出版的科技工具书每万字计0.05分
            if (TYPE_REFERENCE_BOOK.equals(teachingMaterial.getType())) {
                projectScore += 0.05 * wordCount;
            }
        }
        //计算著作科技分值时，学校认定的一类出版社乘系数1.0、二类出版社乘系数0.8，其它出版社乘系数0.5
        if (ONE_LEVEL_PUBLISHER.equals(teachingMaterial.getPublishLevel())) {
            projectScore *= 1;
        }
        if (TWO_LEVEL_PUBLISHER.equals(teachingMaterial.getPublishLevel())) {
            projectScore *= 0.8;
        }
        if (OTHER_PUBLISHER.equals(teachingMaterial.getPublishLevel())) {
            projectScore *= 0.5;
        }
        projectScoreParseVO.setProjectScore(projectScore);

        //计算每个人的科技分
        Map<String, Double> authorScoreMap = baseScoreComputer.computeEveryAuthorScore(authorList, projectScore, false);
        projectScoreParseVO.setAuthorScoreMap(authorScoreMap);
        //存储
        teachingMaterial.setResultScore(JSON.toJSONString(projectScoreParseVO));
        if (!teachingMaterialService.save(teachingMaterial)) {
            return false;
        }

        //更新每个人的科技分信息
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
        TeachingMaterial teachingMaterial = this.teachingMaterialService.getByPrimaryKey(entityId);
        if (teachingMaterial == null) {
            return Collections.emptyList();
        }
        List<String> authorList = Lists.newArrayList();
        authorList.add(teachingMaterial.getAuthor());
        String more = teachingMaterial.getMoreAuthor().trim();
        if (!Strings.isNullOrEmpty(more)) {
            authorList.addAll(Arrays.asList(more.split(ConstantUtil.COMMA)));
        }
        return authorList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    @Override
    public boolean clearAuthorScore(int entityId) {
        return baseScoreComputer.clearAuthorScore(EntityType.TEACHING_MATERIAL.getValue(), entityId, getAuthorNumberList(entityId));
    }

    @Override
    public boolean restore(int entityId) {
        TeachingMaterial teachingMaterial = this.teachingMaterialService.getByPrimaryKey(entityId).setResultScore(null);
        return teachingMaterialService.save(teachingMaterial);
    }
}