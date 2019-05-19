package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.CompanyProjectMapper;
import top.aleaf.model.CompanyProject;
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
public class CompanyProjectService {
    @Resource
    private CompanyProjectMapper companyProjectMapper;
    @Resource
    private SensitiveFilterService filterService;

    public boolean save(CompanyProject companyProject) {
        if (companyProject.getName() != null) {
            companyProject.setName(filterService.filterSensitiveWord(companyProject.getName()));
        }
        if (companyProject.getChargeGroup() != null) {
            companyProject.setChargeGroup(filterService.filterSensitiveWord(companyProject.getChargeGroup()));
        }
        if (companyProject.getProjectSource() != null) {
            companyProject.setProjectSource(filterService.filterSensitiveWord(companyProject.getProjectSource()));
        }

        if (companyProject.getId() == null) {
            return this.companyProjectMapper.insert(companyProject) > 0;
        } else {
            return this.companyProjectMapper.updateByPrimaryKeySelective(companyProject) > 0;
        }
    }

    public boolean delete(CompanyProject companyProject) {
        if (companyProject != null && companyProject.getId() != null) {
            companyProject.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
            return this.companyProjectMapper.updateByPrimaryKeySelective(companyProject) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        CompanyProject companyProject = new CompanyProject();
        companyProject.setId(id);
        companyProject.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
        return this.companyProjectMapper.updateByPrimaryKeySelective(companyProject) > 0;
    }

    public boolean setStatus(CompanyProject companyProject) {
        return companyProject != null && companyProject.getId() != null && this.companyProjectMapper.updateByPrimaryKeySelective(companyProject) > 0;
    }

    public boolean setStatus(int id, int status) {
        CompanyProject companyProject = new CompanyProject();
        companyProject.setId(id);
        companyProject.setStatus(status);
        return this.companyProjectMapper.updateByPrimaryKeySelective(companyProject) > 0;
    }

    public CompanyProject getByPrimaryKey(int id) {
        return this.companyProjectMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(CompanyProject.class, " and id=" + id, false));
    }

    public List<CompanyProject> getByCreatedId(int userId) {
        return this.companyProjectMapper.selectByExample(
                GeneralExample.getConditionAndGroupAndOrderExample(CompanyProject.class,"created_date desc", "limit 10", "created_id=" + userId + " and status!=" + ConstantUtil.PROJECT_STATUS_DELETE));
    }

    public List<CompanyProject> getAll(CompanyProject companyProject) {
        if (companyProject.getPage() != null && companyProject.getRows() != null) {
            PageHelper.startPage(companyProject.getPage(), companyProject.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (companyProject.getScoreNotNull()) {
            builder.append(" and result_score is not null");
        }
        if (companyProject.getId() != null) {
            builder.append(" and id =").append(companyProject.getId()).append(" ");
        }
        if (companyProject.getName() != null) {
            builder.append(" and name like '%").append(companyProject.getName()).append("%' ");
        }
        if (companyProject.getChargePerson() != null) {
            builder.append(" and charge_person='").append(companyProject.getChargePerson()).append("' ");
        }
        if (companyProject.getStatus() != null) {
            builder.append(" and status=").append(companyProject.getStatus());
        }
        if (companyProject.getCreatedId() != null) {
            builder.append(" and created_id=").append(companyProject.getCreatedId());
        }
        return companyProjectMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                CompanyProject.class, builder.toString(), false
        ));
    }

    /**
     * 数量
     *
     * @param status
     * @return
     */
    public int getAllCount(int status) {
        if (-1 == status) {
            return this.companyProjectMapper.selectCount(new CompanyProject().setStatus(null));
        }
        return this.companyProjectMapper.selectCount(new CompanyProject().setStatus(status));
    }
}