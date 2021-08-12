package net.bxx2004.pandalib.putil;

import java.util.Random;

/**
 * 数学工具
 */
public class PMath {
    /**
     * 随机数
     */
    private static Random random = new Random();
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
    @Deprecated
    public static int sum(int... number){
        int b = 0;
        for (int a : number){
            b = b + a;
        }
        return b;
    }

    /**
     * 从一个数组里面获取随机字符串
     * @param strings 数组
     * @return 随机的字符串
     */
    public static String getRandomAsString(String... strings){
        return strings[PMath.getRandomAsInt(0,strings.length-1)];
    }

    /**
     * 将小数保留小数点后两位
     * @param x 小数
     * @return 格式化的字符串
     */
    public static String formatDouble(double x){
        return String.format("%.2f", x);
    }
}
