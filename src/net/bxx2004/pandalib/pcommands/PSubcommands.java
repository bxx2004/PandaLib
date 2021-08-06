package net.bxx2004.pandalib.pcommands;

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

    /**
     * 返回子命令描述
     * @return "命令描述"
     */
    abstract public String getDescription();

    /**
     * 返回子命令名
     * @return 子命令名
     */
    abstract public String getName();
}
