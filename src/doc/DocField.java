package doc;

import java.lang.annotation.*;

/**
 * Documenta un atributo.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DocField {
    String description();
}

