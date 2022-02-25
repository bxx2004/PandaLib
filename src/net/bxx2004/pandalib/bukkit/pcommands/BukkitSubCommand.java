package net.bxx2004.pandalib.bukkit.pcommands;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface BukkitSubCommand {
    String mainCommand();
    String usage();
    String permission();
    String description() default "";
}
