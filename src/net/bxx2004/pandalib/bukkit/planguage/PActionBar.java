package net.bxx2004.pandalib.bukkit.planguage;

import net.bxx2004.pandalib.bukkit.PandaLib;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 动作栏工具类
 */
public class PActionBar {
    /**
     * 发送一个ActionBar
     * @param player 玩家
     * @param message 内容
     */
    public static void To(Player player, String message){
        player.sendActionBar(message.replaceAll("&", "§"));
    }

    /**
     * 发送一个打印特效的ActionBar
     * @param player 玩家
     * @param message 内容
     * @param speed 速度
     */
    public static void To2(Player player, String message, int speed){
        char[] titles = message.toCharArray();
        new BukkitRunnable(){
            int i = 0;
            String m = "";
            @Override
            public void run() {
                m += titles[i];
                m += m.replaceAll("&", "§") + "_";
                player.sendActionBar(m);
                i = i+1;
                if (i == titles.length){
                    player.sendActionBar(" ");
                    player.sendActionBar(message);
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(PandaLib.getInstance(),0,speed);
    }
}
