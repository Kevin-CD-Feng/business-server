package com.xtxk.cn.utils.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListValuesValidator.class)
public @interface ListValues {

    String message() default "不在数组范围内";

    int[] arrayValue();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
