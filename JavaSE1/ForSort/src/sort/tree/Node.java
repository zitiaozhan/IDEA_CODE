/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Node
 * Author:   郭新晔
 * Date:     2018/9/10 0010 18:07
 * Description: 节点
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.tree;

/**
 * 〈一句话功能简述〉<br>
 * 〈节点〉
 *
 * @author 郭新晔
 * @create 2018/9/10 0010
 * @since 1.0.0
 */
public class Node {
    public Node leftChild;
    public Node rightChild;
    public int value;
    public int level;

    public Node(Node leftChild, Node rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Node(int value) {
        this.value = value;
    }
    public Node() {
    }

    public Node(int value, int level) {
        this.value = value;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}