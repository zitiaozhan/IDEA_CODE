/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PrintTree915
 * Author:   郭新晔
 * Date:     2018/9/15 0015 17:32
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
 * @create 2018/9/15 0015
 * @since 1.0.0
 */
public class PrintTree915 {
    /**
     * 先序遍历(递归)
     *
     * @param head
     */
    static void prePrintTree(Node head) {
        if (head == null) {
            return;
        }
        System.out.println("This node is " + head.value);
        prePrintTree(head.leftChild);
        prePrintTree(head.rightChild);
    }

    /**
     * 先序遍历(非递归)
     *
     * @param head
     */
    static void prePrintTreeeNo(Node head) {
        if (head != null) {
            Stack<Node> nodeStack = new Stack<>();
            nodeStack.push(head);
            while (!nodeStack.isEmpty()) {
                head = nodeStack.pop();
                System.out.println("This node is " + head.value);
                if (head.rightChild != null) {
                    nodeStack.push(head.rightChild);
                }
                if (head.leftChild != null) {
                    nodeStack.push(head.leftChild);
                }
            }
        }
    }

    /**
     * 中序遍历(递归)
     *
     * @param head
     */
    static void midPrintTree(Node head) {
        if (head == null) {
            return;
        }
        midPrintTree(head.leftChild);
        System.out.println("This node is " + head.value);
        midPrintTree(head.rightChild);
    }

    /**
     * 中序遍历(非递归)
     *
     * @param head
     */
    static void midPrintTreeNo(Node head) {
        if (head != null) {
            Stack<Node> nodeStack = new Stack<>();
            while (!nodeStack.isEmpty() || head != null) {
                if (head != null) {
                    nodeStack.push(head);
                    head = head.leftChild;
                } else {
                    head = nodeStack.pop();
                    System.out.println("This node is " + head.value);
                    head = head.rightChild;
                }
            }
        }
    }

    /**
     * 后序遍历(递归)
     *
     * @param head
     */
    static void posPrintTree(Node head) {
        if (head == null) {
            return;
        }
        posPrintTree(head.leftChild);
        posPrintTree(head.rightChild);
        System.out.println("This node is " + head.value);
    }

    /**
     * 后序遍历(非递归)
     *
     * @param head
     */
    static void posPrintTreeNo(Node head) {
        if (head != null) {
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
            while (!printStack.isEmpty()){
                System.out.println("This node is " + printStack.pop().value);
            }
        }
    }

    /**
     * 层级遍历二叉树
     *
     * @param head
     */
    static void printTreeByLevelNo(Node head) {
        if (head != null) {
            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(head);
            while (!nodeQueue.isEmpty()) {
                head = nodeQueue.poll();
                System.out.println("This node is " + head.value);
                if (head.leftChild != null) {
                    nodeQueue.add(head.leftChild);
                }
                if (head.rightChild != null) {
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
//        prePrintTreeeNo(head);
//        midPrintTree(head);
//        midPrintTreeNo(head);
//        posPrintTree(head);
//        posPrintTreeNo(head);
//        printTreeByLevelNo(head);
    }
}