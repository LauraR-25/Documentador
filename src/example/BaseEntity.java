package example;

import doc.DocClass;
import doc.DocMethod;

//  Clase base de ejemplo para demostrar el uso de anotaciones de documentación
@DocClass(author = "Materia PV", description = "Clase base de ejemplo.", version = "1.0")
public class BaseEntity {

    @DocMethod(description = "Devuelve un texto base.")
    public String describe() {
        return "base";
    }
}

