# Patrones estructurales

Los patrones estructurales explican cómo ensamblar objetos y clases en estructuras más grandes, a la vez que se mantiene la flexibilidad y eficiencia de estas estructuras.

## Adapter

### Propósito <a href="#intent" id="intent"></a>

**Adapter** es un patrón de diseño estructural que permite la colaboración entre objetos con interfaces incompatibles.

<figure><img src="../../.gitbook/assets/adapter-es.png" alt=""><figcaption></figcaption></figure>

### Problema <a href="#problem" id="problem"></a>

Imagina que estás creando una aplicación de monitoreo del mercado de valores. La aplicación descarga la información de bolsa desde varias fuentes en formato XML para presentarla al usuario con bonitos gráficos y diagramas.

En cierto momento, decides mejorar la aplicación integrando una inteligente biblioteca de análisis de una tercera persona. Pero hay una trampa: la biblioteca de análisis solo funciona con datos en formato JSON.

<figure><img src="../../.gitbook/assets/problem-es (1).png" alt=""><figcaption><p>No puedes utilizar la biblioteca de análisis “tal cual” porque ésta espera los datos en un formato que es incompatible con tu aplicación.</p></figcaption></figure>

Podrías cambiar la biblioteca para que funcione con XML. Sin embargo, esto podría descomponer parte del código existente que depende de la biblioteca. Y, lo que es peor, podrías no tener siquiera acceso al código fuente de la biblioteca, lo que hace imposible esta solución.

### Solución <a href="#solution" id="solution"></a>

Puedes crear un _adaptador_. Se trata de un objeto especial que convierte la interfaz de un objeto, de forma que otro objeto pueda comprenderla.

Un adaptador envuelve uno de los objetos para esconder la complejidad de la conversión que tiene lugar tras bambalinas. El objeto envuelto ni siquiera es consciente de la existencia del adaptador. Por ejemplo, puedes envolver un objeto que opera con metros y kilómetros con un adaptador que convierte todos los datos al sistema anglosajón, es decir, pies y millas.

Los adaptadores no solo convierten datos a varios formatos, sino que también ayudan a objetos con distintas interfaces a colaborar. Funciona así:

1. El adaptador obtiene una interfaz compatible con uno de los objetos existentes.
2. Utilizando esta interfaz, el objeto existente puede invocar con seguridad los métodos del adaptador.
3. Al recibir una llamada, el adaptador pasa la solicitud al segundo objeto, pero en un formato y orden que ese segundo objeto espera.

En ocasiones se puede incluso crear un adaptador de dos direcciones que pueda convertir las llamadas en ambos sentidos.

<figure><img src="../../.gitbook/assets/solution-es (2).png" alt=""><figcaption></figcaption></figure>

Regresemos a nuestra aplicación del mercado de valores. Para resolver el dilema de los formatos incompatibles, puedes crear adaptadores de XML a JSON para cada clase de la biblioteca de análisis con la que trabaje tu código directamente. Después ajustas tu código para que se comunique con la biblioteca únicamente a través de estos adaptadores. Cuando un adaptador recibe una llamada, traduce los datos XML entrantes a una estructura JSON y pasa la llamada a los métodos adecuados de un objeto de análisis envuelto.

### Pros y contras <a href="#pros-cons" id="pros-cons"></a>

:heavy\_check\_mark:  _Principio de responsabilidad única_. Puedes separar la interfaz o el código de conversión de datos de la lógica de negocio primaria del programa.

:heavy\_check\_mark:  _Principio de abierto/cerrado_. Puedes introducir nuevos tipos de adaptadores al programa sin descomponer el código cliente existente, siempre y cuando trabajen con los adaptadores a través de la interfaz con el cliente.

:heavy\_multiplication\_x:  La complejidad general del código aumenta, ya que debes introducir un grupo de nuevas interfaces y clases. En ocasiones resulta más sencillo cambiar la clase de servicio de modo que coincida con el resto de tu código.

## Adapter in Java

### Encajar piezas cuadradas en agujeros redondos <a href="#example-0-title" id="example-0-title"></a>

Este sencillo ejemplo muestra el modo en que un Adapter puede hacer que objetos incompatibles trabajen juntos.

```
adapter
├── round 
│   ├── RoundHole.java (Agujeros redondos)
│   └── RoundPeg.java (Piezas redondas)
├── square
│   └── SquarePeg.java (Piezas cuadradas)
├── adapters
│   └── SquarePegAdapter.java (Adaptador de piezas cuadradas para agujeros redondos) 
└── Demo.java (Código cliente)
```

:link: [Adapter in Java](https://github.com/dromero-7854/software-engineering/tree/main/java-design-patterns-examples/src/adapter/example)

## Bridge

### Propósito <a href="#intent" id="intent"></a>

**Bridge** es un patrón de diseño estructural que te permite dividir una clase grande, o un grupo de clases estrechamente relacionadas, en dos jerarquías separadas (abstracción e implementación) que pueden desarrollarse independientemente la una de la otra.

<figure><img src="../../.gitbook/assets/bridge.png" alt=""><figcaption></figcaption></figure>

### Problema <a href="#problem" id="problem"></a>

¿_Abstracción?_ ¿_Implementación_? ¿Asusta? Mantengamos la calma y veamos un ejemplo sencillo.

Digamos que tienes una clase geométrica `Forma` con un par de subclases: `Círculo` y `Cuadrado`. Deseas extender esta jerarquía de clase para que incorpore colores, por lo que planeas crear las subclases de forma `Rojo` y `Azul`. Sin embargo, como ya tienes dos subclases, tienes que crear cuatro combinaciones de clase, como `CírculoAzul` y `CuadradoRojo`.

<figure><img src="../../.gitbook/assets/problem-es (2).png" alt=""><figcaption><p>El número de combinaciones de clase crece en progresión geométrica.</p></figcaption></figure>

Añadir nuevos tipos de forma y color a la jerarquía hará que ésta crezca exponencialmente. Por ejemplo, para añadir una forma de triángulo deberás introducir dos subclases, una para cada color. Y, después, para añadir un nuevo color habrá que crear tres subclases, una para cada tipo de forma. Cuanto más avancemos, peor será.

### Solución <a href="#solution" id="solution"></a>

Este problema se presenta porque intentamos extender las clases de forma en dos dimensiones independientes: por forma y por color. Es un problema muy habitual en la herencia de clases.

El patrón Bridge intenta resolver este problema pasando de la herencia a la composición del objeto. Esto quiere decir que se extrae una de las dimensiones a una jerarquía de clases separada, de modo que las clases originales referencian un objeto de la nueva jerarquía, en lugar de tener todo su estado y sus funcionalidades dentro de una clase.

<figure><img src="../../.gitbook/assets/solution-es.png" alt=""><figcaption><p>Puedes evitar la explosión de una jerarquía de clase transformándola en varias jerarquías relacionadas.</p></figcaption></figure>

Con esta solución, podemos extraer el código relacionado con el color y colocarlo dentro de su propia clase, con dos subclases: `Rojo` y `Azul`. La clase `Forma` obtiene entonces un campo de referencia que apunta a uno de los objetos de color. Ahora la forma puede delegar cualquier trabajo relacionado con el color al objeto de color vinculado. Esa referencia actuará como un puente entre las clases `Forma` y `Color`. En adelante, añadir nuevos colores no exigirá cambiar la jerarquía de forma y viceversa.

**Abstracción e implementación**

El libro de la GoF  introduce los términos _Abstracción_ e _Implementación_ como parte de la definición del patrón Bridge. En mi opinión, los términos suenan demasiado académicos y provocan que el patrón parezca más complicado de lo que es en realidad. Una vez leído el sencillo ejemplo con las formas y los colores, vamos a descifrar el significado que esconden las temibles palabras del libro de esta banda de cuatro.

La _Abstracción_ (también llamada _interfaz_) es una capa de control de alto nivel para una entidad. Esta capa no tiene que hacer ningún trabajo real por su cuenta, sino que debe delegar el trabajo a la capa de _implementación_ (también llamada _plataforma_).

Ten en cuenta que no estamos hablando de las _interfaces_ o las _clases abstractas_ de tu lenguaje de programación. Son cosas diferentes.

Cuando hablamos de aplicación reales, la abstracción puede representarse por una interfaz gráfica de usuario (GUI), y la implementación puede ser el código del sistema operativo subyacente (API) a la que la capa GUI llama en respuesta a las interacciones del usuario.

En términos generales, puedes extender esa aplicación en dos direcciones independientes:

* Tener varias GUI diferentes (por ejemplo, personalizadas para clientes regulares o administradores).
* Soportar varias API diferentes (por ejemplo, para poder lanzar la aplicación con Windows, Linux y macOS).

En el peor de los casos, esta aplicación podría asemejarse a un plato gigante de espagueti, en el que cientos de condicionales conectan distintos tipos de GUI con varias API por todo el código.

<figure><img src="../../.gitbook/assets/bridge-3-es.png" alt=""><figcaption><p>Realizar incluso un cambio sencillo en una base de código monolítica es bastante difícil porque debes comprender <em>todo el asunto</em> muy bien. Es mucho más sencillo realizar cambios en módulos más pequeños y bien definidos.</p></figcaption></figure>

Puedes poner orden en este caos metiendo el código relacionado con combinaciones específicas interfaz-plataforma dentro de clases independientes. Sin embargo, pronto descubrirás que hay _muchas_ de estas clases. La jerarquía de clase crecerá exponencialmente porque añadir una nueva GUI o soportar una API diferente exigirá que se creen más y más clases.

Intentemos resolver este problema con el patrón Bridge, que nos sugiere que dividamos las clases en dos jerarquías:

* Abstracción: la capa GUI de la aplicación.
* Implementación: las API de los sistemas operativos.

<figure><img src="../../.gitbook/assets/bridge-2-es.png" alt=""><figcaption><p>Una de las formas de estructurar una aplicación multiplataforma.</p></figcaption></figure>

El objeto de la abstracción controla la apariencia de la aplicación, delegando el trabajo real al objeto de la implementación vinculado. Las distintas implementaciones son intercambiables siempre y cuando sigan una interfaz común, permitiendo a la misma GUI funcionar con Windows y Linux.

En consecuencia, puedes cambiar las clases de la GUI sin tocar las clases relacionadas con la API. Además, añadir soporte para otro sistema operativo sólo requiere crear una subclase en la jerarquía de implementación.

### Pros y contras <a href="#pros-cons" id="pros-cons"></a>

:heavy\_check\_mark:  Puedes crear clases y aplicaciones independientes de plataforma.

:heavy\_check\_mark:  El código cliente funciona con abstracciones de alto nivel. No está expuesto a los detalles de la plataforma.

:heavy\_check\_mark:  _Principio de abierto/cerrado_. Puedes introducir nuevas abstracciones e implementaciones independientes entre sí.

:heavy\_check\_mark:  _Principio de responsabilidad única_. Puedes centrarte en la lógica de alto nivel en la abstracción y en detalles de la plataforma en la implementación.

:heavy\_multiplication\_x:  Puede ser que el código se complique si aplicas el patrón a una clase muy cohesionada.

## Bridge in Java

### Bridge (puente) entre dispositivos y controles remotos <a href="#example-0-title" id="example-0-title"></a>

Este ejemplo muestra la separación entre las clases de los remotos y los dispositivos que controlan.

Los remotos actúan como abstracciones, y los dispositivos son sus implementaciones. Gracias a las interfaces comunes, los mismos remotos pueden funcionar con distintos dispositivos y viceversa.

El patrón Bridge permite cambiar o incluso crear nuevas clases sin tocar el código de la jerarquía opuesta.

```
bridge
├── devices 
│   ├── Device.java (Interfaz común de todos los dispositivos)
│   ├── Radio.java
│   └── TV.java
├── remotes
│   ├── Remote.java (Interfaz común de todos los remotos)
│   ├── BasicRemote.java
│   └── AdvancedRemote.java
└── Demo.java (Código cliente)
```

:link: [Bridge in Java](https://github.com/dromero-7854/software-engineering/tree/main/java-design-patterns-examples/src/bridge/example)

## Composite

### Propósito <a href="#intent" id="intent"></a>

**Composite** es un patrón de diseño estructural que te permite componer objetos en estructuras de árbol y trabajar con esas estructuras como si fueran objetos individuales.

<figure><img src="../../.gitbook/assets/composite.png" alt=""><figcaption></figcaption></figure>

### Problema <a href="#problem" id="problem"></a>

El uso del patrón Composite sólo tiene sentido cuando el modelo central de tu aplicación puede representarse en forma de árbol.

Por ejemplo, imagina que tienes dos tipos de objetos: `Productos` y `Cajas`. Una `Caja` puede contener varios `Productos` así como cierto número de `Cajas` más pequeñas. Estas `Cajas` pequeñas también pueden contener algunos `Productos` o incluso `Cajas` más pequeñas, y así sucesivamente.

Digamos que decides crear un sistema de pedidos que utiliza estas clases. Los pedidos pueden contener productos sencillos sin envolver, así como cajas llenas de productos... y otras cajas. ¿Cómo determinarás el precio total de ese pedido?

<figure><img src="../../.gitbook/assets/problem-es (3).png" alt=""><figcaption><p>Un pedido puede incluir varios productos empaquetados en cajas, que a su vez están empaquetados en cajas más grandes y así sucesivamente. La estructura se asemeja a un árbol boca abajo.</p></figcaption></figure>

Puedes intentar la solución directa: desenvolver todas las cajas, repasar todos los productos y calcular el total. Esto sería viable en el mundo real; pero en un programa no es tan fácil como ejecutar un bucle. Tienes que conocer de antemano las clases de `Productos` y `Cajas` a iterar, el nivel de anidación de las cajas y otros detalles desagradables. Todo esto provoca que la solución directa sea demasiado complicada, o incluso imposible.

### Solución <a href="#solution" id="solution"></a>

El patrón Composite sugiere que trabajes con `Productos` y `Cajas` a través de una interfaz común que declara un método para calcular el precio total.

¿Cómo funcionaría este método? Para un producto, sencillamente devuelve el precio del producto. Para una caja, recorre cada artículo que contiene la caja, pregunta su precio y devuelve un total por la caja. Si uno de esos artículos fuera una caja más pequeña, esa caja también comenzaría a repasar su contenido y así sucesivamente, hasta que se calcule el precio de todos los componentes internos. Una caja podría incluso añadir costos adicionales al precio final, como costos de empaquetado.

<figure><img src="../../.gitbook/assets/composite-comic-1-es.png" alt=""><figcaption><p>El patrón Composite te permite ejecutar un comportamiento de forma recursiva sobre todos los componentes de un árbol de objetos.</p></figcaption></figure>

La gran ventaja de esta solución es que no tienes que preocuparte por las clases concretas de los objetos que componen el árbol. No tienes que saber si un objeto es un producto simple o una sofisticada caja. Puedes tratarlos a todos por igual a través de la interfaz común. Cuando invocas un método, los propios objetos pasan la solicitud a lo largo del árbol.

### Pros y contras <a href="#pros-cons" id="pros-cons"></a>

:heavy\_check\_mark:  Puedes trabajar con estructuras de árbol complejas con mayor comodidad: utiliza el polimorfismo y la recursión en tu favor.

:heavy\_check\_mark:  _Principio de abierto/cerrado_. Puedes introducir nuevos tipos de elemento en la aplicación sin descomponer el código existente, que ahora funciona con el árbol de objetos.

:heavy\_multiplication\_x:  Puede resultar difícil proporcionar una interfaz común para clases cuya funcionalidad difiere demasiado. En algunos casos, tendrás que generalizar en exceso la interfaz componente, provocando que sea más difícil de comprender.

## Composite in Java

### Formas gráficas simples y compuestas <a href="#example-0-title" id="example-0-title"></a>

Este ejemplo muestra cómo crear formas gráficas complejas compuestas por formas simples, y cómo tratarlas a ambas de manera uniforme.

```
composite
├── shapes 
│   ├── Shape.java (Interfaz común de las formas)
│   ├── BaseShape.java (Forma abstracta con funcionalidad básica)
│   ├── Dot.java
│   ├── Circle.java
│   ├── Rectangle.java
│   └── CompoundShape.java (Forma compuesta, que consiste en otros objetos de forma
├── editor 
│   └── ImageEditor.java (Editor de forma)
└── Demo.java (Código cliente)
```

:link: [Composite in Java](https://github.com/dromero-7854/software-engineering/tree/main/java-design-patterns-examples/src/composite/example)

## Decorator

### Propósito <a href="#intent" id="intent"></a>

**Decorator** es un patrón de diseño estructural que te permite añadir funcionalidades a objetos colocando estos objetos dentro de objetos encapsuladores especiales que contienen estas funcionalidades.

<figure><img src="../../.gitbook/assets/decorator.png" alt=""><figcaption></figcaption></figure>

### Problema <a href="#problem" id="problem"></a>

Imagina que estás trabajando en una biblioteca de notificaciones que permite a otros programas notificar a sus usuarios acerca de eventos importantes.

La versión inicial de la biblioteca se basaba en la clase `Notificador` que solo contaba con unos cuantos campos, un constructor y un único método `send`. El método podía aceptar un argumento de mensaje de un cliente y enviar el mensaje a una lista de correos electrónicos que se pasaban a la clase notificadora a través de su constructor. Una aplicación de un tercero que actuaba como cliente debía crear y configurar el objeto notificador una vez y después utilizarlo cada vez que sucediera algo importante.

<figure><img src="../../.gitbook/assets/problem1-es.png" alt=""><figcaption><p>Un programa puede utilizar la clase notificadora para enviar notificaciones sobre eventos importantes a un grupo predefinido de correos electrónicos.</p></figcaption></figure>

En cierto momento te das cuenta de que los usuarios de la biblioteca esperan algo más que unas simples notificaciones por correo. A muchos de ellos les gustaría recibir mensajes SMS sobre asuntos importantes. Otros querrían recibir las notificaciones por Facebook y, por supuesto, a los usuarios corporativos les encantaría recibir notificaciones por Slack.

<figure><img src="../../.gitbook/assets/problem2.png" alt=""><figcaption><p>Cada tipo de notificación se implementa como una subclase de la clase notificadora.</p></figcaption></figure>

No puede ser muy complicado ¿verdad? Extendiste la clase `Notificador` y metiste los métodos adicionales de notificación dentro de nuevas subclases. Ahora el cliente debería instanciar la clase notificadora deseada y utilizarla para el resto de notificaciones.

Pero entonces alguien te hace una pregunta razonable: “¿Por qué no se pueden utilizar varios tipos de notificación al mismo tiempo? Si tu casa está en llamas, probablemente quieras que te informen a través de todos los canales”.

Intentaste solucionar ese problema creando subclases especiales que combinaban varios métodos de notificación dentro de una clase. Sin embargo, enseguida resultó evidente que esta solución inflaría el código en gran medida, no sólo el de la biblioteca, sino también el código cliente.

<figure><img src="../../.gitbook/assets/problem3.png" alt=""><figcaption><p>Explosión combinatoria de subclases.</p></figcaption></figure>

Debes encontrar alguna otra forma de estructurar las clases de las notificaciones para no alcanzar cifras que rompan accidentalmente un récord Guinness.

### Solución <a href="#solution" id="solution"></a>

Cuando tenemos que alterar la funcionalidad de un objeto, lo primero que se viene a la mente es extender una clase. No obstante, la herencia tiene varias limitaciones importantes de las que debes ser consciente.

* La herencia es estática. No se puede alterar la funcionalidad de un objeto existente durante el tiempo de ejecución. Sólo se puede sustituir el objeto completo por otro creado a partir de una subclase diferente.
* Las subclases sólo pueden tener una clase padre. En la mayoría de lenguajes, la herencia no permite a una clase heredar comportamientos de varias clases al mismo tiempo.

Una de las formas de superar estas limitaciones es empleando la _Agregación_ o la _Composición_ en lugar de la _Herencia_. Ambas alternativas funcionan prácticamente del mismo modo: un objeto _tiene una_ referencia a otro y le delega parte del trabajo, mientras que con la herencia, el propio objeto _puede_ realizar ese trabajo, heredando el comportamiento de su superclase.

Con esta nueva solución puedes sustituir fácilmente el objeto “ayudante” vinculado por otro, cambiando el comportamiento del contenedor durante el tiempo de ejecución. Un objeto puede utilizar el comportamiento de varias clases con referencias a varios objetos, delegándoles todo tipo de tareas. La agregación/composición es el principio clave que se esconde tras muchos patrones de diseño, incluyendo el Decorator. A propósito, regresemos a la discusión sobre el patrón.

<figure><img src="../../.gitbook/assets/solution1-es.png" alt=""><figcaption><p>Herencia vs. Agregación</p></figcaption></figure>

“Wrapper” (envoltorio, en inglés) es el sobrenombre alternativo del patrón Decorator, que expresa claramente su idea principal. Un _wrapper_ es un objeto que puede vincularse con un objeto _objetivo_. El wrapper contiene el mismo grupo de métodos que el objetivo y le delega todas las solicitudes que recibe. No obstante, el wrapper puede alterar el resultado haciendo algo antes o después de pasar la solicitud al objetivo.

¿Cuándo se convierte un simple wrapper en el verdadero decorador? Como he mencionado, el wrapper implementa la misma interfaz que el objeto envuelto. Éste es el motivo por el que, desde la perspectiva del cliente, estos objetos son idénticos. Haz que el campo de referencia del wrapper acepte cualquier objeto que siga esa interfaz. Esto te permitirá _envolver_ un objeto en varios wrappers, añadiéndole el comportamiento combinado de todos ellos.

En nuestro ejemplo de las notificaciones, dejemos la sencilla funcionalidad de las notificaciones por correo electrónico dentro de la clase base `Notificador`, pero convirtamos el resto de los métodos de notificación en decoradores.

<figure><img src="../../.gitbook/assets/solution2.png" alt=""><figcaption><p>Varios métodos de notificación se convierten en decoradores.</p></figcaption></figure>

El código cliente debe envolver un objeto notificador básico dentro de un grupo de decoradores que satisfagan las preferencias del cliente. Los objetos resultantes se estructurarán como una pila.

<figure><img src="../../.gitbook/assets/solution3-es.png" alt=""><figcaption><p>Las aplicaciones pueden configurar pilas complejas de decoradores de notificación.</p></figcaption></figure>

El último decorador de la pila será el objeto con el que el cliente trabaja. Debido a que todos los decoradores implementan la misma interfaz que la notificadora base, al resto del código cliente no le importa si está trabajando con el objeto notificador “puro” o con el decorado.

Podemos aplicar la misma solución a otras funcionalidades, como el formateo de mensajes o la composición de una lista de destinatarios. El cliente puede decorar el objeto con los decoradores personalizados que desee, siempre y cuando sigan la misma interfaz que los demás.

### Pros y contras <a href="#pros-cons" id="pros-cons"></a>

:heavy\_check\_mark:  Puedes extender el comportamiento de un objeto sin crear una nueva subclase.

:heavy\_check\_mark:  Puedes añadir o eliminar responsabilidades de un objeto durante el tiempo de ejecución.

:heavy\_check\_mark:  Puedes combinar varios comportamientos envolviendo un objeto con varios decoradores.

:heavy\_check\_mark:  Principio de responsabilidad única. Puedes dividir una clase monolítica que implementa muchas variantes posibles de comportamiento, en varias clases más pequeñas.

:heavy\_multiplication\_x:  Resulta difícil eliminar un wrapper específico de la pila de wrappers.

:heavy\_multiplication\_x:  Es difícil implementar un decorador de tal forma que su comportamiento no dependa del orden en la pila de decoradores.

:heavy\_multiplication\_x:  El código de configuración inicial de las capas pueden tener un aspecto desagradable.

## Decorator in Java

### Decoradores de codificación y compresión <a href="#example-0-title" id="example-0-title"></a>

Este ejemplo muestra cómo puedes ajustar el comportamiento de un objeto sin cambiar su código.

Inicialmente, la clase de la lógica de negocio sólo podía leer y escribir datos en texto sin formato. Después creamos varias pequeñas clases envoltorio que añaden un nuevo comportamiento tras ejecutar operaciones estándar en un objeto envuelto.

El primer _wrapper_ codifica y decodifica información, y el segundo comprime y extrae datos.

Puedes incluso combinar estos _wrappers_ envolviendo un decorador con otro.
