/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: StackQueueConvert915
 * Author:   郭新晔
 * Date:     2018/9/15 0015 21:36
 * Description: 队列与栈相互转化
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
 * 〈队列与栈相互转化〉
 *
 * @author 郭新晔
 * @create 2018/9/15 0015
 * @since 1.0.0
 */
public class StackQueueConvert915 {
    public static void main(String[] args) {
        MyQStack915 myStack = new MyQStack915();
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());
        System.out.println(myStack.pop());

        System.out.println("==========================================");

        MySQueue915 myQueue=new MySQueue915();
        myQueue.add(2);
        myQueue.add(3);
        myQueue.add(4);
        System.out.println(myQueue.poll());
        System.out.println(myQueue.poll());
        System.out.println(myQueue.poll());
    }
}

class MyQStack915{
    private Queue<Integer> stack=new LinkedList<>();
    private Queue<Integer> helper=new LinkedList<>();

    public void push(int value){
        stack.add(value);
    }

    public int pop(){
        if (stack.isEmpty()){
            throw new IndexOutOfBoundsException("The stack is empty");
        }
        while (stack.size()!=1){
            helper.add(stack.poll());
        }
        int res=stack.poll();
        swap();
        return res;
    }

    public int peek(){
        if (stack.isEmpty()){
            throw new IndexOutOfBoundsException("The stack is empty");
        }
        while (stack.size()!=1){
            helper.add(stack.poll());
        }
        int res=stack.poll();
        helper.add(res);
        return res;
    }

    private void swap(){
        Queue temp=stack;
        stack=helper;
        helper=temp;
    }
}

class MySQueue915{
    private Stack<Integer> pusher=new Stack<>();
    private Stack<Integer> poper=new Stack<>();

    public void add(int value){
        pusher.push(value);
    }

    public int poll(){
        if (pusher.isEmpty()&&poper.isEmpty()){
            throw new IndexOutOfBoundsException("The Queue is empty");
        }
        dao();
        return poper.pop();
    }

    public int peek(){
        if (pusher.isEmpty()||poper.isEmpty()){
            throw new IndexOutOfBoundsException("The Queue is empty");
        }
        dao();
        return poper.peek();
    }

    private void dao(){
        if (!poper.isEmpty()){
            return;
        }
        while (!pusher.isEmpty()){
            poper.push(pusher.pop());
        }
    }
}