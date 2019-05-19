/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PaperScoreComputer
 * Author:   郭新晔
 * Date:     2019/4/6 0006 14:49
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.sync.score;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.model.Paper;
import top.aleaf.model.ProjectScoreParseVO;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.service.PaperService;
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
public class PaperScoreComputer implements ScoreComputer {
    @Resource
    private PaperService paperService;
    @Resource
    private UserService userService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private BaseScoreComputer baseScoreComputer;

    private static final String SPECIAL_JOURNAL = "特种期刊";
    private static final String AUTHORITATIVE_ONE = "权威一类";
    private static final String AUTHORITATIVE_TWO = "权威二类";
    private static final String AUTHORITATIVE_THREE = "权威三类";
    private static final String CORE_ONE = "核心一类";
    private static final String CORE_TWO = "核心二类";
    private static final String OTHER_DOMESTIC_PUBLIC_JOURNALS = "其余国内公开期刊";

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean computeProjectScore(int entityId) {
        Paper paper = this.paperService.getByPrimaryKey(entityId);
        if (paper == null) {
            return false;
        }
        ProjectScoreParseVO projectScoreParseVO = new ProjectScoreParseVO();
        double projectScore = 0.0;

        //装填基础数据
        projectScoreParseVO.setEntityId(paper.getId());
        projectScoreParseVO.setEntityType(EntityType.PAPER.getValue());
        projectScoreParseVO.setIsSkipFirst(Strings.isNullOrEmpty(paper.getFirstAuthor()));
        projectScoreParseVO.setName(paper.getName());
        projectScoreParseVO.setParseDate(new Date());

        //作者编号列表
        List<String> authorList = this.getAuthorNumberList(entityId);
        projectScoreParseVO.setAuthorList(authorList);

        //计算项目总分
        switch (paper.getLevel()) {
            //在特种期刊上发表的论文每篇计200分
            case SPECIAL_JOURNAL:
                projectScore += 200;
                break;
            //在权威一类期刊上发表的论文每篇计20分
            case AUTHORITATIVE_ONE:
                projectScore += 20;
                break;
            //在权威二类期刊上发表的论文每篇计16分
            case AUTHORITATIVE_TWO:
                projectScore += 16;
                break;
            //在权威三类期刊上发表的论文每篇计12分
            case AUTHORITATIVE_THREE:
                projectScore += 12;
                break;
            //在核心一类期刊上发表的论文每篇计8分
            case CORE_ONE:
                projectScore += 8;
                break;
            //在核心二类期刊上发表的论文每篇计4分
            case CORE_TWO:
                projectScore += 4;
                break;
            //其余国内公开期刊上发表的论文每篇计2分
            case OTHER_DOMESTIC_PUBLIC_JOURNALS:
                projectScore += 2;
                break;
            //在增刊、专刊上发表的论文不计分
            default:
                projectScore += 0;
        }
        projectScoreParseVO.setProjectScore(projectScore);

        //计算每个人的科技分
        Map<String, Double> authorScoreMap = baseScoreComputer.computeEveryAuthorScore(authorList, projectScore, projectScoreParseVO.getIsSkipFirst());
        projectScoreParseVO.setAuthorScoreMap(authorScoreMap);

        //保存项目科技分信息
        paper.setResultScore(JSON.toJSONString(projectScoreParseVO));
        if (!this.paperService.save(paper)) {
            return false;
        }

        //更新个人科技分JSON
        baseScoreComputer.updateUserScore(authorList, authorScoreMap, scoreService, userService, projectScoreParseVO, projectScoreParseVO.getIsSkipFirst());

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
        Paper paper = this.paperService.getByPrimaryKey(entityId);
        if (paper == null) {
            return Collections.emptyList();
        }
        List<String> authorList = Lists.newArrayList();
        authorList.add(paper.getFirstAuthor());
        String otherAuthor = paper.getMoreAuthor().trim();
        if (!Strings.isNullOrEmpty(otherAuthor)) {
            authorList.addAll(Arrays.asList(otherAuthor.split(ConstantUtil.COMMA)));
        }
        return authorList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    @Override
    public boolean clearAuthorScore(int entityId) {
        return baseScoreComputer.clearAuthorScore(EntityType.PAPER.getValue(), entityId, getAuthorNumberList(entityId));
    }

    @Override
    public boolean restore(int entityId) {
        return paperService.save(this.paperService.getByPrimaryKey(entityId).setResultScore(null));
    }
}