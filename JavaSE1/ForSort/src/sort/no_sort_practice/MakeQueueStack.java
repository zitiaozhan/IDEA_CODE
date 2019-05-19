/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MakeQueueStack
 * Author:   郭新晔
 * Date:     2018/9/8 0008 16:29
 * Description: 数组模拟栈与队列
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.no_sort_practice;

/**
 * 〈一句话功能简述〉<br>
 * 〈数组模拟栈与队列〉
 *
 * @author 郭新晔
 * @create 2018/9/8 0008
 * @since 1.0.0
 */
public class MakeQueueStack {
    public static void main(String[] args) {
        /*MyStack myStack = new MyStack(3);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());*/

        /*MyQueue myQueue=new MyQueue(3);
        myQueue.push(2);
        myQueue.push(3);
        myQueue.push(4);
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());*/
    }
}

/**
 * 用数组模拟栈
 */
class MyStack {
    private int[] stack;
    private int index;

    public MyStack(int initSize) {
        if (initSize < 0) {
            throw new IllegalArgumentException("初始化大小不能为负!");
        }
        this.stack = new int[initSize];
    }

    /**
     * 入栈
     *
     * @param num
     */
    public void push(int num) {
        if (index == stack.length) {
            throw new ArrayIndexOutOfBoundsException("超出范围!");
        }
        this.stack[index++] = num;
    }

    /**
     * 出栈
     *
     * @return
     */
    public int pop() {
        if (index == 0) {
            throw new ArrayIndexOutOfBoundsException("栈已空!");
        }
        return this.stack[--index];
    }
}

/**
 * 数组模拟队列
 */
class MyQueue {
    private int[] queue;
    private int size;
    private int start;
    private int end;

    public MyQueue(int initSize) {
        if (initSize < 0) {
            throw new IllegalArgumentException("初始化大小不能小于0");
        }
        this.queue = new int[initSize];
        start = 0;
        end = 0;
        size = 0;
    }

    /**
     * 加入队列
     *
     * @param num
     */
    public void push(int num) {
        if (size == this.queue.length) {
            throw new ArrayIndexOutOfBoundsException("this queue is full");
        }
        size++;
        queue[start] = num;
        start = start == queue.length - 1 ? 0 : start + 1;
    }

    /**
     * 弹出队列
     * @return
     */
    public int pop() {
        if (size == 0) {
            throw new ArrayIndexOutOfBoundsException("this queue is empty");
        }
        size--;
        int temp = end;
        end = end == queue.length - 1 ? 0 : end + 1;
        return queue[temp];
    }
}