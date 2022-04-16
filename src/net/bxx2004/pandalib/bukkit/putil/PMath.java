package net.bxx2004.pandalib.bukkit.putil;

import net.bxx2004.pandalib.bukkit.manager.Lang;
import net.bxx2004.pandalib.bukkit.planguage.PAction;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 数学工具
 */
public class PMath {
    public static boolean sum(Player player, String term){
        try {
            if (term.contains("|")){
                String[] a = term.split("\\|");
                for (String b : a){
                    boolean result = (boolean) PAction.go(b.trim(),player);
                    if (result){
                        return true;
                    }
                }
                return false;
            }
            if (term.contains("&")){
                String[] a = term.split("&");
                for (String b : a){
                    boolean result = (boolean) PAction.go(b.trim(),player);
                    if (!result){
                        return false;
                    }
                }
                return true;
            }
            boolean result = (boolean) PAction.go(term.trim(),player);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            Lang.error("&4异常: IF条件异常",term);
        }
        return false;
    }
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
    public static String[] toStringArray(List<String> list){
        String[] array = new String[list.size()];
        for (int i = 0 ; i < list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
    }
    public static List<String> toStringList(String... array){
        List<String> list = new ArrayList<>();
        for (String s : array){
            list.add(s);
        }
        return list;
    }
}
