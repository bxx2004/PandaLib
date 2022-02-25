package net.bxx2004.pandalib.bukkit.pcommands;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface BukkitCommand {
    String name();
    String permission();
    String[] aliases();
    String permissionMessage();
}
