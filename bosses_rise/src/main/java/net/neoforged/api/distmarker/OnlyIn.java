package net.neoforged.api.distmarker;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface OnlyIn {
    Dist value();
    Class<?> _interface() default Object.class;
}
