package com.github.choonchernlim.statemachine.mailing;

import org.springframework.statemachine.annotation.OnTransition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta annotation to allow Enum usage on @OnTransition instead of String.
 * <p>
 * See https://docs.spring.io/spring-statemachine/docs/current/reference/html/sm-context.html#transition-annotations
 */
// Must be written using Java because Groovy throws this error:
// Groovy:Annotation @org.springframework.statemachine.annotation.OnTransition is not allowed on element ANNOTATION
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnTransition
public @interface MailingOnTransition {
    MailingMetadata.State[] source() default {};

    MailingMetadata.State[] target() default {};
}
