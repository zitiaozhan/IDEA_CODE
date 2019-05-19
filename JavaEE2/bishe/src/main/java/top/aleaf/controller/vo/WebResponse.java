package top.aleaf.controller.vo;

import lombok.Data;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/5/6 0006
 * Author:   郭新晔
 */
@Data
public class WebResponse<T> {
    private T data;
    private boolean success;
    private Exception exception;
    private String msg;
    private boolean isManager;
}