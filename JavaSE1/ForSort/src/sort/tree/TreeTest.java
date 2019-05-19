/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TreeTest
 * Author:   郭新晔
 * Date:     2018/9/26 0026 20:49
 * Description: 二叉树测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.tree;

import primary.class_04.Code_06_IsBalancedTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 〈一句话功能简述〉<br>
 * 〈二叉树测试〉
 *
 * @author 郭新晔
 * @create 2018/9/26 0026
 * @since 1.0.0
 */
public class TreeTest {
    /**
     * 按层次遍历并换行打印
     *
     * @param head
     */
    static void printTreeByLevel(Node head) {
        if (head != null) {
            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(head);
            Node last = head, nlast = head;
            while (!nodeQueue.isEmpty()) {
                head = nodeQueue.poll();
                System.out.print(head.value + "  ");
                if (head.leftChild != null) {
                    nlast = head.leftChild;
                    nodeQueue.add(head.leftChild);
                }
                if (head.rightChild != null) {
                    nlast = head.rightChild;
                    nodeQueue.add(head.rightChild);
                }
                if (last == head) {
                    System.out.println();
                    last = nlast;
                }
            }
        }
    }

    /**
     * 二叉树的前序序列化
     *
     * @param head
     */
    static void biTreeSerializationByPre(Node head, StringBuilder bt) {
        if (head == null) {
            bt.append("#_");
            return;
        }
        bt.append(head.value + "_");
        biTreeSerializationByPre(head.leftChild, bt);
        biTreeSerializationByPre(head.rightChild, bt);
    }

    /*static Node biTreeDeserializationByPre(String dest) {
        if (dest == null || "".equals(dest)) {
            return null;
        }
        Node head = null;
        Node cur = new Node();
        Stack<Node> nodeStack = new Stack<>();
        String[] subStrs = dest.split("_");
        for (String item : subStrs) {
            if (!"#".equals(item)) {
                cur.value = Integer.parseInt(item);
                head = head == null ? cur : head;
                nodeStack.push(cur);
                cur.leftChild = new Node();
                cur.rightChild = new Node();
                cur = cur.leftChild;
            } else {
                //二叉树的null节点总比普通节点多一个,
                //因此添加栈的非空判断
                if (!nodeStack.isEmpty()) {
                    cur = nodeStack.pop();
                    cur = cur.rightChild;
                }
            }

        }
        return head;
    }*/

    /**
     * 二叉树的前序反序列化
     *
     * @param dest
     * @return
     */
    static Node biTreeDeserializationByPre(String dest) {
        if (dest == null || "".equals(dest)) {
            return null;
        }
        String[] values = dest.split("_");
        Queue<String> nodeQueue = new LinkedList<>();
        for (String item : values) {
            nodeQueue.add(item);
        }
        return deserializationByPreBody(nodeQueue);
    }

    static Node deserializationByPreBody(Queue<String> values) {
        String value = values.poll();
        if (value.equals("#")) {
            return null;
        }
        Node head = new Node(Integer.parseInt(value));
        head.leftChild = deserializationByPreBody(values);
        head.rightChild = deserializationByPreBody(values);
        return head;
    }

    /**
     * 判断一棵树是否为平衡二叉树
     * @param head
     * @return
     */
    public static boolean isBalance(Code_06_IsBalancedTree.Node head) {
        boolean[] res = new boolean[1];
        res[0] = true;
        getHeight(head, 1, res);
        return res[0];
    }

    public static int getHeight(Code_06_IsBalancedTree.Node head, int level, boolean[] res) {
        if (head == null) {
            return level;
        }
        int lH = getHeight(head.left, level + 1, res);
        if (!res[0]) {
            return level;
        }
        int rH = getHeight(head.right, level + 1, res);
        if (!res[0]) {
            return level;
        }
        if (Math.abs(lH - rH) > 1) {
            res[0] = false;
        }
        return Math.max(lH, rH);
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
        printTreeByLevel(head);
        StringBuilder res = new StringBuilder("");
        biTreeSerializationByPre(head, res);
        System.out.println(res.toString());
        head = biTreeDeserializationByPre(res.toString());
        printTreeByLevel(head);
    }
}