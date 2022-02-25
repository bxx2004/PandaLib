package net.bxx2004.pandalib.bukkit.ptask.depend;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Depend {
    String name();
    String version() default "all";
    boolean asynchronous() default false;
}
