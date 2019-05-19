package sort.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}

public class Solution {
    /**
     * 二叉树的前序序列化
     *
     * @param root
     * @return
     */
    String Serialize(TreeNode root) {
        if (root == null) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        SerializeRe(root, str);
        return str.toString();
    }

    /**
     * 二叉树的前序序列化递归方法
     *
     * @param root
     * @param str
     */
    public void SerializeRe(TreeNode root, StringBuilder str) {
        if (root == null) {
            str.append("#,");
            return;
        } else {
            str.append(root.val);
            str.append(",");
            SerializeRe(root.left, str);
            SerializeRe(root.right, str);
        }
        return;
    }

    /**
     * 前序反序列化
     *
     * @param str
     * @return
     */
    TreeNode Deserialize(String str) {
        if (str == null || str.length() == 0 || str.equals("#")) {
            return null;
        }
        String[] value = str.split(",");
        return DeserializeRe(value);
    }

    int index = 0;

    /**
     * 前序反序列化递归方法
     *
     * @param str
     * @return
     */
    public TreeNode DeserializeRe(String[] str) {
        TreeNode root = new TreeNode(-1);
        if (str[index].equals("#") || index > str.length) {
            return null;
        }
        root.val = Integer.valueOf(str[index]);
        index++;
        root.left = DeserializeRe(str);
        index++;
        root.right = DeserializeRe(str);
        return root;
    }

    /**
     * 根据数组从上到下构造一棵二叉树（即完全二叉树）
     *
     * @param a
     * @return
     */
    public TreeNode buildTreeLevel(int[] a) {
        if (a == null || a.length == 0) {
            return null;
        }
        return buildTreeLevelRe(a, 0);
    }

    private TreeNode buildTreeLevelRe(int[] a, int index) {
        TreeNode root = new TreeNode(-1);
        root.val = a[index];
        if (index * 2 + 1 <= a.length - 1) {
            root.left = buildTreeLevelRe(a, index * 2 + 1);
        }
        if (index * 2 + 2 <= a.length - 1) {
            root.right = buildTreeLevelRe(a, index * 2 + 2);
        }
        return root;
    }

    /**
     * 前序遍历二叉树
     *
     * @param root
     */
    public void preOderTree(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.val);
        preOderTree(root.left);
        preOderTree(root.right);
        return;
    }

    /**
     * 中序遍历二叉树
     *
     * @param root
     */
    public void inOderTree(TreeNode root) {
        if (root == null) {
            return;
        }
        inOderTree(root.left);
        System.out.println(root.val);
        inOderTree(root.right);
        return;
    }

    /**
     * 后序遍历二叉树
     *
     * @param root
     */
    public void postOderTree(TreeNode root) {
        if (root == null) {
            return;
        }
        postOderTree(root.left);
        postOderTree(root.right);
        System.out.println(root.val);
        return;
    }

    /**
     * 层次遍历二叉树
     *
     * @param root
     */
    public void levelOderTree(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> nodes = new LinkedList<TreeNode>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            TreeNode node = nodes.poll();
            System.out.println(node.val);
            if (node.left != null) {
                nodes.add(node.left);
            }
            if (node.right != null) {
                nodes.add(node.right);
            }
        }
    }

    /**
     * 给定一颗二叉搜索树，请找出其中的第k大的结点。例如， 5 / \ 3 7 /\ /\ 2 4 6 8 中， 按结点数值大小顺序第三个结点的值为4。
     * 解法一：时间复杂度O(k),空间复杂度O(1)
     *
     * @param root
     * @param k
     * @return
     */
    int count = 0;// 计数器

    public TreeNode KthNodeRe(TreeNode root, int k) {
        if (root != null) {
            TreeNode node = KthNodeRe(root.left, k);
            if (node != null) {
                return node;
            }
            count++;
            if (count == k) {
                return root;
            }
            node = KthNodeRe(root.right, k);
            if (node != null) {
                return node;
            }
        }
        return null;
    }

    ArrayList<TreeNode> arr = new ArrayList<TreeNode>();

    /**
     * 解法二：时间复杂度O(n),空间复杂度O(n)
     *
     * @param pRoot
     * @param k
     * @return
     */
    public TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot == null || k <= 0) {
            return null;
        }
        InOder(pRoot);
        return k > arr.size() ? null : arr.get(arr.size() - k);
    }

    public void InOder(TreeNode pRoot) {
        if (pRoot == null) {
            return;
        }
        InOder(pRoot.left);
        arr.add(pRoot);
        InOder(pRoot.right);
    }


    /**
     * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，
     * 那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，
     * 那么中位数就是所有数值排序之后中间两个数的平均值
     *
     * @param num
     */
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(
            new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            });

    public void Insert(Integer num) {
        if (count % 2 == 0) {
            // 1.新加入的元素先入到大根堆，由大根堆筛选出堆中最大的元素
            maxHeap.offer(num);
            int filteredMaxNum = maxHeap.poll();
            // 2.筛选后的【大根堆中的最大元素】进入小根堆
            minHeap.offer(filteredMaxNum);
        } else {
            // 1.新加入的元素先入到小根堆，由小根堆筛选出堆中最小的元素
            minHeap.offer(num);
            int filteredMinNum = minHeap.poll();
            // 2.筛选后的【小根堆中的最小元素】进入大根堆
            maxHeap.offer(filteredMinNum);
        }
        count++;
    }

    public Double GetMedian() {
        if (count % 2 == 0) {
            return new Double((minHeap.peek() + maxHeap.peek())) / 2;
        } else {
            return new Double(minHeap.peek());
        }
    }

    /**
     * 给定一棵二叉树，要求寻找其最下面一层的最左边叶子
     *
     * 可以用队列进行从右至左的层级遍历，队列最后一个节点就是最左下的节点。
     * 也可以用递归的方式求解，对每个节点，求其左右子树的高度和左下叶子，
     * 如果高度相等，返回左子树的左下叶子，否则返回高度较高的子树的左下叶子
     *
     * 其中re[0]用来帮助判断层级高度, re[1]为存储的节点数据
     * @param root
     * @return
     */
    public int findBottomLeftValue(TreeNode root) {
        return find(root)[1];
    }

    public int[] find(TreeNode root) {
        int[] re = new int[2];
        if (root == null) {
            re[0] = 0;
            re[1] = 0;
            return re;
        }
        if (root.left == null && root.right == null) {
            re[0] = 1;
            re[1] = root.val;
            return re;
        }
        int[] reLeft = find(root.left);
        int[] reRight = find(root.right);
        if (reLeft[0] >= reRight[0]) {
            re[0] = reLeft[0] + 1;
            re[1] = reLeft[1];
        } else {
            re[0] = reRight[0] + 1;
            re[1] = reRight[1];
        }
        return re;
    }


    /*@Test
    public void testDeserialize() {
        String str = "1,2,4,#,#,5,#,#,3,#,#";
        TreeNode root = Deserialize(str);
        preOderTree(root);
    }

    @Test
    public void testBuildTreePre() {
        String str = "1,2,4,#,#,5,#,#,3,#,#";
        TreeNode root = Deserialize(str);
        // preOderTree(root);
        String serialize = Serialize(root);
        System.out.println(serialize);
    }

    @Test
    public void testBuildTreeLevel() {
        int[] a = { 1, 2, 3, 4, 5 };
        TreeNode root = buildTreeLevel(a);
        inOderTree(root);
    }

    @Test
    public void testBuildTreeKthNode() {
        int[] a = { 4, 2, 5, 1, 3 };
        TreeNode root = buildTreeLevel(a);
        inOderTree(root);
    }*/
}