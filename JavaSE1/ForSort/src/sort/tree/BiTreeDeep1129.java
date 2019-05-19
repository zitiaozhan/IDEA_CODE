/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: BiTreeDeep1129
 * Author:   郭新晔
 * Date:     2018/11/29 0029 20:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 〈〉
 *
 * @create 2018/11/29 0029
 */
public class BiTreeDeep1129 {
    static int getHeight(Node head) {
        if (head == null) {
            return 0;
        }
        int l = getHeight(head.leftChild);
        int r = getHeight(head.rightChild);
        return Math.max(l, r) + 1;
    }

    static void prePrintTree(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + "、");
        prePrintTree(head.leftChild);
        prePrintTree(head.rightChild);
    }

    static void prePrintTreeNo(Node head) {
        if (head != null) {
            Stack<Node> nodeStack = new Stack<>();
            nodeStack.push(head);
            while (!nodeStack.isEmpty()) {
                head = nodeStack.pop();
                System.out.print(head.value + "、");
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
        System.out.print(head.value + "、");
        midPrintTree(head.rightChild);
    }

    static void midPrintTreeNo(Node head) {
        if (head != null) {
            Stack<Node> nodeStack = new Stack<>();
            while (!nodeStack.isEmpty() || head != null) {
                if (head != null) {
                    nodeStack.push(head);
                    head = head.leftChild;
                } else {
                    head = nodeStack.pop();
                    System.out.print(head.value + "、");
                    head = head.rightChild;
                }
            }
        }
    }

    static void lastPrintTree(Node head) {
        if (head == null) {
            return;
        }
        lastPrintTree(head.leftChild);
        lastPrintTree(head.rightChild);
        System.out.print(head.value + "、");
    }

    static void lastPrintTreeNo(Node head) {
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
                System.out.print(printStack.pop().value + "、");
            }
        }
    }

    static void BFS(Node head) {
        if (head != null) {
            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(head);
            while (!nodeQueue.isEmpty()) {
                head = nodeQueue.poll();
                System.out.print(head.value + "、");
                if (head.leftChild != null) {
                    nodeQueue.add(head.leftChild);
                }
                if (head.rightChild != null) {
                    nodeQueue.add(head.rightChild);
                }
            }
        }
    }

    static void BFSLine(Node head) {
        if (head != null) {
            Node cur = head, last = head;
            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(head);
            while (!nodeQueue.isEmpty()) {
                head = nodeQueue.poll();
                System.out.print(head.value + "、");
                if (head.leftChild != null) {
                    nodeQueue.add(head.leftChild);
                    cur = head.leftChild;
                }
                if (head.rightChild != null) {
                    nodeQueue.add(head.rightChild);
                    cur = head.rightChild;
                }
                if (head == last) {
                    last = cur;
                    System.out.println();
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
        System.out.println("Height: " + getHeight(head));
        prePrintTree(head);
        System.out.println();
        prePrintTreeNo(head);
        System.out.println();
        midPrintTree(head);
        System.out.println();
        midPrintTreeNo(head);
        System.out.println();
        lastPrintTree(head);
        System.out.println();
        lastPrintTreeNo(head);
        System.out.println();
        BFS(head);
        System.out.println();
        BFSLine(head);
    }
}