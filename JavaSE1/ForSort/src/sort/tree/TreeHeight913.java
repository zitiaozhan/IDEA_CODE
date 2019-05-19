/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TreeHeight913
 * Author:   郭新晔
 * Date:     2018/9/13 0013 20:47
 * Description: 遍历输的高度
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
 * 〈遍历树的高度〉
 *
 * @author 郭新晔
 * @create 2018/9/13 0013
 * @since 1.0.0
 */
public class TreeHeight913 {
    /**
     * 递归遍历树的高度
     *
     * @param head
     * @param level
     * @return
     */
    static int getHeight1(Node head, int level) {
        if (head == null) {
            return 0;
        }
        int leftLevel = getHeight1(head.leftChild, level + 1);
        int rightLevel = getHeight1(head.rightChild, level + 1);
        return leftLevel > rightLevel ? leftLevel + 1 : rightLevel + 1;
    }

    /**
     * 非递归的遍历树的高度
     *
     * @param head
     * @return
     */
    static int getHeight2(Node head) {
        int level = 0;
        class NodeHelper {
            Node node;
            int level;

            public NodeHelper(Node node, int level) {
                this.node = node;
                this.level = level;
            }
        }
        Stack<NodeHelper> nodeHelperStack = new Stack<NodeHelper>();
        nodeHelperStack.push(new NodeHelper(head, 1));
        NodeHelper nodeHelper = null;
        while (!nodeHelperStack.isEmpty()) {
            nodeHelper = nodeHelperStack.pop();
            level = nodeHelper.level > level ? nodeHelper.level : level;
            if (nodeHelper.node.rightChild != null) {
                nodeHelperStack.push(new NodeHelper(nodeHelper.node.rightChild, nodeHelper.level + 1));
            }
            if (nodeHelper.node.leftChild != null) {
                nodeHelperStack.push(new NodeHelper(nodeHelper.node.leftChild, nodeHelper.level + 1));
            }
        }
        return level;
    }

    /**
     * 非递归实现遍历树的高度
     *
     * @param head
     * @return
     */
    static int getHeight3(Node head) {
        int front = -1, rear = -1;
        int last = 0, level = 0;
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
        height = getHeight1(head, 1);
        height = getHeight2(head);
        height = getHeight3(head);
        System.out.println("树的高度为: " + height);
    }
}