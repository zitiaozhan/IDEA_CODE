/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: SmallRootPile
 * Author:   郭新晔
 * Date:     2018/11/6 0006 19:28
 * Description: 小根堆处理Top N问题
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.no_sort_practice;

import java.util.Arrays;

/**
 * 〈一句话功能简述〉<br>
 * 〈模拟小根堆处理Top N问题〉
 *
 * @author 郭新晔
 * @create 2018/11/6 0006
 * @since 1.0.0
 */
public class SmallRootPile {
    //保存已生成数据中最小值、最大值、当前的值、数据的数量、统计Top N
    private int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, value, count, n, up, low;
    //小根堆
    private int[] heap;

    public SmallRootPile(int low, int up, int count, int n) {
        this.up = up;
        this.low = low;
        this.count = count;
        this.n = n > 50 ? 50 : n;
        this.heap = new int[this.n];

        for (int i = 0; i < n; i++) {
            heap[i] = getValue();
        }
        //生成长度为n的小根堆
        for (int i = 0; i < n; i++) {
            heapInitInsert(heap, i);
        }
        this.count -= n;
    }

    /**
     * 得到TopN结果
     *
     * @return 返回数据排行
     */
    public int[] getTopN() {
        for (int i = 0; i < this.count; i++) {
            heapInsert(getValue());
        }
        Arrays.sort(this.heap);
        if (this.heap[this.n - 1] == this.max) {
            System.out.println(">_< >_< >_< >_< >_< >_< Right");
        } else {
            System.out.println("ERROR ERROR ERROR ERROR Error");
            System.out.println("min=" + this.min + " ||| max=" + this.max);
        }
        return this.heap;
    }

    /**
     * 初始化大小为n的小根堆
     *
     * @param nums
     * @param i
     */
    private void heapInitInsert(int[] nums, int i) {
        while (nums[i] < nums[(i - 1) / 2]) {
            swap(nums, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    /**
     * 处理大数据量的Top问题
     *
     * @param num 插入目标数据
     */
    private void heapInsert(int num) {
        if (num > heap[0]) {
            heap[0] = num;
            adjustment();
        }
    }

    /**
     * 将被打乱的小根堆调整好
     */
    private void adjustment() {
        int index = 0, left = 2 * index + 1, less, size = heap.length;
        while (left < size) {
            less = left + 1 < size && heap[left + 1] < heap[left] ? left + 1 : left;
            less = heap[less] <= heap[index] ? less : index;
            if (less == index) {
                break;
            }
            swap(heap, less, index);
            index = less;
            left = 2 * index + 1;
        }
    }

    /**
     * 整型数据生成器
     *
     * @param min 生成数范围下限
     * @param max 生成数范围上限
     * @return 返回生成整型结果
     */
    private int digitalGenerator(int min, int max) {
        return max > min ? min + (int) (Math.random() * (max - min + 1)) : max == min ? min : 0;
    }

    /**
     * 获得随机生成数结果
     *
     * @return 返回随机数
     */
    private int getValue() {
        value = digitalGenerator(this.low, this.up);
        min = min < value ? min : value;
        max = max > value ? max : value;
        return value;
    }

    /**
     * 数组元素交换
     *
     * @param nums 目标数组
     * @param a    下标1
     * @param b    下标2
     */
    private void swap(int[] nums, int a, int b) {
        if (a == b || nums[a] == nums[b]) {
            return;
        }
        nums[a] = nums[a] ^ nums[b];
        nums[b] = nums[a] ^ nums[b];
        nums[a] = nums[a] ^ nums[b];
    }

    public static void main(String[] args) {
        long begin, end;
        begin = System.currentTimeMillis();
        SmallRootPile test = new SmallRootPile(-100000000, 900000000, 100000000, 10);
        System.out.println(Arrays.toString(test.getTopN()));
        end = System.currentTimeMillis();
        System.out.println("====================共耗时：" + (end - begin) + "毫秒===================");
    }
}