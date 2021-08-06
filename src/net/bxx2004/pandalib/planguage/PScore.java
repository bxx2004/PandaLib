package net.bxx2004.pandalib.planguage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * 记分板工具
 */
public class PScore {
    private Scoreboard sc;
    private Objective obj;
    private List<String> content;
    private List<Team> timers;
    private List<String> tempList;
    private Plugin p;
    private int speed;
    private boolean ba;
    /**
     * 创建一个记分板
     * @param plugin 插件
     * @param id 内部ID
     * @param disPlayName 名称
     */
    public PScore(Plugin plugin, String id, String disPlayName){
        this.sc = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = this.sc.registerNewObjective(id, "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(disPlayName.replaceAll("&", "§"));
        tempList = new ArrayList<>();
        timers = new ArrayList<>();
        this.p = plugin;
        this.speed = 20;
    }

    /**
     * 设置一个记分板的内容
     * @param list 记分板内容
     */
    public void setContent(List<String> list){
        if (content != null && content.size() == 15){
            content = list;
            refresh();
        }else {
            for (int i = 0; i < list.size(); i++) {
                tempList.add("§" + ChatColor.values()[i].getChar());
            }
            for (int i = 0; i < list.size(); i++) {
                Team timer = sc.registerNewTeam("" + i);
                timer.addEntry(tempList.get(i));
                obj.getScore(tempList.get(i)).setScore(i);

                timers.add(timer);
            }
            content = list;
            refresh();
        }
    }

    /**
     * 设置记分板名称
     * @param name 名称
     */
    public void setDisPlayName(String name){
        obj.setDisplayName(name.replaceAll("&", "§"));
    }
    /**
     * 手动刷新记分板
     */
    public void refresh(){
        for (int i = 0; i < timers.size(); i++) {
            Team timer = timers.get(i);
            timer.setPrefix(content.get(i).replaceAll("&","§"));
        }
    }

    /**
     * 取消某玩家显示
     * @param player 玩家
     */
    public void removePlayer(Player player){
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }
    /**
     * 是否开启自动刷新
     * @param b 是否开启
     */
    @Deprecated
    public void setAutoRefresh(boolean b){
        this.ba = b;
        if (ba){
            new BukkitRunnable(){
                @Override
                public void run() {
                    if (ba){
                        refresh();
                    }else {
                        cancel();
                    }
                }
            }.runTaskTimerAsynchronously(p,0,speed);
        }
    }

    /**
     * 设置刷新速度
     * @param i 速度
     */
    @Deprecated
    public void setSpeed(int i){
        this.speed = i;
    }
    /**
     * 展示记分板给玩家
     * @param player 玩家
     */
    public void To(Player player){
        player.setScoreboard(sc);
    }
}
