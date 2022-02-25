package net.bxx2004.pandalib.bukkit.pcommands;

import net.bxx2004.java.reflect.PJVariable;
import net.bxx2004.pandalib.bukkit.planguage.PHelper;
import net.bxx2004.pandalib.bukkit.planguage.PLangNode;
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
    public static HashMap<String,List<PSubcommands>> commandMap = new HashMap<>();
    private CommandSender commandSender;
    private String[] strings;
    public PHelper helper;
    private String name = this.getClass().getAnnotationsByType(BukkitCommand.class)[0].name();
    private String permission = this.getClass().getAnnotationsByType(BukkitCommand.class)[0].permission();
    private String[] aliases = this.getClass().getAnnotationsByType(BukkitCommand.class)[0].aliases();
    private void do2(List<PSubcommands> psubcommandsList){
        Iterator iterator = psubcommandsList.iterator();
        while(iterator.hasNext()) {
            PSubcommands sub = (PSubcommands) iterator.next();
            sub.performCommand(commandSender, strings);
        }
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
        if (permission != null){
            if (commandSender.hasPermission(permission)){
                this.strings = strings;
                if (commandMap.get(name) != null && strings.length >= 1){
                    do2(commandMap.get(name));
                    return false;
                }
            }
        }else {
            this.strings = strings;
            if (commandMap.get(name) != null && strings.length >= 1){
                do2(commandMap.get(name));
                return false;
            }
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

    private void hookHelper(PLangNode node){
        HashMap<String,String> map = new HashMap<>();
        if (this.helper != null){
            StringBuilder subcommanddesc = new StringBuilder();
            for (PSubcommands sub : commandMap.get(name)) {
                String usage = (String) new PJVariable(sub).getValue("usage");
                String description = (String) new PJVariable(sub).getValue("description");
                if (usage.contains("Node")){
                    String s = usage.split(":")[1];
                    usage = node.lang(s);
                }
                if (description.contains("Node")){
                    String s = description.split(":")[1];
                    description = node.lang(s);
                }
                subcommanddesc.append(usage + "-" +description).append(",");
            }
            String ali = "";
            for (String a : aliases){
                ali += a + "/";
            }
            map.put(name, permission+ "," + ali +"," + subcommanddesc);
        }
        if (commandMap.get(name) != null){
            StringBuilder subcommanddesc = new StringBuilder();
            for (PSubcommands sub : commandMap.get(name)) {
                String usage = (String) new PJVariable(sub).getValue("usage");
                String description = (String) new PJVariable(sub).getValue("description");
                if (usage.contains("Node")){
                    String s = usage.split(":")[1];
                    usage = node.lang(s);
                }
                if (description.contains("Node")){
                    String s = description.split(":")[1];
                    description = node.lang(s);
                }
                subcommanddesc.append(usage + "-" +description).append(",");
            }
            String ali = "";
            for (String a : aliases){
                ali += a + "/";
            }
            map.put(name, permission+ "," + ali +"," + subcommanddesc);
            helper = new PHelper(null, map);
        }
    }
    /**
     * 获取某个指令的所有命令帮助
     * @param node 节点多语言表达式
     * @param pCommand 指令
     * @param player player可为null , 不为null的情况下直接以中文形式发送命令帮助给玩家
     * @return 命令帮助
     */
    public static PHelper getCommandHelp(PLangNode node,PCommand pCommand, Player player){
        if (pCommand.helper == null){
            pCommand.hookHelper(node);
        }
        if (player != null){
            pCommand.helper.toPlayerOfKey(player, pCommand.name,true);
        }
        return pCommand.helper;
    }
}
