/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: GoodsAction
 * Author:   郭新晔
 * Date:     2018/5/8 0008 20:11
 * Description: MVC---controller
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.action;

import com.bean.Goods;
import com.opensymphony.xwork2.ActionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈MVC---controller〉
 *
 * @author 郭新晔
 * @create 2018/5/8 0008
 * @since 1.0.0
 */
public class GoodsAction {
    private Integer index;
    private Goods goods;
    private String path;
    private Map<String, Object> session = ActionContext.getContext().getSession();

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Struts2的执行方法返回值都是String,而且都没有方法参数
     *
     * @return 返回Struts结果字符串(自定义)
     */
    public String save() {
        List<Goods> goodsList = session.get("goodsList") == null ? new ArrayList<>() : (List<Goods>) session.get("goodsList");
        if (goodsList.add(this.goods)) {
            session.put("goodsList", goodsList);
            path = "findAll_Goods";
            return "ok";
        }
        return "fail";
    }

    public String remove() {
        List<Goods> goodsList = (List<Goods>) session.get("goodsList");
        if (goodsList != null && goodsList.size() > 0 && this.index != null && this.index > -1) {
            goodsList.remove(this.index);
            session.put("goodsList", goodsList);
            path = "findAll_Goods";
            return "ok";
        }
        return "fail";
    }

    public String update() {
        List<Goods> goodsList = (List<Goods>) session.get("goodsList");
        if (goodsList != null && goodsList.size() > 0) {
            goodsList.set(this.goods.getId(), goods);
            session.put("goodsList", goodsList);
            path = "findAll_Goods";
            return "ok";
        }
        return "fail";
    }

    public String findById() {
        List<Goods> goodsList = (List<Goods>) session.get("goodsList");
        if (goodsList != null && goodsList.size() > 0 && this.index != null && this.index > -1) {
            this.goods = goodsList.get(this.index);
            this.goods.setId(this.index);
            session.put("goods", this.goods);
            path = "update.jsp";
            return "ok";
        }
        return "fail";
    }

    public String findAll() {
        List<Goods> goodsList = (List<Goods>) session.get("goodsList");
        if (goodsList != null && goodsList.size() > 0) {
            path = "list.jsp";
            return "ok";
        }
        return "fail";
    }
}