package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.PatentMapper;
import top.aleaf.model.Patent;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.GeneralExample;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Service
public class PatentService {
    @Autowired
    private PatentMapper patentMapper;
    @Autowired
    private SensitiveFilterService filterService;

    public boolean save(Patent patent) {
        if (patent.getName() != null) {
            patent.setName(filterService.filterSensitiveWord(patent.getName()));
        }
        if (patent.getId() == null) {
            return this.patentMapper.insert(patent) > 0;
        } else {
            return this.patentMapper.updateByPrimaryKeySelective(patent) > 0;
        }
    }

    public boolean delete(Patent patent) {
        if (patent != null && patent.getId() != null) {
            patent.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
            return this.patentMapper.updateByPrimaryKeySelective(patent) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        Patent patent = new Patent();
        patent.setId(id);
        patent.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
        return this.patentMapper.updateByPrimaryKeySelective(patent) > 0;
    }

    public boolean setStatus(Patent patent) {
        if (patent != null && patent.getId() != null) {
            return this.patentMapper.updateByPrimaryKeySelective(patent) > 0;
        }
        return false;
    }

    public boolean setStatus(int id, int status) {
        Patent patent = new Patent();
        patent.setId(id);
        patent.setStatus(status);
        return this.patentMapper.updateByPrimaryKeySelective(patent) > 0;
    }

    public Patent getByPrimaryKey(int id) {
        return this.patentMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(Patent.class, " and id=" + id, false));
    }

    public List<Patent> getAll(Patent patent) {
        if (patent.getPage() != null && patent.getRows() != null) {
            PageHelper.startPage(patent.getPage(), patent.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (patent.getName() != null) {
            builder.append(" and name like '%").append(patent.getName()).append("%' ");
        }
        if (patent.getAuthor() != null) {
            builder.append(" and author=").append(patent.getAuthor()).append(" ");
        }
        if (patent.getStatus() != null) {
            builder.append(" and status=").append(patent.getStatus());
        }
        if (patent.getCreatedId() != null) {
            builder.append(" and created_id=").append(patent.getCreatedId());
        }
        return patentMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                Patent.class, builder.toString(), false
        ));
    }
}