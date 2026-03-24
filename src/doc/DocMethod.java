package doc;

import java.lang.annotation.*;

/**
 * Documenta un método.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface DocMethod {
    String description();
}

