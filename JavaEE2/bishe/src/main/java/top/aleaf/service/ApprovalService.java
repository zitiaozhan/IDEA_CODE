package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.ApprovalMapper;
import top.aleaf.model.Approval;
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
public class ApprovalService {
    @Resource
    private ApprovalMapper approvalMapper;

    public boolean save(Approval approval) {
        if (approval.getId() == null) {
            return this.approvalMapper.insert(approval) > 0;
        } else {
            return this.approvalMapper.updateByPrimaryKeySelective(approval) > 0;
        }
    }

    public boolean delete(Approval approval) {
        if (approval != null && approval.getId() != null) {
            approval.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
            return this.approvalMapper.updateByPrimaryKeySelective(approval) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        Approval approval = new Approval();
        approval.setId(id);
        approval.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
        return this.approvalMapper.updateByPrimaryKeySelective(approval) > 0;
    }

    public boolean setStatus(Approval approval) {
        return approval != null && approval.getId() != null && this.approvalMapper.updateByPrimaryKeySelective(approval) > 0;
    }

    public boolean setStatus(int id, int status) {
        Approval approval = new Approval();
        approval.setId(id);
        approval.setStatus(status);
        return this.approvalMapper.updateByPrimaryKeySelective(approval) > 0;
    }

    public Approval getByPrimaryKey(int id) {
        return this.approvalMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(Approval.class, " and id=" + id, true));
    }

    public List<Approval> getByUserId(int userId) {
        return this.approvalMapper.selectByExample(
                GeneralExample.getBaseAndConditionExample(Approval.class, " and user_id=" + userId, true));
    }

    public List<Approval> getAll(Approval approval) {
        if (approval.getPage() != null && approval.getRows() != null) {
            PageHelper.startPage(approval.getPage(), approval.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (approval.getUserId() != null) {
            builder.append(" and user_id=").append(approval.getUserId()).append(" ");
        }
        if (approval.getEntityId() != null && approval.getEntityType() != null) {
            builder.append(" and entity_id=").append(approval.getEntityId()).append(" ");
            builder.append(" and entity_type='").append(approval.getEntityType()).append("' ");
        }
        return approvalMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                Approval.class, builder.toString(), true
        ));
    }

    /**
     * 统计审核数目前limit名的管理
     *
     * @param limit 限制条数
     * @return 结果
     */
    public List<Approval> getUserByApprovalNum(int limit) {
        limit = limit < 1 ? 10 : limit;
        return this.approvalMapper.selectUserByApprovalNum(limit);
    }

    /**
     * 审核项目数量
     *
     * @return 结果
     */
    public int getAllCount() {
        return this.approvalMapper.selectCount(new Approval().setStatus(null));
    }
}