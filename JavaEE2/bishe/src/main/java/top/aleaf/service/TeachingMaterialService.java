package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.TeachingMaterialMapper;
import top.aleaf.model.TeachingMaterial;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.GeneralExample;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Service
public class TeachingMaterialService {
    @Autowired
    private TeachingMaterialMapper teachingMaterialMapper;
    @Autowired
    private SensitiveFilterService filterService;

    public boolean save(TeachingMaterial teachingMaterial) {
        if (teachingMaterial.getName() != null) {
            teachingMaterial.setName(filterService.filterSensitiveWord(teachingMaterial.getName()));
        }
        if (teachingMaterial.getId() == null) {
            return this.teachingMaterialMapper.insert(teachingMaterial) > 0;
        } else {
            return this.teachingMaterialMapper.updateByPrimaryKeySelective(teachingMaterial) > 0;
        }
    }

    public boolean delete(TeachingMaterial teachingMaterial) {
        if (teachingMaterial != null && teachingMaterial.getId() != null) {
            teachingMaterial.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
            return this.teachingMaterialMapper.updateByPrimaryKeySelective(teachingMaterial) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        TeachingMaterial teachingMaterial = new TeachingMaterial();
        teachingMaterial.setId(id);
        teachingMaterial.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
        return this.teachingMaterialMapper.updateByPrimaryKeySelective(teachingMaterial) > 0;
    }

    public boolean setStatus(TeachingMaterial teachingMaterial) {
        if (teachingMaterial != null && teachingMaterial.getId() != null) {
            return this.teachingMaterialMapper.updateByPrimaryKeySelective(teachingMaterial) > 0;
        }
        return false;
    }

    public boolean setStatus(int id, int status) {
        TeachingMaterial teachingMaterial = new TeachingMaterial();
        teachingMaterial.setId(id);
        teachingMaterial.setStatus(status);
        return this.teachingMaterialMapper.updateByPrimaryKeySelective(teachingMaterial) > 0;
    }

    public TeachingMaterial getByPrimaryKey(int id) {
        return this.teachingMaterialMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(TeachingMaterial.class, " and id=" + id, false));
    }

    public List<TeachingMaterial> getAll(TeachingMaterial teachingMaterial) {
        if (teachingMaterial.getPage() != null && teachingMaterial.getRows() != null) {
            PageHelper.startPage(teachingMaterial.getPage(), teachingMaterial.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (teachingMaterial.getName() != null) {
            builder.append(" and name like '%").append(teachingMaterial.getName()).append("%' ");
        }
        if (teachingMaterial.getAuthor() != null) {
            builder.append(" and author=").append(teachingMaterial.getAuthor()).append(" ");
        }
        if (teachingMaterial.getStatus() != null) {
            builder.append(" and status=").append(teachingMaterial.getStatus());
        }
        if (teachingMaterial.getCreatedId() != null) {
            builder.append(" and created_id=").append(teachingMaterial.getCreatedId());
        }
        return teachingMaterialMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                TeachingMaterial.class, builder.toString(), false
        ));
    }
}