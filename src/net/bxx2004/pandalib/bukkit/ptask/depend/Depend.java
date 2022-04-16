package net.bxx2004.pandalib.bukkit.ptask.depend;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
/**
 * 依赖任务器注解
 */
public @interface Depend {
    /**
     * 插件名称
     */
    String name();

    /**
     * 插件版本
     */
    String version() default "all";

    /**
     * 是否异步
     */
    boolean asynchronous() default false;
}
