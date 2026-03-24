package generator;

import java.util.*;

final class ClassDoc {
    final String qualifiedName;
    final String simpleName;
    final String author;
    final String description;
    final String version;
    final boolean isSubclass;
    final String superclassQualifiedName;

    final List<FieldDoc> fields;
    final List<MethodDoc> constructors;
    final List<MethodDoc> methods;

    ClassDoc(
            String qualifiedName,
            String simpleName,
            String author,
            String description,
            String version,
            boolean isSubclass,
            String superclassQualifiedName,
            List<FieldDoc> fields,
            List<MethodDoc> constructors,
            List<MethodDoc> methods
    ) {
        this.qualifiedName = qualifiedName;
        this.simpleName = simpleName;
        this.author = author;
        this.description = description;
        this.version = version;
        this.isSubclass = isSubclass;
        this.superclassQualifiedName = superclassQualifiedName;
        this.fields = Collections.unmodifiableList(new ArrayList<>(fields));
        this.constructors = Collections.unmodifiableList(new ArrayList<>(constructors));
        this.methods = Collections.unmodifiableList(new ArrayList<>(methods));
    }
}

final class FieldDoc {
    final String name;
    final String type;
    final String modifiers;
    final String description;

    FieldDoc(String name, String type, String modifiers, String description) {
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
        this.description = description;
    }
}

final class MethodDoc {
    final String name;
    final String signature;
    final String modifiers;
    final String returnType; // null for constructors
    final String description;
    final boolean isGetter;
    final boolean isSetter;
    final String relatedFieldName;
    final boolean isConstructor;
    final boolean isOverride;
    final String overridesFrom;
    final List<ParamDoc> parameters;

    MethodDoc(
            String name,
            String signature,
            String modifiers,
            String returnType,
            String description,
            boolean isGetter,
            boolean isSetter,
            String relatedFieldName,
            boolean isConstructor,
            boolean isOverride,
            String overridesFrom,
            List<ParamDoc> parameters
    ) {
        this.name = name;
        this.signature = signature;
        this.modifiers = modifiers;
        this.returnType = returnType;
        this.description = description;
        this.isGetter = isGetter;
        this.isSetter = isSetter;
        this.relatedFieldName = relatedFieldName;
        this.isConstructor = isConstructor;
        this.isOverride = isOverride;
        this.overridesFrom = overridesFrom;
        this.parameters = Collections.unmodifiableList(new ArrayList<>(parameters));
    }
}

final class ParamDoc {
    final String name;
    final String type;
    final String description;

    ParamDoc(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }
}

