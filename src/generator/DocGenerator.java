package generator;

import doc.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

final class DocGenerator {

    //  Genera la documentación de una clase a partir de su reflexión y anotaciones
    ClassDoc generateFor(Class<?> clazz) {
        DocClass cAnn = clazz.getAnnotation(DocClass.class);

        String author = cAnn != null ? cAnn.author() : null;
        String version = cAnn != null ? cAnn.version() : null;
        String description = cAnn != null ? cAnn.description() : null;

        boolean isSubclass = clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class;
        String superclassName = isSubclass ? clazz.getSuperclass().getName() : "-";

        //  Mapeo de campos por nombre para detección de getters/setters
        Map<String, Field> declaredFields = new LinkedHashMap<>();
        for (Field f : clazz.getDeclaredFields()) {
            declaredFields.put(f.getName(), f);
        }

        //  Procesa campos, constructores y métodos, ordenándolos y extrayendo anotaciones
        List<FieldDoc> fields = Arrays.stream(clazz.getDeclaredFields())
                .sorted(Comparator.comparing(Field::getName))
                .map(f -> {
                    DocField fAnn = f.getAnnotation(DocField.class);
                    String fd = fAnn != null ? fAnn.description() : null;
                    return new FieldDoc(
                            f.getName(),
                            typeToString(f.getGenericType()),
                            modifiersToString(f.getModifiers()),
                            fd
                    );
                })
                .collect(Collectors.toList());

        //  Ordena constructores por número de parámetros y métodos por nombre y número de parámetros
        List<MethodDoc> constructors = Arrays.stream(clazz.getDeclaredConstructors())
                .sorted(Comparator.comparingInt(Constructor::getParameterCount))
                .map(c -> methodFromConstructor(clazz, c, declaredFields))
                .collect(Collectors.toList());

        //  Filtra métodos sintéticos (como los generados para lambdas) y ordena por nombre y número de parámetros
        List<MethodDoc> methods = Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> !m.isSynthetic())
                .sorted(Comparator.comparing(Method::getName).thenComparingInt(Method::getParameterCount))
                .map(m -> methodFromMethod(clazz, m, declaredFields))
                .collect(Collectors.toList());


        //  Crea el objeto ClassDoc con toda la información recopilada
        return new ClassDoc(
                clazz.getName(),
                clazz.getSimpleName(),
                author,
                description,
                version,
                isSubclass,
                superclassName,
                fields,
                constructors,
                methods
        );
    }

    //  Genera un MethodDoc a partir de un Constructor, extrayendo anotaciones y creando la firma
    private MethodDoc methodFromConstructor(Class<?> clazz, Constructor<?> c, Map<String, Field> fieldsByName) {
        DocMethod mAnn = c.getAnnotation(DocMethod.class);
        String desc = mAnn != null ? mAnn.description() : null;

        List<ParamDoc> params = paramsFromExecutable(c);

        String signature = signatureForConstructor(clazz, c);

        return new MethodDoc(
                clazz.getSimpleName(),
                signature,
                modifiersToString(c.getModifiers()),
                null,
                desc,
                false,
                false,
                null,
                true,
                false,
                null,
                params
        );
    }

    //  " " Method, detectando getters/setters, overrides y extrayendo anotaciones
    private MethodDoc methodFromMethod(Class<?> clazz, Method m, Map<String, Field> fieldsByName) {
        DocMethod mAnn = m.getAnnotation(DocMethod.class);
        String desc = mAnn != null ? mAnn.description() : null;

        List<ParamDoc> params = paramsFromExecutable(m);

        String signature = signatureForMethod(m);

        GetterSetterInfo gs = detectGetterSetter(clazz, m, fieldsByName);

        OverrideInfo ovr = detectOverride(clazz, m);

        return new MethodDoc(
                m.getName(),
                signature,
                modifiersToString(m.getModifiers()),
                typeToString(m.getGenericReturnType()),
                desc,
                gs.isGetter,
                gs.isSetter,
                gs.relatedField,
                false,
                ovr.isOverride,
                ovr.from,
                params
        );
    }

    //  Extrae la lista de parámetros de un metodo o constructor, incluyendo su tipo y descripción si está anotado
    private List<ParamDoc> paramsFromExecutable(Executable exec) {
        Parameter[] params = exec.getParameters();
        List<ParamDoc> out = new ArrayList<>();
        for (Parameter p : params) {
            DocParam pAnn = p.getAnnotation(DocParam.class);
            String pd = pAnn != null ? pAnn.description() : null;
            out.add(new ParamDoc(p.getName(), typeToString(p.getParameterizedType()), pd));
        }
        return out;
    }

    //  Detecta si el metodo es un getter o setter basado en su firma y nombre, y si corresponde a un campo declarado.
    private GetterSetterInfo detectGetterSetter(Class<?> clazz, Method m, Map<String, Field> fieldsByName) {
        //  Detecta si el metodo es un getter o setter basado en su firma y nombre, y si corresponde a un campo declarado.
        String name = m.getName();
        if (m.getParameterCount() == 0 && m.getReturnType() != Void.TYPE) {
            String field = null;
            if (name.startsWith("get") && name.length() > 3) {
                field = decap(name.substring(3));
            } else if (name.startsWith("is") && name.length() > 2 && (m.getReturnType() == boolean.class || m.getReturnType() == Boolean.class)) {
                field = decap(name.substring(2));
            }
            if (field != null && fieldsByName.containsKey(field)) {
                return new GetterSetterInfo(true, false, field);
            }
        }
        if (m.getParameterCount() == 1 && m.getReturnType() == Void.TYPE && name.startsWith("set") && name.length() > 3) {
            String field = decap(name.substring(3));
            if (fieldsByName.containsKey(field)) {
                return new GetterSetterInfo(false, true, field);
            }
        }
        return new GetterSetterInfo(false, false, null);
    }

    //  Detecta si un metodo es un override basado en la presencia de @Override o si existe un metodo con la misma firma en la superclase.
    private OverrideInfo detectOverride(Class<?> clazz, Method m) {
        // Consider override if @Override present OR method exists with same signature in superclass.
        boolean hasOverrideAnn = m.getAnnotation(Override.class) != null;
        Class<?> sc = clazz.getSuperclass();
        if (sc != null && sc != Object.class) {
            try {
                Method sm = sc.getMethod(m.getName(), m.getParameterTypes());
                if (!sm.getDeclaringClass().equals(clazz)) {
                    return new OverrideInfo(true, sm.getDeclaringClass().getName());
                }
            } catch (NoSuchMethodException ignored) {
            }
        }
        return new OverrideInfo(hasOverrideAnn, hasOverrideAnn ? "(unknown)" : null);
    }

    //  Convierte la primera letra de una cadena a minúscula, usada para detectar el nombre del campo relacionado en getters/setters.
    private static String decap(String s) {
        if (s.isEmpty()) return s;
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }

    //  Convierte los modificadores de un campo o metodo a una cadena legible, indicando "(package-private)" si no tiene modificadores explícitos.
    private static String modifiersToString(int mod) {
        String s = Modifier.toString(mod);
        return (s == null || s.isBlank()) ? "(package-private)" : s;
    }

    //  Convierte un tipo a su representación de cadena, reemplazando '$' por '.' para clases internas.
    private static String typeToString(Type t) {
        return t.getTypeName().replace('$', '.');
    }
    private static String signatureForMethod(Method m) {
        String params = Arrays.stream(m.getGenericParameterTypes())
                .map(DocGenerator::typeToString)
                .collect(Collectors.joining(", "));
        return m.getName() + "(" + params + ")";
    }

    //  Genera la firma de un constructor usando el nombre de la clase y los tipos de parámetros, formateados como "ClassName(paramType1, paramType2, ...)".
    private static String signatureForConstructor(Class<?> clazz, Constructor<?> c) {
        String params = Arrays.stream(c.getGenericParameterTypes())
                .map(DocGenerator::typeToString)
                .collect(Collectors.joining(", "));
        return clazz.getSimpleName() + "(" + params + ")";
    }


    //  Clases auxiliares para almacenar información sobre getters/setters y overrides detectados durante el análisis de metodos.
    private static final class GetterSetterInfo {
        final boolean isGetter;
        final boolean isSetter;
        final String relatedField;

        GetterSetterInfo(boolean isGetter, boolean isSetter, String relatedField) {
            this.isGetter = isGetter;
            this.isSetter = isSetter;
            this.relatedField = relatedField;
        }
    }

    //  Considera override si @Override presente O metodo existe con la misma firma en la superclase.
    private static final class OverrideInfo {
        final boolean isOverride;
        final String from;

        OverrideInfo(boolean isOverride, String from) {
            this.isOverride = isOverride;
            this.from = from;
        }
    }
}

