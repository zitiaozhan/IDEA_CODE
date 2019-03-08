/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: GeneralExample
 * Author:   郭新晔
 * Date:     2019/1/18 0018 15:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.util;

import tk.mybatis.mapper.entity.Example;

/**
 * 〈〉
 *
 * @create 2019/1/18 0018
 */
public class GeneralExample {
    /**
     * 基础查询Example
     *
     * @param clazz 类型
     * @return
     */
    public static Example getBaseExample(Class clazz) {
        Example example = new Example(clazz);
        example.setOrderByClause("id desc");
        example.createCriteria().andCondition(" status!=1 ");
        return example;
    }

    /**
     * 基础查询+指定条件查询Example
     * @param clazz         类型
     * @param conditionStr  指定条件
     * @param flag          是否带有status!=1
     * @return
     */
    public static Example getBaseAndConditionExample(Class clazz,String conditionStr,boolean flag) {
        Example example = new Example(clazz);
        example.setOrderByClause("id desc");
        StringBuilder condition=new StringBuilder("");
        if (flag){
            condition.append(" status!=1 ");
        }
        condition.append(" ").append(conditionStr).append(" ");
        example.createCriteria().andCondition(condition.toString());
        return example;
    }

    /**
     * 指定排序方式的查询Example
     * @param clazz     类型
     * @param orderStr  指定排序方式
     * @return
     */
    public static Example getOrderExample(Class clazz, String orderStr) {
        Example example = new Example(clazz);
        example.setOrderByClause(orderStr);
        return example;
    }

    /**
     * 指定条件的查询Example
     * @param clazz         类型
     * @param conditionStr  指定条件
     * @return
     */
    public static Example getConditionExample(Class clazz, String conditionStr) {
        Example example = new Example(clazz);
        example.createCriteria().andCondition(" " + conditionStr + " ");
        return example;
    }

    /**
     * 指定排序方式和指定条件的查询Example
     * @param clazz         类型
     * @param orderStr      指定排序方式
     * @param conditionStr  指定条件
     * @return
     */
    public static Example getConditionExample(Class clazz, String orderStr, String conditionStr) {
        Example example = new Example(clazz);
        example.setOrderByClause(orderStr);
        example.createCriteria().andCondition(" " + conditionStr + " ");
        return example;
    }

    /**
     * 空的Example
     *
     * @param clazz 类型
     * @return
     */
    public static Example getEmptyExample(Class clazz) {
        Example example = new Example(clazz);
        return example;
    }
}