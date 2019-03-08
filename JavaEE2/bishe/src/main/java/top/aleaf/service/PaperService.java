package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.PaperMapper;
import top.aleaf.model.Paper;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.GeneralExample;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Service
public class PaperService {
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private SensitiveFilterService filterService;

    public boolean save(Paper paper) {
        if (paper.getName() != null) {
            paper.setName(filterService.filterSensitiveWord(paper.getName()));
        }
        if (paper.getId() == null) {
            return this.paperMapper.insert(paper) > 0;
        } else {
            return this.paperMapper.updateByPrimaryKey(paper) > 0;
        }
    }

    public boolean delete(Paper paper) {
        if (paper != null && paper.getId() != null) {
            paper.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
            return this.paperMapper.updateByPrimaryKeySelective(paper) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        Paper paper = new Paper();
        paper.setId(id);
        paper.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
        return this.paperMapper.updateByPrimaryKeySelective(paper) > 0;
    }

    public boolean setStatus(Paper paper) {
        if (paper != null && paper.getId() != null) {
            return this.paperMapper.updateByPrimaryKeySelective(paper) > 0;
        }
        return false;
    }

    public boolean setStatus(int id, int status) {
        Paper paper = new Paper();
        paper.setId(id);
        paper.setStatus(status);
        return this.paperMapper.updateByPrimaryKeySelective(paper) > 0;
    }

    public Paper getByPrimaryKey(int id) {
        return this.paperMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(Paper.class, " and id=" + id, false));
    }

    public List<Paper> getAll(Paper paper) {
        if (paper.getPage() != null && paper.getRows() != null) {
            PageHelper.startPage(paper.getPage(), paper.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (paper.getName() != null) {
            builder.append(" and name like '%").append(paper.getName()).append("%' ");
        }
        if (paper.getFirstAuthor() != null) {
            builder.append(" and first_author=").append(paper.getFirstAuthor()).append(" ");
        }
        if (paper.getStatus() != null) {
            builder.append(" and status=").append(paper.getStatus());
        }
        if (paper.getCreatedId() != null) {
            builder.append(" and created_id=").append(paper.getCreatedId());
        }
        return paperMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                Paper.class, builder.toString(), false
        ));
    }
}