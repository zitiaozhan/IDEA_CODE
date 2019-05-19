/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Test1107
 * Author:   郭新晔
 * Date:     2018/11/7 0007 20:06
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
 * @create 2018/11/7 0007
 * @since 1.0.0
 */
public class Test1107 {
    //不借助任何数据结构用递归实现栈的翻转

    /**
     * 翻转栈的实现
     *
     * @param stack 目标栈
     */
    public static void reverse(Stack<Integer> stack) {
        //递归的结束条件
        if (stack.isEmpty()) {
            return;
        }
        //得到栈底元素并移除该元素
        int num = getStackBottom(stack);
        //得到栈底元素的顺序与栈的出栈顺序相反
        reverse(stack);
        //将栈底元素依次压栈，压栈的顺序刚好与原来顺序相反
        stack.push(num);
    }

    /**
     * 取出并移除当前栈中栈底元素
     *
     * @param stack 目标栈
     * @return 返回当前栈底元素
     */
    public static int getStackBottom(Stack<Integer> stack) {
        //出栈栈中栈顶元素
        int res = stack.pop();
        //若栈已空，表示当前取出元素为栈底元素，返回栈底元素
        if (stack.isEmpty()) {
            return res;
        } else {
            //得到栈底元素，并将其传递给递归的上一层
            int last = getStackBottom(stack);
            //依次入栈非栈底元素
            stack.push(res);
            return last;
        }
    }

    public static int smallSum(int[] nums, int l, int r) {
        if (l >= r) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return smallSum(nums, l, mid) + smallSum(nums, mid + 1, r) + merge(nums, l, mid, r);
    }

    public static int merge(int[] nums, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int left = l, right = mid + 1, index = 0, res = 0;
        while (left <= mid && right <= r) {
            res += nums[left] < nums[right] ? (r - right + 1) * nums[left] : 0;
            help[index++] = nums[left] < nums[right] ? nums[left++] : nums[right++];
        }
        while (left <= mid) {
            help[index++] = nums[left++];
        }
        while (right <= r) {
            help[index++] = nums[right++];
        }
        for (index = 0; index < help.length; index++) {
            nums[l + index] = help[index];
        }
        return res;
    }

    public static void main(String[] args) {
        /*Stack<Integer> numStack = new Stack<>();
        numStack.push(1);
        numStack.push(2);
        numStack.push(3);
        reverse(numStack);
        while (!numStack.isEmpty()) {
            System.out.println(numStack.pop());
        }*/
        System.out.println(smallSum(new int[]{1, 3, 5, 2, 4, 6}, 0, 5));
    }
}