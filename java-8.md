# Java 8

### Paradigma Funcional

Ver [Paradigma Funcional](software-engineering/paradigmas-de-programacion.md#paradigma-funcional)

### Expresiones Lambda

* Funciones anónimas
* Esenciales para entender la programación declarativa
* parámetros => expresión

```java
package com.software_engineering.java8;

public interface Operacion {
  double calcularPromedio(double n1, double n2);
}

```

```java
package com.software_engineering.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LambdaApp {

  public void ordenarModoImperativo() {
    List < String > lista = new ArrayList < String > ();
    lista.add("MitoCode");
    lista.add("Code");
    lista.add("Mito");
    // Modo Imperativo
    Collections.sort(lista, new Comparator < String > () {
      @Override
      public int compare(String o1, String o2) {
        return o1.compareTo(o2);
      }
    });
    System.out.println("Ordenar lista Modo Imperativo");
    for (String elemento: lista)
      System.out.println(elemento);
  }

  public void ordenarModoDeclarativo() {
    List < String > lista = new ArrayList < String > ();
    lista.add("MitoCode");
    lista.add("Code");
    lista.add("Mito");
    // Modo Declarativo
    Collections.sort(lista, (String p1, String p2) -> p1.compareTo(p2));
    System.out.println("Ordenar lista Modo Declarativo");
    for (String elemento: lista)
      System.out.println(elemento);
  }

  public void calcular() {
    // Modo Imperativo
    Operacion operacionMI = new Operacion() {
      @Override
      public double calcularPromedio(double n1, double n2) {
        return (n1 + n2) / 2;
      }
    };
    System.out.println("Calcular Promedio - Modo Imperativo - calcular promedio (2, 3) = " + operacionMI.calcularPromedio(2, 3));
    Operacion operacionMD = (double x, double y) -> (x + y) / 2;
    System.out.println("Calcular Promedio - Modo Declarativo - calcular promedio (2, 3) = " + operacionMD.calcularPromedio(2, 3));
  }

  public static void main(String[] args) {
    LambdaApp app = new LambdaApp();
    app.ordenarModoImperativo();
    app.ordenarModoDeclarativo();
    app.calcular();
  }

}
```

```
Ordenar lista Modo Imperativo
Code
Mito
MitoCode
Ordenar lista Modo Declarativo
Code
Mito
MitoCode
Calcular Promedio - Modo Imperativo - calcular promedio (2, 3) = 2.5
Calcular Promedio - Modo Declarativo - calcular promedio (2, 3) = 2.5
```

### Sintaxis Expresiones Lambda

```java
package com.software_engineering.java8;

public interface OperacionSinParametros {
  double calcularPromedio();
}
```

```java
package com.software_engineering.java8;

public class SintaxisLambda {

  public double probarSintaxis01() {
    Operacion operacion = (double x, double y) -> (x + y) / 2;
    return operacion.calcularPromedio(2, 3);
  }

  public double probarSintaxis02() {
    Operacion operacion = (double x, double y) -> {
      return (x + y) / 2;
    }; // Se utiliza cuando necesito definir más de una linea de código (mala práctica)
    return operacion.calcularPromedio(2, 3);
  }

  public double probarSintaxis03() {
    Operacion operacion = (x, y) -> (x + y) / 2;
    return operacion.calcularPromedio(2, 3);
  }

  public double probarSintaxis04() {
    OperacionSinParametros operacion = () -> 2;
    return operacion.calcularPromedio();
  }

  public double probarSintaxis05() {
    OperacionSinParametros operacion = () -> {
      int x = 2;
      int y = 3;
      return x + y;
    };
    return operacion.calcularPromedio();
  }

  public static void main(String[] args) {
    SintaxisLambda app = new SintaxisLambda();
    System.out.println(app.probarSintaxis01());
    System.out.println(app.probarSintaxis02());
    System.out.println(app.probarSintaxis03());
    System.out.println(app.probarSintaxis04());
    System.out.println(app.probarSintaxis05());
  }

}
```

```
2.5
2.5
2.5
2.0
5.0
```

### Scope (Ámbito o alcance)

```java
package com.software_engineering.java8.lambda.scope;

public class Scopes {

  private static double atributo1;
  private double atributo2;

  public double probarVariableLocal() {
    final double n3 = 3;
    Operacion op = new Operacion() {
      //
      // Clase Anónima
      // 
      @Override
      public double calcular(double n1, double n2) {
        // Cuando trabajamos en una clase anónima y necesitamos hacer referencia a una variable local
        // es necesario que la variable local (en este caso "n3") esté definida como 'final' 
        // o se comporte como una variable 'final' (en el código, debe haber un solo lugar en el que le asignemos un valor)
        return n1 + n2 + n3;
      }
    };
    return op.calcular(1, 1);
  }

  public double probarVariableLocalConLambda() {
    final double n3 = 3;
    Operacion op = (x, y) -> {
      // El concepto de una variable local en una expresión lambda se mantiene igual que para una clase anónima
      // Cuando estamos en una expresión lambda y utilizamos una variable local es opcional definirla como 'final'
      // Si la variable local no se define como 'final' y se utiliza en la expresión lambda, la variable se va a comportar
      // como una variable 'final'
      return x + y + n3;
    };
    return op.calcular(1, 1);
  }

  // Las variables estáticas, los atributos de una clase y las variables locales, tienen el mismo comportamiento que tendrían dentro de una clase anónima

  public double probarVariablesEstaticas() {
    Operacion op = (x, y) -> {
      atributo1 = x + y;
      return atributo1;
    };
    return op.calcular(3, 2);
  }

  public double probarAtributos() {
    Operacion op = (x, y) -> {
      atributo2 = x + y;
      return atributo2;
    };
    return op.calcular(3, 2);
  }

  public static void main(String[] args) {
    Scopes app = new Scopes();
    System.out.println(app.probarVariableLocal());
    System.out.println(app.probarVariableLocalConLambda());
    System.out.println(app.probarVariablesEstaticas());
    System.out.println(app.probarAtributos());
  }

}
```

```
5.0
5.0
5.0
5.0
```

### Default Methods

```java
package com.software_engineering.java8.default_method;

public interface PersonaA {

  public void caminar();

  default public void hablar() {
    System.out.println("Saludos!!!");
  }

}
```

```java
package com.software_engineering.java8.default_method;

public interface PersonaB {

  default public void hablar() {
    System.out.println("Hellooooo!!!");
  }

}
```

```java
package com.software_engineering.java8.default_method;

public class DefaultMethod implements PersonaA, PersonaB {

  @Override
  public void caminar() {
    System.out.println("Caminar");
  }

  // Si implemento otra interfaz que tambien tenga un default method "hablar", el compilador va a dar una excepción
  // "Duplicate default methods named hablar with the parameters () and () are inherited from the types PersonaB and PersonaA"
  // De esta manera puedo definir que default method quiero usar
  // ⤋
  @Override
  public void hablar() {
    PersonaA.super.hablar();
  }
  // Otra opción es sobreescribir el default method

  public static void main(String[] args) {
    DefaultMethod app = new DefaultMethod();
    app.caminar();
    app.hablar();
  }

}
```

### Interfaces Funcionales

```java
package com.software_engineering.java8.functional_interfaces;

//
// Una interfaz funcional es aquella que solo define un sólo método
// De esta manera se formaliza en java8 una interfaz funcional
//
@FunctionalInterface
public interface Operacion {

  double calcularPromedio(double n1, double n2);

}
```

```java
package com.software_engineering.java8.functional_interfaces;

public class FunctionalInterfacesApp {

  public double operar(double x, double y) {
    Operacion op = (n1, n2) -> n1 + n2;
    return op.calcularPromedio(x, y);
  }

  public static void main(String[] args) {
    FunctionalInterfacesApp app = new FunctionalInterfacesApp();
    double result = app.operar(2, 3);
    System.out.println(result);
  }

}
```

### Method Reference

```java
package com.software_engineering.java8.method_reference;

public class Persona {

  private int id;

  private String nombre;

  public Persona(int id, String nombre) {
    super();
    this.id = id;
    this.nombre = nombre;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

}
```

```java
package com.software_engineering.java8.method_reference;

@FunctionalInterface
public interface Operacion {

  void saludar();

}
```

```java
package com.software_engineering.java8.method_reference;

@FunctionalInterface
public interface IPersona {

  Persona crear(int id, String nombre);

}
```

```java
package com.software_engineering.java8.method_reference;

import java.util.Arrays;
import java.util.Comparator;

public class MethodReferenceApp {

  public static void metodoEstaticoRef() {
    System.out.println("Método referido estático");
  }

  public void metodoInstanciaObjetoArbitrarioRef() {
    String[] nombres = {
      "Mito",
      "Code",
      "Jaime"
    };
    Arrays.sort(nombres, new Comparator < String > () {
      @Override
      public int compare(String o1, String o2) {
        return o1.compareToIgnoreCase(o2);
      }
    });
    System.out.println(Arrays.toString(nombres));

    String[] nombres2 = {
      "Mito",
      "Code",
      "Jaime"
    };
    Arrays.sort(nombres2, (s1, s2) -> s1.compareToIgnoreCase(s2));
    System.out.println(Arrays.toString(nombres2));

    String[] nombres3 = {
      "Mito",
      "Code",
      "Jaime"
    };
    Arrays.sort(nombres3, String::compareToIgnoreCase);
    System.out.println(Arrays.toString(nombres3));
  }

  public void metodoInstanciaParticularRef() {
    System.out.println("Método referido instancia particular de una clase");
  }

  public void constructorRef() {
    IPersona iper = new IPersona() {
      @Override
      public Persona crear(int id, String nombre) {
        return new Persona(id, nombre);
      }
    };
    Persona per = iper.crear(1, "MitoCode1");
    System.out.println(per.getId() + " - " + per.getNombre());

    iper = (x, y) -> new Persona(x, y);
    per = iper.crear(2, "MitoCode2");
    System.out.println(per.getId() + " - " + per.getNombre());

    iper = Persona::new;
    per = iper.crear(3, "MitoCode3");
    System.out.println(per.getId() + " - " + per.getNombre());
  }

  public static void main(String[] args) {
    Operacion op1 = MethodReferenceApp::metodoEstaticoRef;
    op1.saludar();

    MethodReferenceApp app = new MethodReferenceApp();
    app.metodoInstanciaObjetoArbitrarioRef();

    Operacion op2 = app::metodoInstanciaParticularRef;
    op2.saludar();

    app.constructorRef();
  }

}
```

```
Método referido estático
[Code, Jaime, Mito]
[Code, Jaime, Mito]
[Code, Jaime, Mito]
Método referido instancia particular de una clase
1 - MitoCode1
2 - MitoCode2
3 - MitoCode3
```
