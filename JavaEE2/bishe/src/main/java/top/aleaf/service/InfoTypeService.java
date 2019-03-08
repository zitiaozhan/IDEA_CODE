package top.aleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.InfoTypeMapper;
import top.aleaf.model.InfoType;
import top.aleaf.utils.GeneralExample;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Service
public class InfoTypeService {
    @Autowired
    private InfoTypeMapper infoTypeMapper;

    public InfoType getByPrimaryKey(int id) {
        return this.infoTypeMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(InfoType.class, " and id=" + id, false));
    }

    public InfoType getByEntityType(String entityType) {
        return this.infoTypeMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(InfoType.class, " and entity_type='" + entityType + "' ", false));
    }

    public List<InfoType> getAll() {
        return this.infoTypeMapper.selectAll();
    }

}