package sort.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 求树的高度
 *
 * @author Fangchao 2016年12月4日
 */
public class BiTreeDepth {

    /**
     * 递归方法
     *
     * @param root
     * @return
     */
    static int btdep(Node root) {
        if (root == null) {
            return 0;
        }
        int ldep = btdep(root.leftChild);
        int rdep = btdep(root.rightChild);
        return Math.max(ldep, rdep) + 1;
    }

    /**
     * 非递归方法
     * 求每层的个数 树的最大宽度  都可以采用这种思想
     *
     * @param root
     * @return
     */
    static int btdep2(Node root) {
        if (root == null) {
            return 0;
        }
        int front = -1, rear = -1;
        int last = 0, level = 0;
        //x为每层的节点数
        List<Integer> x = new ArrayList<>();
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(++rear, root);
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
                x.add(level-1,rear-last);
                last = rear;
            }
        }
        for (int i=0;i<x.size();i++){
            System.out.println("第["+(i+1)+"]层有"+x.get(i)+"个节点...");
        }
        return level;
    }

    static void init(Node root) {
        Node cur = root;
        cur.leftChild = new Node(4);
        cur.rightChild = new Node(6);
        cur.leftChild.leftChild = new Node(3);
    }

    public static void main(String[] args) {
        Node root = new Node(5);
        init(root);
        System.out.println("递归方法 树的高度是" + btdep(root));

        System.out.println("递归方法 树的高度是" + btdep2(root));
    }
}