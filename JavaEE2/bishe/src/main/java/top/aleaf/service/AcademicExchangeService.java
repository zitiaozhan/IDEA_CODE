package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.AcademicExchangeMapper;
import top.aleaf.model.AcademicExchange;
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
public class AcademicExchangeService {
    @Resource
    private AcademicExchangeMapper academicExchangeMapper;
    @Resource
    private SensitiveFilterService filterService;

    public boolean save(AcademicExchange academicExchange) {
        if (academicExchange.getMeetingName() != null) {
            academicExchange.setMeetingName(filterService.filterSensitiveWord(academicExchange.getMeetingName()));
        }
        if (academicExchange.getId() == null) {
            return this.academicExchangeMapper.insert(academicExchange) > 0;
        } else {
            return this.academicExchangeMapper.updateByPrimaryKeySelective(academicExchange) > 0;
        }
    }

    public boolean delete(AcademicExchange academicExchange) {
        if (academicExchange != null && academicExchange.getId() != null) {
            academicExchange.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
            return this.academicExchangeMapper.updateByPrimaryKeySelective(academicExchange) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        AcademicExchange academicExchange = new AcademicExchange();
        academicExchange.setId(id);
        academicExchange.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
        return this.academicExchangeMapper.updateByPrimaryKeySelective(academicExchange) > 0;
    }

    public boolean setStatus(AcademicExchange academicExchange) {
        return academicExchange != null && academicExchange.getId() != null && this.academicExchangeMapper.updateByPrimaryKeySelective(academicExchange) > 0;
    }

    public boolean setStatus(int id, int status) {
        AcademicExchange academicExchange = new AcademicExchange();
        academicExchange.setId(id);
        academicExchange.setStatus(status);
        return this.academicExchangeMapper.updateByPrimaryKeySelective(academicExchange) > 0;
    }

    public AcademicExchange getByPrimaryKey(int id) {
        return this.academicExchangeMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(AcademicExchange.class, " and id=" + id, false));
    }

    public List<AcademicExchange> getAll(AcademicExchange academicExchange) {
        if (academicExchange.getPage() != null && academicExchange.getRows() != null) {
            PageHelper.startPage(academicExchange.getPage(), academicExchange.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (academicExchange.getScoreNotNull()) {
            builder.append(" and result_score is not null");
        }
        if (academicExchange.getMeetingName() != null) {
            builder.append(" and meeting_name like '%").append(academicExchange.getMeetingName()).append("%' ");
        }
        if (academicExchange.getMainParticipant() != null) {
            builder.append(" and main_participant like '%").append(academicExchange.getMainParticipant()).append("%' ");
        }
        if (academicExchange.getStatus() != null) {
            builder.append(" and status=").append(academicExchange.getStatus());
        }
        if (academicExchange.getId() != null) {
            builder.append(" and id=").append(academicExchange.getId());
        }
        if (academicExchange.getCreatedId() != null) {
            builder.append(" and created_id=").append(academicExchange.getCreatedId());
        }
        return academicExchangeMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                AcademicExchange.class, builder.toString(), false
        ));
    }

    public List<AcademicExchange> getByCreatedId(int userId) {
        return this.academicExchangeMapper.selectByExample(
                GeneralExample.getConditionAndGroupAndOrderExample(AcademicExchange.class,"created_date desc", "limit 10", "created_id=" + userId + " and status!=" + ConstantUtil.PROJECT_STATUS_DELETE));
    }

    /**
     * 数量
     * @param status
     * @return
     */
    public int getAllCount(int status) {
        if (-1 == status) {
            return this.academicExchangeMapper.selectCount(new AcademicExchange().setStatus(null));
        }
        return this.academicExchangeMapper.selectCount(new AcademicExchange().setStatus(status));
    }
}