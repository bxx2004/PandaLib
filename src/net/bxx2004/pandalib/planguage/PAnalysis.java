package net.bxx2004.pandalib.planguage;

import net.bxx2004.pandalib.manager.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * 执行特殊的动作
 * @since 1.5.3
 */
public abstract class PAnalysis{
    private static HashMap<String,PAnalysisObject> map = new HashMap<String, PAnalysisObject>();
    public PAnalysis(String key){
        map.put(key, new PAnalysisObject() {
            @Override
            void vaule(Player player1,String... args) {
                run(player1, args);
            }
        });
    }
    public abstract void run(Player player,String... args);

    /**
     * 执行语法
     * @param word 语法
     * @param player 玩家
     */
    public static void go(String word,Player player){
        String words = word.split(" ")[0];
        String[] args = word.split(" ");
        String[] args1 = new String[args.length - 1];
        for (int i = 0; i < args.length; i++){
            if (i >= args1.length){
                continue;
            }else {
                args1[i] = args[i+1];
            }
        }
        if (!map.keySet().contains(words)){
            Lang.print("&c[&fPAnalysis&c] &c执行失败,该动作 &f" + words +" &c无效...");
        }else {
            map.get(words).vaule(player,args1);
        }
    }
    /**
     * 执行特殊的动作
     * @param player 玩家
     * @param text 文本
     */
    @Deprecated
    public static void go(Player player, String text){
        String[] t = text.split(" ");
        String t1 = t[1].replaceAll("<player>",player.getName());
        if (t[0].equals("[MESSAGE]")){
            PMessage.to(player,t1);
        }
        if (t[0].equals("[TITLE]")){
            PTitle.To(player,t1);
        }
        if (t[0].equals("[ACTIONBAR]")){
            PActionBar.To(player,t1);
        }
        if (t[0].equals("[COMMAND=PLAYER]")){
            Bukkit.dispatchCommand(player, t1);
        }
        if (t[0].equals("[COMMAND=CONSOLE]")){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), t1);
        }
        if (t[0].equals("[CLOSE]")){
            player.closeInventory();
        }
        if (t[0].equals("[COMMAND=OP]")){
            if (player.isOp()){
                Bukkit.dispatchCommand(player, t1);
            }else {
                player.setOp(true);
                Bukkit.dispatchCommand(player, t1);
                player.setOp(false);
            }
        }
    }
}
abstract class PAnalysisObject{
    abstract void vaule(Player player,String... args);
}