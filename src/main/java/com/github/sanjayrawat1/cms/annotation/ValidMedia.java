package com.github.sanjayrawat1.cms.annotation;

import com.github.sanjayrawat1.cms.annotation.validator.MediaValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Sanjay Singh Rawat
 */
@Documented
@Constraint(validatedBy = MediaValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMedia {
    String message() default "Invalid media file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
