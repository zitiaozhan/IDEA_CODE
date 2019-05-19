/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PrintTree1011
 * Author:   郭新晔
 * Date:     2018/10/11 0011 10:26
 * Description: 二叉树的遍历
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
 * 〈二叉树的遍历〉
 *
 * @author 郭新晔
 * @create 2018/10/11 0011
 * @since 1.0.0
 */
public class PrintTree1011 {
    static void prePrintTree(Node head) {
        if (head == null) {
            return;
        }
        System.out.println("This Node is " + head.value);
        prePrintTree(head.leftChild);
        prePrintTree(head.rightChild);
    }

    static void prePrintTreeByRece(Node head) {
        if (head != null) {
            Stack<Node> nodeStack = new Stack<>();
            nodeStack.push(head);
            while (!nodeStack.isEmpty()) {
                head = nodeStack.pop();
                System.out.println("This Node is " + head.value);
                if (head.rightChild != null) {
                    nodeStack.push(head.rightChild);
                }
                if (head.leftChild != null) {
                    nodeStack.push(head.leftChild);
                }
            }
        }
    }

    static void midPrintTree(Node head) {
        if (head == null) {
            return;
        }
        midPrintTree(head.leftChild);
        System.out.println("This Node is " + head.value);
        midPrintTree(head.rightChild);
    }

    static void midPrintTreeByRece(Node head) {
        if (head != null) {
            Stack<Node> nodeStack = new Stack<>();
            while (!nodeStack.isEmpty() || head != null) {
                if (head != null) {
                    nodeStack.push(head);
                    head = head.leftChild;
                } else {
                    head = nodeStack.pop();
                    System.out.println("This Node is " + head.value);
                    head = head.rightChild;
                }
            }
        }
    }

    static void postPrintTree(Node head) {
        if (head == null) {
            return;
        }
        postPrintTree(head.leftChild);
        postPrintTree(head.rightChild);
        System.out.println("This Node is " + head.value);
    }

    static void postPrintTreeByRece(Node head) {
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
            while (!printStack.isEmpty()) {
                head = printStack.pop();
                System.out.println("This Node is " + head.value);
            }
        }
    }

    static void printTreeByLevel(Node head) {
        if (head != null) {
            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(head);
            while (!nodeQueue.isEmpty()) {
                head = nodeQueue.poll();
                System.out.println("This Node is " + head.value);
                if (head.leftChild != null) {
                    nodeQueue.add(head.leftChild);
                }
                if (head.rightChild != null) {
                    nodeQueue.add(head.rightChild);
                }
            }
        }
    }

    static int getTreeHeight(Node head) {
        if (head == null) {
            return 0;
        }
        int left = getTreeHeight(head.leftChild) + 1;
        int right = getTreeHeight(head.rightChild) + 1;
        return Math.max(left, right);
    }

    static int getTreeHeight2(Node head) {
        int level = 0;
        if (head != null) {
            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(head);
            Node last = head, nlast = head;
            while (!nodeQueue.isEmpty()) {
                head = nodeQueue.poll();
                if (head.leftChild != null) {
                    nodeQueue.add(head.leftChild);
                    nlast = head.leftChild;
                }
                if (head.rightChild != null) {
                    nodeQueue.add(head.rightChild);
                    nlast = head.rightChild;
                }
                if (last == head) {
                    level++;
                    last = nlast;
                }
            }
        }
        return level;
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
//        prePrintTreeByRece(head);
//        midPrintTree(head);
//        midPrintTreeByRece(head);
//        postPrintTree(head);
//        postPrintTreeByRece(head);
//        printTreeByLevel(head);
        int deep;
//        deep = getTreeHeight(head);
        deep = getTreeHeight2(head);
        System.out.println(deep);
    }

}