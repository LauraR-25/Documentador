# Persona

**Fully qualified name:** `example.Persona`

**Generated:** 2026-03-24T01:41:17.7662578

## Table of contents

- [Overview](#overview)
- [Class documentation](#class-documentation)
- [Attributes](#attributes)
- [Constructors](#constructors)
- [Methods](#methods)

## Overview

|Property|Value|
|---|---|
|Name|Persona|
|Author|Laura|
|Version|1.0.0|
|Description|Representa una persona con nombre y edad.|
|Subclass|Yes|
|Superclass|example.BaseEntity|

## Class documentation

Representa una persona con nombre y edad.

## Attributes

|Name|Type|Modifiers|Description|
|---|---|---|---|
|`edad`|`int`|private|Edad en años|
|`nombre`|`java.lang.String`|private|Nombre de la persona|

## Constructors

### Persona

**Signature:** `Persona()`

|Property|Value|
|---|---|
|Modifiers|public|
|Return type|(constructor)|
|Description|Constructor por defecto|
|Is constructor|Yes|
|Is getter|No|
|Is setter|No|
|Overrides|No|

**Parameters:**

- (none)

### Persona

**Signature:** `Persona(java.lang.String, int)`

|Property|Value|
|---|---|
|Modifiers|public|
|Return type|(constructor)|
|Description|Constructor con datos|
|Is constructor|Yes|
|Is getter|No|
|Is setter|No|
|Overrides|No|

**Parameters:**

- `arg0`: `java.lang.String` — Nombre inicial
- `arg1`: `int` — Edad inicial

## Methods

### describe

**Signature:** `describe()`

|Property|Value|
|---|---|
|Modifiers|public|
|Return type|`java.lang.String`|
|Description|Sobreescribe describe() de BaseEntity|
|Is constructor|No|
|Is getter|No|
|Is setter|No|
|Overrides|Yes|
|Overrides from|`example.BaseEntity`|

**Parameters:**

- (none)

### getEdad

**Signature:** `getEdad()`

|Property|Value|
|---|---|
|Modifiers|public|
|Return type|`int`|
|Description|Obtiene la edad|
|Is constructor|No|
|Is getter|Yes|
|Is setter|No|
|Related attribute|`edad`|
|Overrides|No|

**Parameters:**

- (none)

### getNombre

**Signature:** `getNombre()`

|Property|Value|
|---|---|
|Modifiers|public|
|Return type|`java.lang.String`|
|Description|Obtiene el nombre|
|Is constructor|No|
|Is getter|Yes|
|Is setter|No|
|Related attribute|`nombre`|
|Overrides|No|

**Parameters:**

- (none)

### setEdad

**Signature:** `setEdad(int)`

|Property|Value|
|---|---|
|Modifiers|public|
|Return type|`void`|
|Description|Cambia la edad|
|Is constructor|No|
|Is getter|No|
|Is setter|Yes|
|Related attribute|`edad`|
|Overrides|No|

**Parameters:**

- `arg0`: `int` — Nueva edad

### setNombre

**Signature:** `setNombre(java.lang.String)`

|Property|Value|
|---|---|
|Modifiers|public|
|Return type|`void`|
|Description|Cambia el nombre|
|Is constructor|No|
|Is getter|No|
|Is setter|Yes|
|Related attribute|`nombre`|
|Overrides|No|

**Parameters:**

- `arg0`: `java.lang.String` — Nuevo nombre

