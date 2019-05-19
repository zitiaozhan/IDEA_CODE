/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MakeStack
 * Author:   郭新晔
 * Date:     2018/9/9 0009 9:33
 * Description: 用队列模拟栈
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.no_sort_practice;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 〈一句话功能简述〉<br>
 * 〈用队列模拟栈〉
 *
 * @author 郭新晔
 * @create 2018/9/9 0009
 * @since 1.0.0
 */
public class StackQueneConvert {

}

/**
 * 用两个栈模拟一个队列
 */
class MySQueue {
    private Stack<Integer> pushStack;
    private Stack<Integer> popStack;

    public MySQueue() {
        pushStack = new Stack();
        popStack = new Stack();
    }

    /**
     * 入队列
     *
     * @param num
     */
    public void push(int num) {
        pushStack.push(num);
        dao();
    }

    /**
     * 出队列
     *
     * @return
     */
    public int pop() {
        if (popStack.isEmpty() && pushStack.isEmpty()) {
            throw new IndexOutOfBoundsException("this queue is empty");
        }
        dao();
        return popStack.pop();
    }

    /**
     * 只弹不出
     *
     * @return
     */
    public int peek() {
        if (popStack.isEmpty() && pushStack.isEmpty()) {
            throw new IndexOutOfBoundsException("this queue is empty");
        }
        dao();
        return popStack.peek();
    }

    /**
     * 倒元素
     */
    private void dao() {
        if (popStack.isEmpty()) {
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
    }
}

/**
 * 用两个队列模拟一个栈
 */
class MyQStack {
    private Queue<Integer> queue;
    private Queue<Integer> help;

    public MyQStack() {
        this.queue = new LinkedList<>();
        this.help = new LinkedList<>();
    }

    public void push(int num) {
        this.queue.add(num);
    }

    public int pop() {
        if (this.queue.isEmpty()) {
            throw new IndexOutOfBoundsException("this stack is empty");
        }
        while (this.queue.size() > 1) {
            this.help.add(this.queue.poll());
        }
        int res = this.queue.poll();
        swap();
        return res;
    }

    public int peek() {
        if (this.queue.isEmpty()) {
            throw new IndexOutOfBoundsException("this stack is empty");
        }
        while (this.queue.size() > 1) {
            this.help.add(this.queue.poll());
        }
        int res = this.queue.poll();
        this.help.add(res);
        swap();
        return res;
    }


    private void swap() {
        Queue temp = this.queue;
        this.queue = this.help;
        this.help = temp;
    }
}