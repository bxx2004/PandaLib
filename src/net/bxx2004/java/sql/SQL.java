package net.bxx2004.java.sql;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SQL {
    SQLPlatForm platform();
    SQLBase.Type type();
}
