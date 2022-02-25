package net.bxx2004.pandalib.bukkit.pfile;

import java.lang.annotation.*;
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BukkitResource {
    String path();
}
