package net.bxx2004.pandalib.bukkit.pcommands;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface BukkitSubCommand {
    /**
     * 主命令名
     */
    String mainCommand();

    /**
     * 用法
     */
    String usage();

    /**
     * 权限
     */
    String permission();

    /**
     * 描述
     */
    String description() default "";
}
