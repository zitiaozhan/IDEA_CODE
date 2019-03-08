package top.aleaf.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aleaf.dao.SummaryDAO;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageBean {
    private Integer nowPage = 1;
    private Integer lastPage = 1;
    private Integer nextPage = 1;
    private Integer pageSize = 5;
    private Integer pageNum = 1;
    private List<?> pageData = null;

    @Autowired
    private SummaryDAO summaryDAO;

    public Integer getNowPage() {
        return nowPage < 1 ? 1 : nowPage > pageNum ? pageNum : nowPage;
    }

    /**
     * 设置当前页之前必须先设置好总的页数,否则无法比较当前页与总页数的大小
     *
     * @param nowPage
     */
    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage == null || nowPage < 1 || nowPage > this.pageNum ? this.nowPage : nowPage;
    }

    public Integer getPageSize() {
        return pageSize < 1 ? 1 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize == null || pageSize < 1 ? this.pageSize : pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer count) {
        int pageNum = count > 0 ? count % pageSize == 0 ? (count / pageSize) : (count / pageSize + 1) : 1;
        this.pageNum = pageNum < 1 ? this.pageNum : pageNum;
    }

    public List<?> getPageData() {
        return pageData;
    }

    public void setPageData(List<?> pageData) {
        this.pageData = pageData == null ? new ArrayList<Object>() : pageData;
    }

    public Integer getLastPage() {
        return nowPage-1;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public Integer getNextPage() {
        return nowPage+1;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "nowPage=" + nowPage +
                ", lastPage=" + lastPage +
                ", nextPage=" + nextPage +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", pageData=" + pageData +
                ", summaryDAO=" + summaryDAO +
                '}';
    }
}
