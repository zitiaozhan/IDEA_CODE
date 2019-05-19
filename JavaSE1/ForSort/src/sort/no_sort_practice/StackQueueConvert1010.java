/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: StackQueueConvert1010
 * Author:   郭新晔
 * Date:     2018/10/10 0010 22:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.no_sort_practice;

import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/10/10 0010
 * @since 1.0.0
 */
public class StackQueueConvert1010 {
    public static void main(String[] args) {
        MyQStack915 myStack = new MyQStack915();
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());

        System.out.println("==========================================");

        MyQueue1010 myQueue = new MyQueue1010();
        myQueue.push(2);
        myQueue.push(3);
        myQueue.push(4);
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
    }

}

class MyQueue1010 {
    Stack<Integer> pusher = new Stack<>();
    Stack<Integer> poper = new Stack<>();

    void push(int num) {
        pusher.push(num);
    }

    int pop() {
        if (pusher.isEmpty() && poper.isEmpty()) {
            throw new IndexOutOfBoundsException("OOM");
        }
        dao();
        return poper.pop();
    }

    void dao() {
        if (!poper.isEmpty()) {
            return;
        }
        while (!pusher.isEmpty()) {
            poper.push(pusher.pop());
        }
    }
}

class MyStack1010 {
    Queue<Integer> stack = new LinkedList<>();
    Queue<Integer> helper = new LinkedList<>();

    void push(int num) {
        stack.add(num);
    }

    int pop() {
        if (stack.isEmpty()) {
            throw new IndexOutOfBoundsException("OOM");
        }
        while (stack.size() > 1) {
            helper.add(stack.poll());
        }
        int x = stack.poll();
        change();
        return x;
    }

    void change() {
        Queue<Integer> temp = stack;
        stack = helper;
        helper = temp;
    }
}