/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TreeHeight
 * Author:   郭新晔
 * Date:     2018/9/11 0011 22:12
 * Description: 遍历二叉树高度
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
 * 〈遍历二叉树高度〉
 *
 * @author 郭新晔
 * @create 2018/9/11 0011
 * @since 1.0.0
 */
public class TreeHeight {
    public static void main(String[] args) {
        Node head = init();
        //int height=getTreeHeight(head);
        //int height = getTreeHeightNoRecursive(head);
        int height = depGetHeight(head);
        System.out.println("树的高度为: " + height);
    }

    /**
     * 递归实现二叉树高度
     *
     * @param head
     */
    static int getTreeHeight(Node head) {
        if (head == null) {
            return 0;
        }
        int leftHeight = getTreeHeight(head.leftChild);
        int rightHeight = getTreeHeight(head.rightChild);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 非递归实现求二叉树高度
     *
     * @param head
     * @return
     */
    static int getTreeHeightNoRecursive(Node head) {
        int height = 0;
        if (head != null) {
            NodeHeightGet headGet = new NodeHeightGet(head, 1);
            Stack<NodeHeightGet> heightStack = new Stack<>();
            heightStack.push(headGet);
            while (!heightStack.isEmpty()) {
                headGet = heightStack.pop();
                height = Math.max(headGet.level, height);

                if (headGet.node.leftChild != null) {
                    heightStack.push(new NodeHeightGet(headGet.node.leftChild, headGet.level + 1));
                }
                if (headGet.node.rightChild != null) {
                    heightStack.push(new NodeHeightGet(headGet.node.rightChild, headGet.level + 1));
                }
            }
        }
        return height;
    }

    static int depGetHeight(Node head) {
        int front = -1, rear = -1;
        int last = 0, level = 0;
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(++rear, head);
        Node p;
        while (front < rear) {
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
}

class NodeHeightGet {
    public Node node;
    public int level;

    public NodeHeightGet(Node node, int level) {
        this.node = node;
        this.level = level;
    }
}