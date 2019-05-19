/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ArrayToQueueStack915
 * Author:   郭新晔
 * Date:     2018/9/15 0015 21:19
 * Description: 数组实现队列与栈
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.no_sort_practice;

/**
 * 〈一句话功能简述〉<br>
 * 〈数组实现队列与栈〉
 *
 * @author 郭新晔
 * @create 2018/9/15 0015
 * @since 1.0.0
 */
public class ArrayToQueueStack915 {
    public static void main(String[] args) {
        MyStack915 myStack = new MyStack915(3);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());

        System.out.println("==========================================");

        MyQueue915 myQueue = new MyQueue915(3);
        myQueue.add(2);
        myQueue.add(3);
        myQueue.add(4);
        System.out.println(myQueue.poll());
        System.out.println(myQueue.poll());
        System.out.println(myQueue.poll());
    }
}

class MyStack915 {
    private int[] stack;
    private int index = 0;

    public MyStack915(int initSize) {
        stack = new int[initSize];
    }

    public void push(int value) {
        if (index == stack.length) {
            throw new IndexOutOfBoundsException("The stack full");
        }
        stack[index++] = value;
    }

    public int pop() {
        if (index == 0) {
            throw new IndexOutOfBoundsException("The stack empty");
        }
        return stack[--index];
    }

    public int peek() {
        if (index == 0) {
            throw new IndexOutOfBoundsException("The stack empty");
        }
        return stack[index - 1];
    }
}

class MyQueue915 {
    private int[] queue;
    private int l, r;
    private int size;

    public MyQueue915(int initSize) {
        queue = new int[initSize];
        size = 0;
        l = 0;
        r = 0;
    }

    public void add(int value) {
        if (size == queue.length) {
            throw new IndexOutOfBoundsException("The queue is full");
        }
        this.queue[r] = value;
        r = r == queue.length ? 0 : r + 1;
        size++;
    }

    public int poll() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("The queue is empty");
        }
        int temp = queue[l];
        l = l == queue.length ? 0 : l + 1;
        return temp;
    }

    public int peek() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("The queue is empty");
        }
        return queue[l];
    }
}