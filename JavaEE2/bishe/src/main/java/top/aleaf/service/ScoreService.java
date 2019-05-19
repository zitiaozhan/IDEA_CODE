package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.ScoreMapper;
import top.aleaf.model.Score;
import top.aleaf.utils.BisheUtil;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.GeneralExample;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/2/11 0011
 */
@Service
public class ScoreService {
    @Resource
    private ScoreMapper scoreMapper;

    public boolean save(Score score) {
        if (score.getId() == null) {
            return this.scoreMapper.insert(score) > 0;
        } else {
            return this.scoreMapper.updateByPrimaryKeySelective(score) > 0;
        }
    }

    public boolean delete(Score score) {
        if (score != null && score.getId() != null) {
            score.setStatus(1);
            return this.scoreMapper.updateByPrimaryKeySelective(score) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        Score score = new Score();
        score.setId(id);
        score.setStatus(1);
        return this.scoreMapper.updateByPrimaryKeySelective(score) > 0;
    }

    public Score getByPrimaryKey(int id) {
        return this.scoreMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(Score.class, " and id=" + id, true));
    }

    public Score getByUserNumber(String userNumber) {
        return this.scoreMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(Score.class, " and user_number=" + userNumber, true));
    }

    public List<Score> getAll(Score score) {
        if (score.getPage() != null && score.getRows() != null) {
            PageHelper.startPage(score.getPage(), score.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (score.getUserNumber() != null) {
            builder.append(" and user_number=").append(score.getUserNumber()).append(" ");
        }
        return scoreMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                Score.class, builder.toString(), true
        ));
    }

    /**
     * 分数排行榜TOP10
     *
     * @return 列表
     */
    public List<Score> getScoreTop() {
        List<Score> list = this.scoreMapper.selectByExample(
                GeneralExample.getConditionAndGroupAndOrderExample(Score.class, "sat_score desc", "limit 10", " status=0")).stream().limit(10).collect(Collectors.toList());

        list.forEach(item -> {
            item.setSatScore(Double.parseDouble(BisheUtil.doubleFormat(item.getSatScore(), ConstantUtil.DOUBLE_PATTERN)));
        });
        return list;
    }

    /**
     * 数量排行榜TOP10
     *
     * @return 列表
     */
    public List<Score> getNumberTop() {
        return this.scoreMapper.selectByExample(
                GeneralExample.getConditionAndGroupAndOrderExample(Score.class, "project_num desc,sat_score desc", "limit 10", " status=0")).stream().limit(10).collect(Collectors.toList());
    }

    /**
     * 按用户编号查询
     *
     * @param numberList 用户编号列表
     * @return 结果
     */
    public List<Score> getUserByNumberList(List<String> numberList) {
        return this.scoreMapper.selectByNumberList(numberList);
    }
}