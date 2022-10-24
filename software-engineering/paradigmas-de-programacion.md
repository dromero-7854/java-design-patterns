# Paradigmas de Programación

### ¿Qué es un paradigma?

Un paradigma es un modelo para resolver un problema de software. Son modelos, estilos o formas de trabajo para resolver un problema de software.

Ayudan a tener unos fundamentos y criterios a la hora de diseñar soluciones. El trabajar con un paradigma nos brinda la posibilidad de seguir un diseño o un modelo básico para el desarrollo de programas, con unas reglas y normas especificas con las cuales nosotros podemos trabajar en pro de facilitar la solución de nuestros propios problemas.

* Orden
* Forma
* Establece reglas
* Facilitan el proceso

## Programación imperativa

Es necesario describir paso a paso lo que el programa debe hacer y cómo debe hacerlo.

Dentro de la programación imperativa se desglosan otras:

* PE - Programación Estructurada
* POO - Programación Orientada a Objetos
* POE - Programación Orientada a Eventos
* POE - Programación Reactiva

### Pros y contras <a href="#pros-cons" id="pros-cons"></a>

:heavy\_check\_mark:  Más fácil de entender.

:heavy\_check\_mark:  Recomendada para iniciar.

:heavy\_check\_mark:  Facilidad de modificación.

:heavy\_check\_mark:  Escalable.

:heavy\_multiplication\_x:  El código crece rápidamente.

:heavy\_multiplication\_x:  Difícil de optimizar.

## Programación declarativa

Especifica que se tiene hacer. Se centra en el resultado más que en el proceso.

Busca crear el resultado, sin necesidad de mostrar todos los pasos necesarios para el mismo.

Dentro de la programación declarativa se desglosan otras:

* PL - Programación Lógica
* PL - Programación Funcional
* POC - Programación Orientada a Componentes

### Pros y contras <a href="#pros-cons" id="pros-cons"></a>

:heavy\_check\_mark:  Muy optimizable.

:heavy\_check\_mark:  Basada totalmente en algoritmos.

:heavy\_multiplication\_x:  Mas difícil de entender.

:heavy\_multiplication\_x:  Alejada de la forma natural de análisis.

## Paradigma Funcional

La programación funcional es un paradigma de programación declarativo (se centra en el qué y no en el cómo)

Esta basada en el **cálculo lambda** y se caracteriza por concebir un programa como una colección de funciones matemáticas.

### Características principales

* **Funciones puras**: **No existen los efectos laterales**. Un _efecto lateral_ ocurre cuando la ejecución de una función influye en la ejecución de otra función.

```lisp
 // Código NO FUNCIONAL con efecto lateral
 int x = 0;
 int foo(int y) {
  x++;
  return x + y;
 }
 int boo(int y) {
  x++;
  return x + y;
 }

 foo(3) = 4
 foo(3) = 5
 boo(3) = 6 // La ejecución previa de foo a modificado la variable global, y por lo tanto influye en la ejecución de boo
 foo(3) = 7 
```

Esto es muy malo para la depuración, porque quiere decir que cuando una función va mal, puede ser que el resultado incorrecto sea por la propia función, o por cualquier otra función del sistema que la ha influido.

En la programación funcional por tanto, **no existe el concepto de variable global**. El resultado de una función solamente depende de los parámetros de entrada.&#x20;

* **Aplicación parcial**: Puede usarse una función son sólo algunos de sus argumentos.

```lisp
doble x = x + x
cuadruple = doble . doble 
```

* **Orden Superior**: Las funciones son tratadas como si fueran datos. Se puede pasar una función a otra función, o una función puede devolver como resultado otra función.

```
doble x = x + x
foo x y = x y // Asumo que "x" es una función e "y" es una dato

foo doble 4 = doble 4 = 8  
```

* **Recursividad**: No existen los bucles iterativos (for, while, do while)
* **Inferencia de tipos**: Los tipos no son necesariamente explícitos. Un sistema de inferencia de tipos infiere el tipo de las expresiones.

```
doble x = x + x
```

* **Pereza**: Una expresión sólo se evalúa si es necesario.

```
foo x = 42
doble x = x + x
foo(doble 4) = 42 // Nunca se ejecuta "doble 4", porque no necesita ejecutarlo para saber el resultado
```

### Resumiendo

* El paradigma funcional es un **paradigma declarativo**.
* Está basado en el **cálculo lambda**.
* Un programa funcional es una colección de **funciones puras**.
* Los programas funcionales **suelen ser más fáciles de depurar en comparación a los programas imperativos gracias a la ausencia de efectos laterales**.
