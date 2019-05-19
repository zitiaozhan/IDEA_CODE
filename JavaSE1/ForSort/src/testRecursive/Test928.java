/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Test928
 * Author:   郭新晔
 * Date:     2018/9/28 0028 23:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package testRecursive;

import java.util.Stack;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/9/28 0028
 * @since 1.0.0
 */
public class Test928 {
    /**
     * 逆序栈中元素
     * @param stack
     */
    void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int i = get(stack);
        reverse(stack);
        stack.push(i);
    }

    /**
     * 移除栈底元素并返回
     *
     * @param stack
     * @return
     */
    int get(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int last = get(stack);
            stack.push(result);
            return last;
        }
    }
}