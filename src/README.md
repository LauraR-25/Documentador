# Proyecto 4 (Programación Visual): Generador de documentación

## Objetivo
Programa que recibe una clase Java y genera un archivo **Markdown** con la documentación de:
- **Clase**: nombre, autor, descripción, versión, si es subclase.
- **Métodos**: parámetros, tipo de retorno, descripción, modificadores, si es getter/setter, si es constructor, si es sobreescrito.
- **Atributos**: tipo, descripción, modificadores.

La salida sigue un esquema inspirado en la referencia tipo Compodoc:
https://compodoc.github.io/compodoc-demo-nestjs/classes/ArticleEntity.html

## Anotaciones personalizadas
- `@doc.DocClass(author, description, version)`
- `@doc.DocField(description)`
- `@doc.DocMethod(description)` (métodos y constructores)
- `@doc.DocParam(description)` (parámetros)

> Nota: para que Java mantenga los nombres reales de parámetros en reflection, se recomienda compilar con `-parameters`.

## Ejemplo incluido
Clase: `example.Persona` (extiende `example.BaseEntity`).

## Cómo ejecutar (sin Maven/Gradle)
Desde la raíz del proyecto (carpeta donde está `src/`):

1) Compilar:
- Windows PowerShell:
  - `javac -parameters -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java .\src | ForEach-Object FullName)`

2) Generar markdown:
- `java -cp out generator.Main example.Persona Persona.md`

Esto crea `Persona.md` en la raíz.

