package net.bxx2004.pandalib.bukkit.pcommands;

import net.bxx2004.pandalib.bukkit.planguage.PHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 更友好的命令系统(直接继承)
 */
public abstract class PCommand implements TabExecutor {
    private CommandSender commandSender;
    private String[] strings;
    private List<PSubcommands> subs;
    public PHelper helper;
    /**
     * 返回所有子命令类
     * @return 子命令集合
     */
    public List<PSubcommands> doCommand(){
        return null;
    }
    private void do2(List<PSubcommands> psubcommandsList){
        Iterator iterator = psubcommandsList.iterator();
        while(iterator.hasNext()) {
            PSubcommands sub = (PSubcommands) iterator.next();
            sub.performCommand(commandSender, strings);
        }
    }

    /**
     * 获取命令名,权限,别名 应把返回值写为 [CommandName-Permission-Allies]
     * @return 命令帮助内容
     */
    public String getName_Per_Allies(){
        return "CommandName-Permission-Allies";
    }
    /**
     * 同CommandExecutor一样
     * @param commandSender 命令发送者
     * @param command ...
     * @param s ...
     * @param strings ...
     * @return ...
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        this.commandSender = commandSender;
        this.strings = strings;
        if (subs == null){
            subs = doCommand();
        }
        if (subs != null && strings.length >= 1){
            do2(subs);
            return false;
        }
        return false;
    }

    /**
     * 同TabExecutor一样
     * @param commandSender 命令发送者
     * @param command ...
     * @param s ...
     * @param strings ...
     * @return ...
     */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
    /**
     * 注册命令
     * @param plugin 插件主类
     * @param name 命令名称
     * @param pcommands 命令类
     */
    public static void registerCommand(JavaPlugin plugin ,String name, PCommand pcommands){
        plugin.getCommand(name).setExecutor(pcommands);
    }

    private void hookHelper(){
        if (subs == null){
            subs = doCommand();
        }
        String s = getName_Per_Allies();
        HashMap<String,String> map = new HashMap<>();
        if (subs != null){
            StringBuilder subcommanddesc = new StringBuilder();
            for (PSubcommands sub : subs) {
                subcommanddesc.append(sub.getName() + "-" +sub.getDescription()).append(",");
            }
            map.put(s.split("-")[0], s.split("-")[1]+ "," + s.split("-")[2] +"," + subcommanddesc);
            helper = new PHelper(null, map);
        }
    }
    /**
     * 获取某个指令的所有命令帮助
     * @param pCommand 指令
     * @param player player可为null , 不为null的情况下直接以中文形式发送命令帮助给玩家
     * @return 命令帮助
     */
    public static PHelper getCommandHelp(PCommand pCommand, Player player){
        if (pCommand.helper == null){
            pCommand.hookHelper();
        }
        if (player != null){
            pCommand.helper.toPlayerOfKey(player, pCommand.getName_Per_Allies().split("-")[0],true);
        }
        return pCommand.helper;
    }
}
