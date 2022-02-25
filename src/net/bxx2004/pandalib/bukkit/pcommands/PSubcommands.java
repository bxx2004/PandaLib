package net.bxx2004.pandalib.bukkit.pcommands;

import org.bukkit.command.CommandSender;

/**
 * 子命令抽象类
 */
public abstract class PSubcommands {
    /**
     * 子命令执行方法
     * @param sender 命令发送者
     * @param strings 参数
     * @return ...
     */
    public abstract boolean performCommand(CommandSender sender, String[] strings);
}
