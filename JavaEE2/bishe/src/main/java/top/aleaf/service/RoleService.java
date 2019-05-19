package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.RoleMapper;
import top.aleaf.model.Role;
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
public class RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private SensitiveFilterService filterService;

    public boolean save(Role role) {
        if (role.getName()!=null){
            role.setName(filterService.filterSensitiveWord(role.getName()));
        }
        if (role.getDetail()!=null){
            role.setDetail(filterService.filterSensitiveWord(role.getDetail()));
        }
        if (role.getId() == null) {
            return this.roleMapper.insert(role) > 0;
        } else {
            return this.roleMapper.updateByPrimaryKeySelective(role) > 0;
        }
    }

    public boolean delete(Role role) {
        if (role != null && role.getId() != null) {
            role.setStatus(1);
            return this.roleMapper.updateByPrimaryKeySelective(role) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        Role role = new Role();
        role.setId(id);
        role.setStatus(1);
        return this.roleMapper.updateByPrimaryKeySelective(role) > 0;
    }

    public Role getByPrimaryKey(int id) {
        return this.roleMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(Role.class, " and id=" + id, true));
    }

    public List<Role> getAll(Role role) {
        if (role.getPage() != null && role.getRows() != null) {
            PageHelper.startPage(role.getPage(), role.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (role.getName() != null) {
            builder.append(" and name like '%").append(role.getName()).append("%' ");
        }
        return roleMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                Role.class, builder.toString(), true
        ));
    }
}