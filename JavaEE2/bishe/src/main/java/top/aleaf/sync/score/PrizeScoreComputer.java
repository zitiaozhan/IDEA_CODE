package top.aleaf.sync.score;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.aleaf.model.Prize;
import top.aleaf.model.ProjectScoreParseVO;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.service.PrizeService;
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
public class PrizeScoreComputer implements ScoreComputer {
    @Resource
    private PrizeService prizeService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private UserService userService;
    @Resource
    private BaseScoreComputer baseScoreComputer;

    private static final String PRIZE_COUNTRY = "国家科学技术奖";
    private static final String PRIZE_MINISTRY_OF_EDUCATION = "教育部人文社会科学研究优秀成果奖";
    private static final String PRIZE_PROVINCE_SCIENCE = "省部级科学技术奖";
    private static final String PRIZE_PROVINCE_SOCIETY = "省部级哲学社会科学优秀成果奖";
    private static final String PRIZE_COUNTRY_OFFICE = "国科奖办公室批科学技术奖";
    private static final String PRIZE_CITY_SCIENCE = "厅级科学技术奖";
    private static final String PRIZE_CITY_SOCIETY = "厅级人文社会科学成果奖";
    private static final String PRIZE_LEVEL_SPECIAL = "特等奖";
    private static final String PRIZE_LEVEL_ONE = "一等奖";
    private static final String PRIZE_LEVEL_TWO = "二等奖";
    private static final String PRIZE_LEVEL_THREE = "三等奖";

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean computeProjectScore(int entityId) {
        Prize prize = this.prizeService.getByPrimaryKey(entityId);
        if (prize == null) {
            return false;
        }
        ProjectScoreParseVO projectScoreParseVO = new ProjectScoreParseVO();
        double projectScore = 0.0;

        //填充基础数据
        projectScoreParseVO.setEntityId(prize.getId());
        projectScoreParseVO.setEntityType(EntityType.PRIZE.getValue());
        projectScoreParseVO.setName(prize.getName());
        projectScoreParseVO.setParseDate(new Date());
        //作者编号列表
        List<String> authorList = this.getAuthorNumberList(entityId);
        projectScoreParseVO.setAuthorList(authorList);
        projectScoreParseVO.setIsSkipFirst(Strings.isNullOrEmpty(prize.getAuthor()));

        //项目总分数
        switch (prize.getPrizeType()) {
            //国家科学技术奖特等奖每项计1000分，一等奖每项计500分、二等奖每项计200分
            case PRIZE_COUNTRY:
                switch (prize.getPrizeLevel()) {
                    case PRIZE_LEVEL_SPECIAL:
                        projectScore += 1000;
                        break;
                    case PRIZE_LEVEL_ONE:
                        projectScore += 500;
                        break;
                    case PRIZE_LEVEL_TWO:
                        projectScore += 200;
                        break;
                    default:
                        projectScore += 0;
                }
                break;
            //教育部人文社会科学研究优秀成果奖一等奖每项计120分、二等奖每项计80分、三等奖每项计40分
            //省部级科学技术奖一等奖每项计120分、二等奖每项计80分、三等奖每项计40分
            case PRIZE_MINISTRY_OF_EDUCATION:
            case PRIZE_PROVINCE_SCIENCE:
                switch (prize.getPrizeLevel()) {
                    case PRIZE_LEVEL_ONE:
                        projectScore += 120;
                        break;
                    case PRIZE_LEVEL_TWO:
                        projectScore += 80;
                        break;
                    case PRIZE_LEVEL_THREE:
                        projectScore += 40;
                        break;
                    default:
                        projectScore += 0;
                }
                break;
            //省部级哲学社会科学优秀成果奖一等奖每项计80分、二等奖每项计40分、三等奖每项计20分
            //经国家科学技术奖励工作办公室登记审批的各种社会力量所设的科学技术奖一等奖每项计80分、二等奖每项计40分、三等奖每项计20分
            case PRIZE_PROVINCE_SOCIETY:
            case PRIZE_COUNTRY_OFFICE:
                switch (prize.getPrizeLevel()) {
                    case PRIZE_LEVEL_ONE:
                        projectScore += 80;
                        break;
                    case PRIZE_LEVEL_TWO:
                        projectScore += 40;
                        break;
                    case PRIZE_LEVEL_THREE:
                        projectScore += 20;
                        break;
                    default:
                        projectScore += 0;
                }
                break;
            //地、市（厅）级科学技术奖一等奖每项计40分、二等奖每项计20分、三等奖每项计10分
            case PRIZE_CITY_SCIENCE:
                switch (prize.getPrizeLevel()) {
                    case PRIZE_LEVEL_ONE:
                        projectScore += 40;
                        break;
                    case PRIZE_LEVEL_TWO:
                        projectScore += 20;
                        break;
                    case PRIZE_LEVEL_THREE:
                        projectScore += 10;
                        break;
                    default:
                        projectScore += 0;
                }
                break;
            //地、市（厅）级人文社会科学成果一等奖每项计20分、二等奖每项计12分、三等奖每项计8分
            case PRIZE_CITY_SOCIETY:
                switch (prize.getPrizeLevel()) {
                    case PRIZE_LEVEL_ONE:
                        projectScore += 20;
                        break;
                    case PRIZE_LEVEL_TWO:
                        projectScore += 12;
                        break;
                    case PRIZE_LEVEL_THREE:
                        projectScore += 8;
                        break;
                    default:
                        projectScore += 0;
                }
                break;
            default:
                projectScore += 0;
        }
        projectScoreParseVO.setProjectScore(projectScore);

        //计算每个人的科技分
        Map<String, Double> authorScoreMap = baseScoreComputer.computeEveryAuthorScore(authorList, projectScore, projectScoreParseVO.getIsSkipFirst());
        projectScoreParseVO.setAuthorScoreMap(authorScoreMap);
        // 保存信息
        prize.setResultScore(JSON.toJSONString(projectScoreParseVO));
        if (!this.prizeService.save(prize)) {
            return false;
        }
        // 对每位用户的score进行更新
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
        Prize prize = this.prizeService.getByPrimaryKey(entityId);
        if (prize == null) {
            return Collections.emptyList();
        }
        List<String> authorList = Lists.newArrayList();
        authorList.add(prize.getAuthor());
        String guidanceTeacher = prize.getGuidanceTeacher().trim();
        if (!Strings.isNullOrEmpty(guidanceTeacher)) {
            authorList.addAll(Arrays.asList(guidanceTeacher.split(ConstantUtil.COMMA)));
        }
        return authorList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    @Override
    public boolean clearAuthorScore(int entityId) {
        return baseScoreComputer.clearAuthorScore(EntityType.PRIZE.getValue(), entityId, getAuthorNumberList(entityId));
    }

    @Override
    public boolean restore(int entityId) {
        return prizeService.save(this.prizeService.getByPrimaryKey(entityId).setResultScore(null));
    }
}