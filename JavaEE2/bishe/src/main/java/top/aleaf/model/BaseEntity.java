package top.aleaf.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * 基础信息
 *
 * @author 郭新晔
 */
@Component
public class BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

    //辅助查询
    @Transient
    private Boolean scoreNotNull = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Boolean getScoreNotNull() {
        return scoreNotNull;
    }

    public void setScoreNotNull(Boolean scoreNotNull) {
        this.scoreNotNull = scoreNotNull;
    }
}
