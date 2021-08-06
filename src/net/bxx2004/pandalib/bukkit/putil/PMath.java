package net.bxx2004.pandalib.bukkit.putil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 数学工具
 */
public class PMath {
    /**
     * 随机数
     */
    public static Random random = new Random();
    /**
     * 获得范围内的随机整数
     * @param max 最大值
     * @param min 最小值
     * @return 随机整数
     */
    public static int getRandomAsInt(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 获得范围内的随机小数
     * @param min 最小值
     * @param max 最大值
     * @return 随机小数
     */
    public static double getRandomAsDouble(double min, double max){
        return min + random.nextDouble() * (max - min);
    }

    /**
     * Int整数求和
     * @param number Int
     * @return 和
     */
    public static int sum(int... number){
        int b = 0;
        for (int a : number){
            b = b + a;
        }
        return b;
    }
}
