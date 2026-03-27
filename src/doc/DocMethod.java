package doc;

import java.lang.annotation.*;

//Documenta un metodo.
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})

// Anotación DocMethod (se utiliza para documentar métodos y constructores) proporciona descripción de su funcionalidad
public @interface DocMethod {
    String description();
}

