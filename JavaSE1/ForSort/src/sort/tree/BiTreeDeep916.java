/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: BiTreeDeep916
 * Author:   郭新晔
 * Date:     2018/9/16 0016 18:43
 * Description: 遍历数的深度
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
 * 〈遍历二叉树的深度〉
 *
 * @author 郭新晔
 * @create 2018/9/16 0016
 * @since 1.0.0
 */
public class BiTreeDeep916 {

    static void prePrintTree(Node head) {
        if (head == null) {
            return;
        }
        System.out.println("This node is " + head.value);
        prePrintTree(head.leftChild);
        prePrintTree(head.rightChild);
    }

    static void prePrintTreeNo(Node head) {
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

    static void midPrintTree(Node head) {
        if (head != null) {
            return;
        }
        midPrintTree(head.leftChild);
        System.out.println("This node is " + head.value);
        midPrintTree(head.rightChild);
    }

    static void midPrintTreeNo(Node head) {
        if (head != null) {
            Stack<Node> nodeStack = new Stack<>();
            if (!nodeStack.isEmpty() || head != null) {
                if (head != null) {
                    nodeStack.push(head);
                    head = head.leftChild;
                } else {
                    head = nodeStack.pop();
                    System.out.println("This node is " + head.value);
                    nodeStack.push(head.rightChild);
                    head = head.rightChild;
                }
            }
        }
    }

    static void posPrintTree(Node head) {
        if (head == null) {
            return;
        }
        posPrintTree(head.leftChild);
        posPrintTree(head.rightChild);
        System.out.println("This node is " + head.value);
    }

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
            while (!printStack.isEmpty()) {
                System.out.println("This node is " + printStack.pop().value);
            }
        }
    }

    static void printTreeByLevel(Node head) {
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

    /**
     * 求解树的高度,只需要
     * 求自己左子树的高度+1(自己的高度)与右子树的高度+1(自己的高度)
     * 的最大值
     *
     * @param head
     * @return
     */
    static int getHeight1(Node head) {
        if (head == null) {
            return 0;
        }
        int leftLevel = getHeight1(head.leftChild);
        int rightLevel = getHeight1(head.rightChild);
        return leftLevel > rightLevel ? leftLevel + 1 : rightLevel + 1;
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
        int height;
        height = getHeight1(head);
        System.out.println("树的高度为: " + height);
    }
}