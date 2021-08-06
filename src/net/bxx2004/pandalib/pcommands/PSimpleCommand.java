package net.bxx2004.pandalib.pcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 简单的命令构建类
 */
public abstract class PSimpleCommand {
    public CommandSender sender;
    public Command command;
    public String string;
    public String[] args;
    /**
     * 构建一个简单的命令
     * @param plugin 插件主类
     * @param name 插件名称
     */
    public PSimpleCommand(JavaPlugin plugin, String name){
        PSimpleCommandBody object = new PSimpleCommandBody(this);
        plugin.getCommand(name).setExecutor(object);
    }
    /**
     * 执行该命令的方法
     */
    abstract public void run();
}
class PSimpleCommandBody implements CommandExecutor{
    private PSimpleCommand commanda;
    public PSimpleCommandBody(PSimpleCommand command){
        this.commanda = command;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] strings) {
        commanda.sender = commandSender;
        commanda.command = command;
        commanda.string = string;
        commanda.args = strings;
        commanda.run();
        return false;
    }
}