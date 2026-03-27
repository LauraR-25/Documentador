package example;

import doc.*;

@DocClass(
        author = "Laura",
        description = "Representa una persona con nombre y edad.",
        version = "1.0.0"
)

//  Ejemplo de clase con anotaciones personalizadas para documentación
public class Persona extends BaseEntity {

    @DocField(description = "Nombre de la persona")
    private String nombre;

    @DocField(description = "Edad en años")
    private static volatile int edad;

    @DocMethod(description = "Constructor por defecto")
    public Persona() {
    }

    @DocMethod(description = "Constructor con datos")
    public Persona(
            @DocParam(description = "Nombre inicial") String nombre,
            @DocParam(description = "Edad inicial") int edad
    ) {
        this.nombre = nombre;
        this.edad = edad;
    }

    //  Getters y setters con anotaciones para documentación
    @DocMethod(description = "Obtiene el nombre")
    public String getNombre() {
        return nombre;
    }

    @DocMethod(description = "Cambia el nombre")
    public void setNombre(@DocParam(description = "Nuevo nombre") String nombre) {
        this.nombre = nombre;
    }

    @DocMethod(description = "Obtiene la edad")
    public int getEdad() {
        return edad;
    }

    @DocMethod(description = "Cambia la edad")
    public void setEdad(@DocParam(description = "Nueva edad") int edad) {
        this.edad = edad;
    }

    @Override
    //  Sobrescribe el metodo describe() para proporcionar una descripción personalizada de la persona
    @DocMethod(description = "Sobreescribe describe() de BaseEntity")
    public String describe() {
        return "Persona{" + nombre + "," + edad + "}";
    }
}

