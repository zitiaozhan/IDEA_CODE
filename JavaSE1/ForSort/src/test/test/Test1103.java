/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Test1103
 * Author:   郭新晔
 * Date:     2018/11/3 0003 22:06
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package test.test;

import java.util.Stack;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/11/3 0003
 * @since 1.0.0
 */
public class Test1103 {
    public static void reverseStack(Stack<Integer> numStack) {
        if (numStack.isEmpty()) {
            return;
        }
        int i = getLastNumber(numStack);
        reverseStack(numStack);
        numStack.push(i);
    }

    public static int getLastNumber(Stack<Integer> numStack) {
        int res = numStack.pop();
        if (numStack.isEmpty()) {
            return res;
        } else {
            int last = getLastNumber(numStack);
            numStack.push(res);
            return last;
        }
    }
}