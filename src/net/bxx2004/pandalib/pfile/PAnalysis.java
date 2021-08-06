package net.bxx2004.pandalib.pfile;

import net.bxx2004.pandalib.planguage.PActionBar;
import net.bxx2004.pandalib.planguage.PMessage;
import net.bxx2004.pandalib.planguage.PTitle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * 执行特殊的动作
 * @since 1.5.3
 */
public class PAnalysis {
    public PAnalysis(){

    }

    /**
     * 执行特殊的动作
     * @param player 玩家
     * @param text 文本
     */
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
