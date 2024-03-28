package com.xtxk.recognition.prepare.service.component.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AspectLogger {
    String value() default "";
}
