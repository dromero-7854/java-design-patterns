# Patrones creacionales

Los patrones creacionales proporcionan varios mecanismos de creación de objetos que incrementan la flexibilidad y la reutilización del código existente.

## Factory Method

### Propósito <a href="#intent" id="intent"></a>

<mark style="background-color:yellow;">**Factory Method**</mark> <mark style="background-color:yellow;"></mark><mark style="background-color:yellow;">es un patrón de diseño creacional que proporciona una interfaz para crear objetos en una superclase, mientras permite a las subclases alterar el tipo de objetos que se crearán.</mark>

<figure><img src="../../.gitbook/assets/factory-method-es.png" alt=""><figcaption></figcaption></figure>

### Problema <a href="#problem" id="problem"></a>

Imagina que estás creando una aplicación de gestión logística. La primera versión de tu aplicación sólo es capaz de manejar el transporte en camión, por lo que la mayor parte de tu código se encuentra dentro de la clase `Camión`.

Al cabo de un tiempo, tu aplicación se vuelve bastante popular. Cada día recibes decenas de peticiones de empresas de transporte marítimo para que incorpores la logística por mar a la aplicación.

<figure><img src="../../.gitbook/assets/problem1-es (3).png" alt=""><figcaption><p>Añadir una nueva clase al programa no es tan sencillo si el resto del código ya está acoplado a clases existentes.</p></figcaption></figure>

Estupendo, ¿verdad? Pero, ¿qué pasa con el código? En este momento, la mayor parte de tu código está acoplado a la clase `Camión`. Para añadir barcos a la aplicación habría que hacer cambios en toda la base del código. Además, si más tarde decides añadir otro tipo de transporte a la aplicación, probablemente tendrás que volver a hacer todos estos cambios.

Al final acabarás con un código bastante sucio, plagado de condicionales que cambian el comportamiento de la aplicación dependiendo de la clase de los objetos de transporte.

### Solución <a href="#solution" id="solution"></a>

El patrón Factory Method sugiere que, en lugar de llamar al operador `new` para construir objetos directamente, se invoque a un método _fábrica_ especial. No te preocupes: los objetos se siguen creando a través del operador `new`, pero se invocan desde el método fábrica. Los objetos devueltos por el método fábrica a menudo se denominan _productos_.

<figure><img src="../../.gitbook/assets/solution1 (1).png" alt=""><figcaption><p>Las subclases pueden alterar la clase de los objetos devueltos por el método fábrica.</p></figcaption></figure>

A simple vista, puede parecer que este cambio no tiene sentido, ya que tan solo hemos cambiado el lugar desde donde invocamos al constructor. Sin embargo, piensa en esto: ahora puedes sobrescribir el método fábrica en una subclase y cambiar la clase de los productos creados por el método.

No obstante, hay una pequeña limitación: las subclases sólo pueden devolver productos de distintos tipos si dichos productos tienen una clase base o interfaz común. Además, el método fábrica en la clase base debe tener su tipo de retorno declarado como dicha interfaz.

<figure><img src="../../.gitbook/assets/solution2-es.png" alt=""><figcaption><p>Todos los productos deben seguir la misma interfaz.</p></figcaption></figure>

Por ejemplo, tanto la clase `Camión` como la clase `Barco` deben implementar la interfaz `Transporte`, que declara un método llamado `entrega`. Cada clase implementa este método de forma diferente: los camiones entregan su carga por tierra, mientras que los barcos lo hacen por mar. El método fábrica dentro de la clase `LogísticaTerrestre` devuelve objetos de tipo camión, mientras que el método fábrica de la clase `LogísticaMarítima` devuelve barcos.

<figure><img src="../../.gitbook/assets/solution3-es.png" alt=""><figcaption><p>Siempre y cuando todas las clases de producto implementen una interfaz común, podrás pasar sus objetos al código cliente sin descomponerlo.</p></figcaption></figure>

El código que utiliza el método fábrica (a menudo denominado código _cliente_) no encuentra diferencias entre los productos devueltos por varias subclases, y trata a todos los productos como la clase abstracta `Transporte`. El cliente sabe que todos los objetos de transporte deben tener el método `entrega`, pero no necesita saber cómo funciona exactamente.

### Pros y contras <a href="#pros-cons" id="pros-cons"></a>

:heavy\_check\_mark:  Evitas un acoplamiento fuerte entre el creador y los productos concretos.

:heavy\_check\_mark:  _Principio de responsabilidad única_. Puedes mover el código de creación de producto a un lugar del programa, haciendo que el código sea más fácil de mantener.

:heavy\_check\_mark:  _Principio de abierto/cerrado_. Puedes incorporar nuevos tipos de productos en el programa sin descomponer el código cliente existente.

:heavy\_multiplication\_x:  Puede ser que el código se complique, ya que debes incorporar una multitud de nuevas subclases para implementar el patrón. La situación ideal sería introducir el patrón en una jerarquía existente de clases creadoras.

## Factory Method in Java

En este ejemplo, los botones y las casillas actuarán como productos. Tienen tres variantes: macOS, Windows y Linux.

La fábrica abstracta define una interfaz para crear botones y casillas. Hay tres fábricas concretas, que devuelven ambos productos en una única variante.

El código cliente funciona con fábricas y productos utilizando interfaces abstractas. Esto permite al código cliente funcionar con cualquier variante de producto creada por el objeto de fábrica.

```
factory_method
├── buttons
│   ├── Button.java
│   ├── MacOSButton.java
│   ├── WindowsButton.java
│   └── LinuxButton.java
├── checkboxes
│   ├── Checkbox.java
│   ├── MacOSCheckbox.java
│   ├── WindowsCheckbox.java
│   └── LinuxCheckbox.java
├── factories
│   ├── GUIFactory.java (Fábrica abstracta)
│   ├── MacOSFactory.java (Fábrica concreta macOS)
│   ├── WindowsFactory.java (Fábrica concreta Windows) 
│   └── LinuxFactory.java (Fábrica concreta Ubuntu) 
├── app
│   └── Application.java (Código cliente)
└── Demo.java (Configuración de la aplicación)
```

:link: [Factory Method in Java](https://github.com/dromero-7854/software-engineering/tree/main/java-design-patterns-examples/src/factory\_method/example)

## Abstract Factory

### Propósito <a href="#intent" id="intent"></a>

**Abstract Factory** es un patrón de diseño creacional que nos permite producir familias de objetos relacionados sin especificar sus clases concretas.

<figure><img src="../../.gitbook/assets/abstract-factory-es.png" alt=""><figcaption></figcaption></figure>

### Problema <a href="#problem" id="problem"></a>

Imagina que estás creando un simulador de tienda de muebles. Tu código está compuesto por clases que representan lo siguiente:

1. Una familia de productos relacionados, digamos: `Silla` + `Sofá` + `Mesilla`.
2. Algunas variantes de esta familia. Por ejemplo, los productos `Silla` + `Sofá` + `Mesilla` están disponibles en estas variantes: `Moderna`, `Victoriana`, `ArtDecó`.

<figure><img src="../../.gitbook/assets/problem-es.png" alt=""><figcaption><p>Familias de productos y sus variantes.</p></figcaption></figure>

Necesitamos una forma de crear objetos individuales de mobiliario para que combinen con otros objetos de la misma familia. Los clientes se enfadan bastante cuando reciben muebles que no combinan.

<figure><img src="../../.gitbook/assets/abstract-factory-comic-1-es.png" alt=""><figcaption><p>Un sofá de estilo moderno no combina con unas sillas de estilo victoriano.</p></figcaption></figure>

Además, no queremos cambiar el código existente al añadir al programa nuevos productos o familias de productos. Los comerciantes de muebles actualizan sus catálogos muy a menudo, y debemos evitar tener que cambiar el código principal cada vez que esto ocurra.

### Solución <a href="#solution" id="solution"></a>

Lo primero que sugiere el patrón Abstract Factory es que declaremos de forma explícita interfaces para cada producto diferente de la familia de productos (por ejemplo, silla, sofá o mesilla). Después podemos hacer que todas las variantes de los productos sigan esas interfaces. Por ejemplo, todas las variantes de silla pueden implementar la interfaz `Silla`, así como todas las variantes de mesilla pueden implementar la interfaz `Mesilla`, y así sucesivamente.

<figure><img src="../../.gitbook/assets/solution1.png" alt=""><figcaption><p>Todas las variantes del mismo objeto deben moverse a una única jerarquía de clase.</p></figcaption></figure>

El siguiente paso consiste en declarar la _Fábrica abstracta_: una interfaz con una lista de métodos de creación para todos los productos que son parte de la familia de productos (por ejemplo, `crearSilla`, `crearSofá` y `crearMesilla`). Estos métodos deben devolver productos **abstractos** representados por las interfaces que extrajimos previamente: `Silla`, `Sofá`, `Mesilla`, etc.

<figure><img src="../../.gitbook/assets/solution2.png" alt=""><figcaption><p>Cada fábrica concreta se corresponde con una variante específica del producto.</p></figcaption></figure>

Ahora bien, ¿qué hay de las variantes de los productos? Para cada variante de una familia de productos, creamos una clase de fábrica independiente basada en la interfaz `FábricaAbstracta`. Una fábrica es una clase que devuelve productos de un tipo particular. Por ejemplo, la `FábricadeMueblesModernos` sólo puede crear objetos de `SillaModerna`, `SofáModerno` y `MesillaModerna`.

El código cliente tiene que funcionar con fábricas y productos a través de sus respectivas interfaces abstractas. Esto nos permite cambiar el tipo de fábrica que pasamos al código cliente, así como la variante del producto que recibe el código cliente, sin descomponer el propio código cliente.

<figure><img src="../../.gitbook/assets/abstract-factory-comic-2-es.png" alt=""><figcaption><p>Al cliente no le debe importar la clase concreta de la fábrica con la que funciona.</p></figcaption></figure>

Digamos que el cliente quiere una fábrica para producir una silla. El cliente no tiene que conocer la clase de la fábrica y tampoco importa el tipo de silla que obtiene. Ya sea un modelo moderno o una silla de estilo victoriano, el cliente debe tratar a todas las sillas del mismo modo, utilizando la interfaz abstracta `Silla`. Con este sistema, lo único que sabe el cliente sobre la silla es que implementa de algún modo el método `sentarse`. Además, sea cual sea la variante de silla devuelta, siempre combinará con el tipo de sofá o mesilla producida por el mismo objeto de fábrica.

Queda otro punto por aclarar: si el cliente sólo está expuesto a las interfaces abstractas, ¿cómo se crean los objetos de fábrica? Normalmente, la aplicación crea un objeto de fábrica concreto en la etapa de inicialización. Justo antes, la aplicación debe seleccionar el tipo de fábrica, dependiendo de la configuración o de los ajustes del entorno.

### Pros y contras <a href="#pros-cons" id="pros-cons"></a>

:heavy\_check\_mark:  Puedes tener la certeza de que los productos que obtienes de una fábrica son compatibles entre sí.

:heavy\_check\_mark:  Evitas un acoplamiento fuerte entre productos concretos y el código cliente.

:heavy\_check\_mark:  _Principio de responsabilidad única_. Puedes mover el código de creación de productos a un solo lugar, haciendo que el código sea más fácil de mantener.

:heavy\_check\_mark:  _Principio de abierto/cerrado_. Puedes introducir nuevas variantes de productos sin descomponer el código cliente existente.

:heavy\_multiplication\_x:  Puede ser que el código se complique más de lo que debería, ya que se introducen muchas nuevas interfaces y clases junto al patrón.
