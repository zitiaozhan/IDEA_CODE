package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.SoftwareWorkMapper;
import top.aleaf.model.SoftwareWork;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.GeneralExample;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Service
public class SoftwareWorkService {
    @Autowired
    private SoftwareWorkMapper softwareWorkMapper;
    @Autowired
    private SensitiveFilterService filterService;

    public boolean save(SoftwareWork softwareWork) {
        if (softwareWork.getName() != null) {
            softwareWork.setName(filterService.filterSensitiveWord(softwareWork.getName()));
        }
        if (softwareWork.getId() == null) {
            return this.softwareWorkMapper.insert(softwareWork) > 0;
        } else {
            return this.softwareWorkMapper.updateByPrimaryKeySelective(softwareWork) > 0;
        }
    }

    public boolean delete(SoftwareWork softwareWork) {
        if (softwareWork != null && softwareWork.getId() != null) {
            softwareWork.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
            return this.softwareWorkMapper.updateByPrimaryKeySelective(softwareWork) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        SoftwareWork softwareWork = new SoftwareWork();
        softwareWork.setId(id);
        softwareWork.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
        return this.softwareWorkMapper.updateByPrimaryKeySelective(softwareWork) > 0;
    }

    public boolean setStatus(SoftwareWork softwareWork) {
        if (softwareWork != null && softwareWork.getId() != null) {
            return this.softwareWorkMapper.updateByPrimaryKeySelective(softwareWork) > 0;
        }
        return false;
    }

    public boolean setStatus(int id, int status) {
        SoftwareWork softwareWork = new SoftwareWork();
        softwareWork.setId(id);
        softwareWork.setStatus(status);
        return this.softwareWorkMapper.updateByPrimaryKeySelective(softwareWork) > 0;
    }

    public SoftwareWork getByPrimaryKey(int id) {
        return this.softwareWorkMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(SoftwareWork.class, " and id=" + id, false));
    }

    public List<SoftwareWork> getAll(SoftwareWork softwareWork) {
        if (softwareWork.getPage() != null && softwareWork.getRows() != null) {
            PageHelper.startPage(softwareWork.getPage(), softwareWork.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (softwareWork.getName() != null) {
            builder.append(" and name like '%").append(softwareWork.getName()).append("%' ");
        }
        if (softwareWork.getAuthor() != null) {
            builder.append(" and author like '%").append(softwareWork.getAuthor()).append("%' ");
        }
        if (softwareWork.getStatus() != null) {
            builder.append(" and status=").append(softwareWork.getStatus());
        }
        if (softwareWork.getCreatedId() != null) {
            builder.append(" and created_id=").append(softwareWork.getCreatedId());
        }
        return softwareWorkMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                SoftwareWork.class, builder.toString(), false
        ));
    }
}