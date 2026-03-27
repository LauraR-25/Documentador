package doc;

import java.lang.annotation.*;

// Documenta un parametro
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)

// Ejemplo de uso
public @interface DocParam {
    String description();
}

