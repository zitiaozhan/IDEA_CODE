package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.ScoreMapper;
import top.aleaf.model.Score;
import top.aleaf.utils.GeneralExample;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Service
public class ScoreService {
    @Autowired
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

    public List<Score> getAll(Score score) {
        if (score.getPage() != null && score.getRows() != null) {
            PageHelper.startPage(score.getPage(), score.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (score.getUserId() != null) {
            builder.append(" and user_id=").append(score.getUserId()).append(" ");
        }
        return scoreMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                Score.class, builder.toString(), true
        ));
    }
}