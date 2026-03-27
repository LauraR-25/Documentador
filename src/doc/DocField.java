package doc;

import java.lang.annotation.*;

// Documenta un atributo
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

// Permite describir un campo con una descripción para documentación automática
public @interface DocField {
    String description();
}

