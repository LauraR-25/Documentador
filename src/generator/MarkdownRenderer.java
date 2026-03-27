package generator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//  Renderiza la documentación de una clase en formato Markdown
final class MarkdownRenderer {

    //  Clase utilitaria con metodo estático para renderizar la documentación de una clase en Markdown
    private MarkdownRenderer() {
    }

    //  Renderiza la documentación de una clase en formato Markdown, incluyendo secciones para descripción,
    //  atributos, constructores y metodos
    static String render(ClassDoc doc) {
        StringBuilder sb = new StringBuilder();

        sb.append("# ").append(doc.simpleName).append("\n\n");
        sb.append("**Fully qualified name:** `").append(doc.qualifiedName).append("`\n\n");
        sb.append("**Generated:** ")
                .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()))
                .append("\n\n");

        sb.append("## Table of contents\n\n");
        sb.append("- [Overview](#overview)\n");
        sb.append("- [Class documentation](#class-documentation)\n");
        sb.append("- [Attributes](#attributes)\n");
        sb.append("- [Constructors](#constructors)\n");
        sb.append("- [Methods](#methods)\n\n");

        sb.append("## Overview\n\n");
        sb.append("|Property|Value|\n");
        sb.append("|---|---|\n");
        row(sb, "Name", doc.simpleName);
        row(sb, "Author", nullToDash(doc.author));
        row(sb, "Version", nullToDash(doc.version));
        row(sb, "Description", nullToDash(doc.description));
        row(sb, "Subclass", doc.isSubclass ? "Yes" : "No");
        if (doc.isSubclass) {
            row(sb, "Superclass", doc.superclassQualifiedName);
        }
        sb.append("\n");

        sb.append("## Class documentation\n\n");
        if (doc.description != null && !doc.description.isBlank()) {
            sb.append(doc.description).append("\n\n");
        } else {
            sb.append("(No description)\n\n");
        }

        sb.append("## Attributes\n\n");
        if (doc.fields.isEmpty()) {
            sb.append("(No attributes)\n\n");
        } else {
            sb.append("|Name|Type|Modifiers|Description|\n");
            sb.append("|---|---|---|---|\n");
            for (FieldDoc f : doc.fields) {
                sb.append("|`").append(f.name).append("`|`").append(f.type).append("`|")
                        .append(escape(f.modifiers)).append("|")
                        .append(escape(nullToDash(f.description))).append("|\n");
            }
            sb.append("\n");
        }

        sb.append("## Constructors\n\n");
        if (doc.constructors.isEmpty()) {
            sb.append("(No constructors)\n\n");
        } else {
            for (MethodDoc c : doc.constructors) {
                renderMethodLike(sb, c);
            }
        }

        sb.append("## Methods\n\n");
        if (doc.methods.isEmpty()) {
            sb.append("(No methods)\n");
        } else {
            for (MethodDoc m : doc.methods) {
                renderMethodLike(sb, m);
            }
        }

        return sb.toString();
    }

    //  Renderiza la documentación de un metodo o constructor en formato Markdown, incluyendo su firma, modificadores,
    //  tipo de retorno, descripción, parámetros y si es constructor/getter/setter/override
    private static void renderMethodLike(StringBuilder sb, MethodDoc m) {
        sb.append("### ").append(m.name).append("\n\n");
        sb.append("**Signature:** `").append(m.signature).append("`\n\n");

        sb.append("|Property|Value|\n");
        sb.append("|---|---|\n");
        row(sb, "Modifiers", escape(m.modifiers));
        row(sb, "Return type", m.returnType == null ? "(constructor)" : "`" + m.returnType + "`");
        row(sb, "Description", escape(nullToDash(m.description)));
        row(sb, "Is constructor", m.isConstructor ? "Yes" : "No");
        row(sb, "Is getter", m.isGetter ? "Yes" : "No");
        row(sb, "Is setter", m.isSetter ? "Yes" : "No");
        if (m.isGetter || m.isSetter) {
            row(sb, "Related attribute", m.relatedFieldName == null ? "-" : "`" + m.relatedFieldName + "`");
        }
        row(sb, "Overrides", m.isOverride ? "Yes" : "No");
        if (m.isOverride && m.overridesFrom != null) {
            row(sb, "Overrides from", "`" + m.overridesFrom + "`");
        }
        sb.append("\n");

        sb.append("**Parameters:**\n\n");
        if (m.parameters.isEmpty()) {
            sb.append("- (none)\n\n");
        } else {
            for (ParamDoc p : m.parameters) {
                sb.append("- `").append(p.name).append("`: `").append(p.type).append("` — ")
                        .append(escape(nullToDash(p.description))).append("\n");
            }
            sb.append("\n");
        }
    }

    //  Agrega una fila a una tabla Markdown, escapando caracteres especiales y manejando nulls como guiones
    private static void row(StringBuilder sb, String k, String v) {
        sb.append("|").append(escape(k)).append("|").append(escape(v)).append("|\n");
    }

    //  Convierte null o strings vacíos a un guion para mejor legibilidad en la tabla Markdown
    private static String nullToDash(String s) {
        return (s == null || s.isBlank()) ? "-" : s;
    }


    //  Escapa caracteres especiales en Markdown para evitar romper la sintaxis de tablas o líneas
    private static String escape(String s) {
        if (s == null) return "-";
        return s.replace("\r", " ")
                .replace("\n", " ")
                .replace("|", "\\|")
                .replace("\u00A0", " ");
    }
}
