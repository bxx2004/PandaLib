package net.bxx2004.pandalib.bukkit.planguage;

import net.bxx2004.pandalib.bukkit.PandaLib;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 游戏动态对话窗体工具
 */
public class PDialog {
    private List<String> content;
    private HashMap<String,HashMap<List<String>,List<String>>> map;
    /**
     * 构造
     */
    public PDialog(){
        this.content = new ArrayList<String>();
        this.map = new HashMap<String, HashMap<List<String>,List<String>>>();
    }

    /**
     * 创建一个对话窗体
     * @param key 该窗体唯一钥
     */
    public void createDialog(String key){
        map.put(key, null);
    }

    /**
     * 为某个窗体设置内容
     * @param key 密钥
     * @param content 内容
     */
    public void setContent(String key, List<String> content){
        if (map.get(key) == null){
            map.replace(key,new HashMap<List<String>,List<String>>());
            setContent(key, content);
        }else {
            if (map.get(key).keySet().size() == 1){
                return;
            }else {
                map.get(key).put(PMessage.replace(content),null);
            }
        }
    }

    /**
     * 为某个窗体设置选项
     * @param key 密钥
     * @param option 选项内容(一行一个选项 [选项内容-执行指令])
     */
    public void setOption(String key, List<String> option){
        if (map.get(key) == null || map.get(key).keySet().size() != 1){
            return;
        }
        for (List<String> list : map.get(key).keySet()){
            map.get(key).replace(list, PMessage.replace(option));
        }
    }

    /**
     * 发送给玩家该对话
     * @param player 玩家
     * @param key 密钥
     */
    public void to(Player player, String key){
        for (int i = 0; i<150; i++){
            player.sendMessage(" ");
        }
        for (List<String> list : map.get(key).keySet()){
            PMessage.toList(player,list);
            player.sendMessage(" ");
            for (String s : map.get(key).get(list)){
                if (s.contains("=")){
                    String[] b = s.split("=");
                    PMessage.to2(player,b[0], ClickEvent.Action.RUN_COMMAND,"/" + b[1], b[0]);
                }else {
                    PMessage.to(player, s);
                }
            }
        }
    }

    /**
     * 发送一个带有书写特效的对话框
     * @param player 玩家
     * @param key 密钥
     * @param speed 速度
     */
    public void to2(Player player, String key,int speed){
        List<Character> c1 = new ArrayList<>();
        for (List<String> list : map.get(key).keySet()){
            for (String a : list){
                char[] c = a.toCharArray();
                for (int i = 0; i < c.length; i++){
                    c1.add(c[i]);
                }
            }
            for (String s : map.get(key).get(list)){
                if (s.contains("=")){
                    String[] p = s.split("=");
                    char[] c = p[0].toCharArray();
                    for (int i = 0; i < c.length; i++){
                        c1.add(c[i]);
                    }
                }else {
                    char[] c = s.toCharArray();
                    for (int i = 0; i < c.length; i++){
                        c1.add(c[i]);
                    }
                }
            }
        }
        Object[] content = c1.toArray();
        new BukkitRunnable(){
            int i = 0;
            String m = "";
            @Override
            public void run() {
                m += content[i];
                for (int i = 0; i < 100; i++){
                    player.sendMessage(" ");
                }
                PMessage.to(player, m);
                i = i+1;
                if (i == content.length){
                    for (int i = 0; i<100; i++){
                        player.sendMessage(" ");
                    }
                    for (List<String> list : map.get(key).keySet()){
                        PMessage.toList(player,list);
                        player.sendMessage(" ");
                        for (String s : map.get(key).get(list)){
                            if (s.contains("=")){
                                String[] b = s.split("=");
                                PMessage.to2(player,b[0], ClickEvent.Action.RUN_COMMAND,"/" + b[1], b[0]);
                            }else {
                                PMessage.to(player, s);
                            }
                        }
                    }
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(PandaLib.getInstance(),0,speed);
    }
}
