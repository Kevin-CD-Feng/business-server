package com.xtxk.cn.utils.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Administrator
 */
public class ListValuesValidator implements ConstraintValidator<ListValues, Integer> {

    private List<Integer> statusCodes;
    /**
     * Initializes the validator in preparation for
     * The constraint annotation for a given constraint declaration
     * is passed.
     * <p>
     * This method is guaranteed to be called before any use of this instance for
     * validation.
     * <p>
     * The default implementation is a no-op.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(ListValues constraintAnnotation) {
        statusCodes = Arrays.stream(constraintAnnotation.arrayValue()).boxed().collect(Collectors.toList());
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return statusCodes.stream().anyMatch(item -> item.equals(value));
    }

}
