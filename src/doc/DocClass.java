package doc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Documenta una clase.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

// Define una anotación para documentar una clase, incluyendo autor, descripción y versión
public @interface DocClass {
    String author();

    String description();

    String version();
}
