package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.mapper.LectureMapper;
import top.aleaf.model.Lecture;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.GeneralExample;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Service
public class LectureService {
    @Autowired
    private LectureMapper lectureMapper;
    @Autowired
    private SensitiveFilterService filterService;

    public boolean save(Lecture lecture) {
        if (lecture.getName() != null) {
            lecture.setName(filterService.filterSensitiveWord(lecture.getName()));
        }
        if (lecture.getId() == null) {
            return this.lectureMapper.insert(lecture) > 0;
        } else {
            return this.lectureMapper.updateByPrimaryKeySelective(lecture) > 0;
        }
    }

    public boolean delete(Lecture lecture) {
        if (lecture != null && lecture.getId() != null) {
            lecture.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
            return this.lectureMapper.updateByPrimaryKeySelective(lecture) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        Lecture lecture = new Lecture();
        lecture.setId(id);
        lecture.setStatus(ConstantUtil.PROJECT_STATUS_DELETE);
        return this.lectureMapper.updateByPrimaryKeySelective(lecture) > 0;
    }

    public boolean setStatus(Lecture lecture) {
        if (lecture != null && lecture.getId() != null) {
            return this.lectureMapper.updateByPrimaryKeySelective(lecture) > 0;
        }
        return false;
    }

    public boolean setStatus(int id, int status) {
        Lecture lecture = new Lecture();
        lecture.setId(id);
        lecture.setStatus(status);
        return this.lectureMapper.updateByPrimaryKeySelective(lecture) > 0;
    }

    public Lecture getByPrimaryKey(int id) {
        return this.lectureMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(Lecture.class, " and id=" + id, false));
    }

    public List<Lecture> getAll(Lecture lecture) {
        if (lecture.getPage() != null && lecture.getRows() != null) {
            PageHelper.startPage(lecture.getPage(), lecture.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (lecture.getName() != null) {
            builder.append(" and name like '%").append(lecture.getName()).append("%' ");
        }
        if (lecture.getRapporteur() != null) {
            builder.append(" and rapporteur=").append(lecture.getRapporteur()).append(" ");
        }
        if (lecture.getStatus() != null) {
            builder.append(" and status=").append(lecture.getStatus());
        }
        if (lecture.getCreatedId() != null) {
            builder.append(" and created_id=").append(lecture.getCreatedId());
        }
        return lectureMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                Lecture.class, builder.toString(), false
        ));
    }
}