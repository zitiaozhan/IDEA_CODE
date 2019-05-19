package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.GovernmentProjectMapper;
import top.aleaf.model.GovernmentProject;
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
public class GovernmentProjectService {
    @Resource
    private GovernmentProjectMapper governmentProjectMapper;
    @Resource
    private SensitiveFilterService filterService;

    public boolean save(GovernmentProject governmentProject) {
        if (governmentProject.getName() != null) {
            governmentProject.setName(filterService.filterSensitiveWord(governmentProject.getName()));
        }
        if (governmentProject.getId() == null) {
            return this.governmentProjectMapper.insert(governmentProject) > 0;
        } else {
            return this.governmentProjectMapper.updateByPrimaryKeySelective(governmentProject) > 0;
        }
    }

    public boolean delete(GovernmentProject governmentProject) {
        if (governmentProject != null && governmentProject.getId() != null) {
            governmentProject.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
            return this.governmentProjectMapper.updateByPrimaryKeySelective(governmentProject) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        GovernmentProject governmentProject = new GovernmentProject();
        governmentProject.setId(id);
        governmentProject.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
        return this.governmentProjectMapper.updateByPrimaryKeySelective(governmentProject) > 0;
    }

    public boolean setStatus(GovernmentProject governmentProject) {
        return governmentProject != null && governmentProject.getId() != null && this.governmentProjectMapper.updateByPrimaryKeySelective(governmentProject) > 0;
    }

    public boolean setStatus(int id, int status) {
        GovernmentProject governmentProject = new GovernmentProject();
        governmentProject.setId(id);
        governmentProject.setStatus(status);
        return this.governmentProjectMapper.updateByPrimaryKeySelective(governmentProject) > 0;
    }

    public GovernmentProject getByPrimaryKey(int id) {
        return this.governmentProjectMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(GovernmentProject.class, " and id=" + id, false));
    }

    public List<GovernmentProject> getByCreatedId(int userId) {
        return this.governmentProjectMapper.selectByExample(
                GeneralExample.getConditionAndGroupAndOrderExample(GovernmentProject.class,"created_date desc", "limit 10", "created_id=" + userId + " and status!=" + ConstantUtil.PROJECT_STATUS_DELETE));
    }

    public List<GovernmentProject> getAll(GovernmentProject governmentProject) {
        if (governmentProject.getPage() != null && governmentProject.getRows() != null) {
            PageHelper.startPage(governmentProject.getPage(), governmentProject.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (governmentProject.getScoreNotNull()) {
            builder.append(" and result_score is not null");
        }
        if (governmentProject.getName() != null) {
            builder.append(" and name like '%").append(governmentProject.getName()).append("%' ");
        }
        if (governmentProject.getChargePerson() != null) {
            builder.append(" and charge_person='").append(governmentProject.getChargePerson()).append("' ");
        }
        if (governmentProject.getStatus() != null) {
            builder.append(" and status=").append(governmentProject.getStatus());
        }
        if (governmentProject.getId() != null) {
            builder.append(" and id=").append(governmentProject.getId());
        }
        if (governmentProject.getCreatedId() != null) {
            builder.append(" and created_id=").append(governmentProject.getCreatedId());
        }
        return governmentProjectMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                GovernmentProject.class, builder.toString(), false
        ));
    }

    /**
     * 数量
     * @param status
     * @return
     */
    public int getAllCount(int status) {
        if (-1 == status) {
            return this.governmentProjectMapper.selectCount(new GovernmentProject().setStatus(null));
        }
        return this.governmentProjectMapper.selectCount(new GovernmentProject().setStatus(status));
    }
}