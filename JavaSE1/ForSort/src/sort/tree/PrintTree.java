/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PrintTree
 * Author:   郭新晔
 * Date:     2018/9/11 0011 20:33
 * Description: 二叉树的遍历
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.tree;

import java.util.Stack;

/**
 * 〈一句话功能简述〉<br>
 * 〈二叉树的遍历〉
 *
 * @author 郭新晔
 * @create 2018/9/11 0011
 * @since 1.0.0
 */
public class PrintTree {
    public static void main(String[] args) {
        Node root = init();
        //preTreePrint(root);
        //noRecursivePrintByPre(root);
        //midTreePrint(root);
        //noRecursivePrintByMid(root);
        //posTreePrint(root);
        //noRecursivePrintByPos(root);
        printTreeMid(root);
    }

    /**
     * 递归的 二叉树的前序遍历
     *
     * @param root
     */
    static void preTreePrint(Node root) {
        if (root == null) {
            return;
        }
        System.out.println("This Node is " + root.value);
        preTreePrint(root.leftChild);
        preTreePrint(root.rightChild);
    }

    /**
     * 非递归的前序遍历
     * 首先入栈根节点,只要栈不空,弹出栈中第一个节点并打印,
     * 然后分别判断左右子树是否存在以及分别将他们入栈
     *
     * @param root
     */
    static void noRecursivePrintByPre(Node root) {
        if (root != null) {
            Stack<Node> nodeStack = new Stack<>();
            nodeStack.push(root);
            while (!nodeStack.isEmpty()) {
                root = nodeStack.pop();
                System.out.println("This Node is " + root.value);
                if (root.rightChild != null) {
                    nodeStack.push(root.rightChild);
                }
                if (root.leftChild != null) {
                    nodeStack.push(root.leftChild);
                }
            }
        }
    }

    /**
     * 递归思路实现中序遍历二叉树
     *
     * @param root
     */
    static void midTreePrint(Node root) {
        if (root == null) {
            return;
        }
        //首先遍历左子树
        midTreePrint(root.leftChild);
        //遍历到此节点
        System.out.println("This Node is " + root.value);
        //最后遍历右子树
        midTreePrint(root.rightChild);
    }

    /**
     * 非递归实现中序遍历二叉树
     * 依次入栈该节点的左节点,并将指针指向其左节点,若指向的节点为null
     * 则弹出栈中第一个节点,打印此节点并将指针指向该节点的右节点,
     * 直到栈已空且当前指针指向节点为null
     *
     * @param root
     */
    static void noRecursivePrintByMid(Node root) {
        if (root != null) {
            Stack<Node> nodeStack = new Stack<>();
            while (!nodeStack.isEmpty() || root != null) {
                if (root != null) {
                    nodeStack.push(root);
                    root = root.leftChild;
                } else {
                    root = nodeStack.pop();
                    System.out.println("This Node is " + root.value);
                    root = root.rightChild;
                }
            }
        }
    }

    static void printTreeMid(Node head) {
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

    static void printTreeMid2(Node head) {
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

    /**
     * 递归实现后序遍历二叉树
     *
     * @param head
     */
    static void posTreePrint(Node head) {
        if (head == null) {
            return;
        }
        posTreePrint(head.leftChild);
        posTreePrint(head.rightChild);
        System.out.println("This Node is " + head.value);
    }

    /**
     * 非递归实现后序遍历二叉树
     * 类似与前序遍历,添加一个打印栈作为辅助栈
     *
     * @param head
     */
    static void noRecursivePrintByPos(Node head) {
        if (head != null) {
            Stack<Node> nodeStack = new Stack<>();
            Stack<Node> printStack = new Stack<>();
            nodeStack.push(head);

            while (!nodeStack.isEmpty()) {
                head = nodeStack.pop();
                //打印栈按照节点栈弹出顺序(中右左)入栈,那么打印栈出栈就会与节点栈入栈顺序相反,顺序为左右中
                printStack.push(head);
                if (head.leftChild != null) {
                    nodeStack.push(head.leftChild);
                }
                //后入先出
                if (head.rightChild != null) {
                    nodeStack.push(head.rightChild);
                }
            }
            //依次弹出打印栈内容
            while (!printStack.isEmpty()) {
                System.out.println("This Node is " + printStack.pop().value);
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
}