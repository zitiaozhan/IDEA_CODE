package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.StringsMapper;
import top.aleaf.model.Strings;
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
public class StringsService {
    @Resource
    private StringsMapper stringsMapper;

    public boolean save(Strings strings) {
        if (strings.getId() == null) {
            return this.stringsMapper.insert(strings) > 0;
        } else {
            return this.stringsMapper.updateByPrimaryKeySelective(strings) > 0;
        }
    }

    public boolean delete(Strings strings) {
        if (strings != null && strings.getId() != null) {
            strings.setStatus(1);
            return this.stringsMapper.updateByPrimaryKeySelective(strings) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        Strings strings = new Strings();
        strings.setId(id);
        strings.setStatus(1);
        return this.stringsMapper.updateByPrimaryKeySelective(strings) > 0;
    }

    public Strings getByPrimaryKey(int id) {
        return this.stringsMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(Strings.class, " and id=" + id, true));
    }

    public Strings getByEntityType(int entityType) {
        return this.stringsMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(Strings.class, " and entity_type=" + entityType, true));
    }

    public List<Strings> getAll(Strings strings) {
        if (strings.getPage() != null && strings.getRows() != null) {
            PageHelper.startPage(strings.getPage(), strings.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (strings.getEntityField() != null) {
            builder.append(" and entity_field like '%").append(strings.getEntityField()).append("%' ");
        }
        if (null != strings.getEntityType()) {
            builder.append(" and entity_type=").append(strings.getEntityType()).append(" ");
        }
        return stringsMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                Strings.class, builder.toString(), true
        ));
    }
}