/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: BiTreeDeep915
 * Author:   郭新晔
 * Date:     2018/9/15 0015 20:17
 * Description: 获得二叉树高度
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 〈一句话功能简述〉<br>
 * 〈获得二叉树高度〉
 *
 * @author 郭新晔
 * @create 2018/9/15 0015
 * @since 1.0.0
 */
public class BiTreeDeep915 {
    static int getHeight1(Node head, int level) {
        if (head == null) {
            return 0;
        }
        int left = getHeight1(head.leftChild, level + 1);
        int right = getHeight1(head.rightChild, level + 1);
        return left > right ? left + 1 : right + 1;
    }

    static int getDepth(Node head) {
        if (head == null) {
            return 0;
        }
        int l = getDepth(head.leftChild);
        int r = getDepth(head.rightChild);
        return Math.max(l, r) + 1;
    }

    static int getHeight2(Node head) {
        if (head == null) {
            return 0;
        }

        class NodeHelper {
            Node node;
            int level;

            public NodeHelper(Node node, int level) {
                this.node = node;
                this.level = level;
            }
        }
        Stack<NodeHelper> nodeStack = new Stack<>();
        nodeStack.push(new NodeHelper(head, 1));
        NodeHelper p;
        int level = 1;
        while (!nodeStack.isEmpty()) {
            p = nodeStack.pop();
            level = p.level > level ? p.level : level;
            if (p.node.rightChild != null) {
                nodeStack.push(new NodeHelper(p.node.rightChild, p.level + 1));
            }
            if (p.node.leftChild != null) {
                nodeStack.push(new NodeHelper(p.node.leftChild, p.level + 1));
            }
        }
        return level;
    }

    static int getHeight3(Node head) {
        int level = 0, last = 0;
        int rear = -1, front = -1;
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(++rear, head);
        Node p;
        while (front < last) {
            p = nodeList.get(++front);
            if (p.leftChild != null) {
                nodeList.add(++rear, p.leftChild);
            }
            if (p.rightChild != null) {
                nodeList.add(++rear, p.rightChild);
            }
            if (front == last) {
                level++;
                last = rear;
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
        int height;
//        height = getHeight1(head, 1);
//        height = getHeight2(head);
        //height = getHeight3(head);
        height = getDepth(head);
        System.out.println("树的高度为: " + height);
    }
}