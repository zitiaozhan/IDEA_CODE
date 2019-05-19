package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.PrizeMapper;
import top.aleaf.model.Prize;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.GeneralExample;

import javax.annotation.Resource;
import java.util.List;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/2/11 0011
 */
@Service
public class PrizeService {
    @Resource
    private PrizeMapper prizeMapper;
    @Resource
    private SensitiveFilterService filterService;

    public boolean save(Prize prize) {
        if (prize.getName() != null) {
            prize.setName(filterService.filterSensitiveWord(prize.getName()));
        }
        if (prize.getId() == null) {
            return this.prizeMapper.insert(prize) > 0;
        } else {
            return this.prizeMapper.updateByPrimaryKeySelective(prize) > 0;
        }
    }

    public boolean delete(Prize prize) {
        if (prize != null && prize.getId() != null) {
            prize.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
            return this.prizeMapper.updateByPrimaryKeySelective(prize) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        Prize prize = new Prize();
        prize.setId(id);
        prize.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
        return this.prizeMapper.updateByPrimaryKeySelective(prize) > 0;
    }

    public boolean setStatus(Prize prize) {
        if (prize != null && prize.getId() != null) {
            return this.prizeMapper.updateByPrimaryKeySelective(prize) > 0;
        }
        return false;
    }

    public boolean setStatus(int id, int status) {
        Prize prize = new Prize();
        prize.setId(id);
        prize.setStatus(status);
        return this.prizeMapper.updateByPrimaryKeySelective(prize) > 0;
    }

    public Prize getByPrimaryKey(int id) {
        return this.prizeMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(Prize.class, " and id=" + id, false));
    }

    public List<Prize> getAll(Prize prize) {
        if (prize.getPage() != null && prize.getRows() != null) {
            PageHelper.startPage(prize.getPage(), prize.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (prize.getScoreNotNull()) {
            builder.append(" and result_score is not null");
        }
        if (prize.getName() != null) {
            builder.append(" and name like '%").append(prize.getName()).append("%' ");
        }
        if (prize.getAuthor() != null) {
            builder.append(" and author='").append(prize.getAuthor()).append("' ");
        }
        if (prize.getStatus() != null) {
            builder.append(" and status=").append(prize.getStatus());
        }
        if (prize.getId() != null) {
            builder.append(" and id=").append(prize.getId());
        }
        if (prize.getCreatedId() != null) {
            builder.append(" and created_id=").append(prize.getCreatedId());
        }
        return prizeMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                Prize.class, builder.toString(), false
        ));
    }

    public List<Prize> getByCreatedId(int userId) {
        return this.prizeMapper.selectByExample(
                GeneralExample.getConditionAndGroupAndOrderExample(Prize.class,"created_date desc", "limit 10", "created_id=" + userId + " and status!=" + ConstantUtil.PROJECT_STATUS_DELETE));
    }

    /**
     * 数量
     * @param status
     * @return
     */
    public int getAllCount(int status) {
        if (-1 == status) {
            return this.prizeMapper.selectCount(new Prize().setStatus(null));
        }
        return this.prizeMapper.selectCount(new Prize().setStatus(status));
    }
}