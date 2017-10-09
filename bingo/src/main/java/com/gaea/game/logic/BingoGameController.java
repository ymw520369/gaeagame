package com.gaea.game.logic;

import com.gaea.game.core.timer.TimerEvent;
import com.gaea.game.core.timer.TimerListener;
import com.gaea.game.logic.data.GameAnn;

/**
 * Created on 2017/9/25.
 *
 * @author Alan
 * @since 1.0
 */
@GameAnn(2)
public class BingoGameController implements TimerListener {
    @Override
    public void onTimer(TimerEvent e) {

    }

    public static void main(String[] args) {
        int[][] arr = new int[5][];
        initializeArray(arr);//初始化5*5的数组1-7*7
        int[][] arr0 = new int[5][];//点亮数组
        for (int i = 0; i < 5; i++) {
            arr0[i] = new int[5];
        }//点亮数组全部默认为0
        System.out.println("开始打印Bingo值");
        print2Array(arr);
        System.out.println("开始打印点亮数组");
        print2Array(arr0);
         /*开始游戏
            对于每次落子后开始遍历1的出现情况
              1，每一行每一列或者每个斜行列攥足5个就结束
              2，点亮数组
         */
        int c = playGame(arr, arr0);
        System.out.println("你本期完成Bingo使用的次数合计 " + c + "次");
        System.out.println("打印完成游戏后的情况----");
        System.out.println("开始打印Bingo值");
        print2Array(arr);
        System.out.println("开始打印结束Bingo后的点亮数组");
        print2Array(arr0);
    }

    //初始化二维数组5*5;输入7*7范围内的数值
    public static void initializeArray(int[][] arr2) {
        for (int i = 0; i < 5; i++) {
            arr2[i] = new int[5];
            for (int j = 0; j < 5; j++) {
                arr2[i][j] = (int) (49 * Math.random() + 1);
            }
        }
    }

    //打印二维数组
    public static void print2Array(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
            System.out.println();
        }

    }

    //参与游戏程序;
    public static int playGame(int[][] arr, int[][] arr0) {
        int i = 0;
        double oc = 0.3;//设置阈值
        int[] arrc = new int[50];
        while (true) {
            //给与机会参与游戏
            double occ = Math.random();
            if (occ > oc) {
                int t = (int) (49 * Math.random() + 1);
                boolean ol = AAE.xFind(arrc, t);
                if (ol) {
                    continue;
                } else {
                    AAE.arrayAddElement(arrc, t);//数组末增加元素
                    increaseValue(arr, arr0, t);//两数组的对应处理
                    i++;
                    boolean flag = judge(arr0); //满足条件否
                    if (flag) {
                        break;
                    }
                }
            } else {
                int t = (int) (49 * Math.random() + 1);
                AAE.arrayAddElement(arrc, t);
                increaseValue(arr, arr0, t);
                i++;
                boolean flag = judge(arr0);
                if (flag) {
                    break;
                }
            }
        }
        return i;
    }

    public static boolean judge(int[][] arr3) {
        //判断点亮数组中是否有满足目标的对象;即遍历12条路
        //遍历行
        boolean judge = false;
        int count = 0;
        for (int i = 0; i < arr3.length; i++) {
            int obj1 = 0;
            int obj2 = 0;
            int obj3 = 0;
            int obj4 = 0;
            obj3 += arr3[i][arr3.length - i - 1];
            obj4 += arr3[i][i];//obj3和obj4为对角两条线
            for (int j = 0; j < arr3.length; j++) {
                obj1 += arr3[i][j];//行
                obj2 += arr3[j][i];  //列
            }
            if (obj1 == 5) {
                count++;
            }
            if (obj2 == 5) {
                count++;
            }
            if (obj3 == 5) {
                count++;
            }
            if (obj4 == 5) {
                count++;
            }
        }
        if (count >= 5) {//点亮数组求和;12条线若存在有至少5个sum=5则结束;
            judge = true;
        }
        return judge;
    }

    //遍历整个二维数组；返回该元素在二维数组中的所有位置;并在点亮数组中标记++
    public static void increaseValue(int[][] arr, int[][] arr0, int x) {
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] == x) {
                    arr0[i][j] = 1;
                }
            }//int hang = (index/10)%hang;//int lie = index%lie;
        }
    }
}


class AAE {

    public static void arrayAddElement(int[] arr, int x) {
        //对于一个长度固定且足够大的数组增加新元素；这里主要使用集合的功能
        //补集运用,这里会用到
        int index = returnIndex(arr);//先访问到非0索引
        boolean flag = xFind(arr, x);
        if (flag == false) {
            arr[index + 1] = x;//将x元素植入数组末尾
        }
    }

    public static int returnIndex(int[] arr1) {
        int index = 0;
        for (int i = 0; i < arr1.length; i++) {
            if (i == 0 && arr1[i] == 0) {
                index = -1;
                break;
            } else if (arr1[i] == 0) {
                index = i - 1;
                break;
            }
        }
        return index;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
        System.out.println();
    }

    //判断元素是否在数组中;是返回true
    public static boolean xFind(int[] arr, int x) {
        boolean flag = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                flag = true;
            }
        }
        return flag;
    }
}
