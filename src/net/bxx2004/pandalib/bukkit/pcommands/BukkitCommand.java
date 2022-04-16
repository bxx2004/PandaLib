package net.bxx2004.pandalib.bukkit.pcommands;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
/**
 * 命令注解
 */
public @interface BukkitCommand {
    /**
     * 命令名
     */
    String name();

    /**
     * 权限
     */
    String permission();

    /**
     * 别名
     */
    String[] aliases();

    /**
     * 权限消息
     */
    String permissionMessage();
}
