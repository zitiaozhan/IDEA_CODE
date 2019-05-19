/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CirclePrintMatrix
 * Author:   郭新晔
 * Date:     2018/9/9 0009 20:56
 * Description: 转圈打印矩阵
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package sort.no_sort_practice;

/**
 * 〈一句话功能简述〉<br>
 * 〈转圈打印矩阵〉
 *
 * @author 郭新晔
 * @create 2018/9/9 0009
 * @since 1.0.0
 */
public class CirclePrintMatrix {
    public static void main(String[] args) {
        int[][] matix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
        };
        circlePrint(matix);
    }

    static void printEdge(int[][] matrix, int tR, int tC, int dR, int dC) {
        if (tR == dR) {
            for (int i = tC; i <= dC; i++) {
                System.out.print(matrix[tR][i] + " ");
            }
        } else if (tC == dC) {
            for (int i = tR; i <= dR; i++) {
                System.out.print(matrix[i][tC] + " ");
            }
        } else {
            int curC = tC, curR = tR;
            while (curC != dC) {
                System.out.print(matrix[tR][curC] + " ");
                curC++;
            }
            while (curR != dR) {
                System.out.print(matrix[curR][dC] + " ");
                curR++;
            }
            while (curC != tC) {
                System.out.print(matrix[dR][curC] + " ");
                curC--;
            }
            while (curR != tR) {
                System.out.print(matrix[curR][tC] + " ");
                curR--;
            }
        }
    }

    static void circlePrint(int[][] matrix) {
        int tR = 0, tC = 0, dR = matrix.length - 1, dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC) {
            printEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }
}