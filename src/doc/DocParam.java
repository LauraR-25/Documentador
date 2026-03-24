package doc;

import java.lang.annotation.*;

/**
 * Documenta un parámetro.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DocParam {
    String description();
}

