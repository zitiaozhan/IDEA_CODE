/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PrintTree913
 * Author:   郭新晔
 * Date:     2018/9/13 0013 20:19
 * Description: 二叉树遍历
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 〈一句话功能简述〉<br>
 * 〈二叉树遍历〉
 *
 * @author 郭新晔
 * @create 2018/9/13 0013
 * @since 1.0.0
 */
public class PrintTree913 {

    /**
     * 递归形式先序遍历
     *
     * @param head
     */
    static void prePrintTree(Node head) {
        if (head == null) {
            return;
        }
        System.out.println("Node : " + head.value);
        prePrintTree(head.leftChild);
        prePrintTree(head.rightChild);
    }

    /**
     * 非递归形式的先序遍历
     *
     * @param head
     */
    static void prePrintTreeNo(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> nodeStack = new Stack<>();
        nodeStack.push(head);
        while (!nodeStack.isEmpty()) {
            head = nodeStack.pop();
            System.out.println("Node : " + head.value);
            if (head.rightChild != null) {
                nodeStack.push(head.rightChild);
            }
            if (head.leftChild != null) {
                nodeStack.push(head.leftChild);
            }
        }
    }

    /**
     * 递归形式的中序遍历
     *
     * @param head
     */
    static void midPrintTree(Node head) {
        if (head == null) {
            return;
        }
        midPrintTree(head.leftChild);
        System.out.println("Node : " + head.value);
        midPrintTree(head.rightChild);
    }

    /**
     * 非递归形式的中序遍历
     *
     * @param head
     */
    static void midPrintTreeNo(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> nodeStack = new Stack<>();
        while (!nodeStack.isEmpty() || head != null) {
            if (head != null) {
                nodeStack.push(head);
                head = head.leftChild;
            } else {
                head = nodeStack.pop();
                System.out.println("Node : " + head.value);
                head = head.rightChild;
            }
        }
    }

    /**
     * 递归形式后序遍历
     *
     * @param head
     */
    static void posPrintTree(Node head) {
        if (head == null) {
            return;
        }
        posPrintTree(head.leftChild);
        posPrintTree(head.rightChild);
        System.out.println("Node : " + head.value);
    }

    /**
     * 非递归形式的后序遍历
     *
     * @param head
     */
    static void posPrintTreeNo(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> nodeStack = new Stack<>();
        Stack<Node> printStack = new Stack<>();
        nodeStack.push(head);
        while (!nodeStack.isEmpty()) {
            head = nodeStack.pop();
            printStack.push(head);
            if (head.leftChild != null) {
                nodeStack.push(head.leftChild);
            }
            if (head.rightChild != null) {
                nodeStack.push(head.rightChild);
            }
        }
        while (!printStack.isEmpty()) {
            System.out.println("Node : " + printStack.pop().value);
        }
    }

    /**
     * 按照层级遍历二叉树
     * @param head
     */
    static void printTreeByLevel(Node head){
        if (head!=null){
            Queue<Node> nodeQueue=new LinkedList<>();
            nodeQueue.add(head);
            while (!nodeQueue.isEmpty()){
                head=nodeQueue.poll();
                System.out.println("Node : " + head.value);
                if (head.leftChild!=null){
                    nodeQueue.add(head.leftChild);
                }
                if (head.rightChild!=null){
                    nodeQueue.add(head.rightChild);
                }
            }
        }
    }

    static Node init() {
        Node root = new Node(6);
        Node node1 = new Node(7);
        Node node2 = new Node(3);
        Node node3 = new Node(2);
        Node node4 = new Node(5);
        Node node5 = new Node(8);
        Node node6 = new Node(3);
        Node node7 = new Node(6);

        //初始化二叉树
        root.leftChild = node6;
        root.rightChild = node7;
        node6.leftChild = node3;
        node6.rightChild = node4;
        node4.leftChild = node1;
        node4.rightChild = node2;
        node7.leftChild = node5;

        return root;
    }

    public static void main(String[] args) {
        Node head = init();
//        prePrintTree(head);
//        prePrintTreeNo(head);
//        midPrintTree(head);
//        midPrintTreeNo(head);
//        posPrintTree(head);
//        posPrintTreeNo(head);
        printTreeByLevel(head);
    }
}