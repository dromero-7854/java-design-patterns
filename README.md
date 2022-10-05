# Patrones de comportamiento

Los patrones de comportamiento tratan con algoritmos y la asignación de responsabilidades entre objetos.

## Chain of Responsibility

### Propósito <a href="#intent" id="intent"></a>

**Chain of Responsibility** es un patrón de diseño de comportamiento que te permite pasar solicitudes a lo largo de una cadena de manejadores. Al recibir una solicitud, cada manejador decide si la procesa o si la pasa al siguiente manejador de la cadena.

<figure><img src=".gitbook/assets/chain-of-responsibility (1).png" alt=""><figcaption></figcaption></figure>

### Problema

Imagina que estás trabajando en un sistema de pedidos online. Quieres restringir el acceso al sistema de forma que únicamente los usuarios autenticados puedan generar pedidos. Además, los usuarios que tengan permisos administrativos deben tener pleno acceso a todos los pedidos.

Tras planificar un poco, te das cuenta de que estas comprobaciones deben realizarse secuencialmente. La aplicación puede intentar autenticar a un usuario en el sistema cuando reciba una solicitud que contenga las credenciales del usuario. Sin embargo, si esas credenciales no son correctas y la autenticación falla, no hay razón para proceder con otras comprobaciones.

<figure><img src=".gitbook/assets/problem1-es (1).png" alt=""><figcaption><p>La solicitud debe pasar una serie de comprobaciones antes de que el propio sistema de pedidos pueda gestionarla.</p></figcaption></figure>

Durante los meses siguientes, implementas varias de esas comprobaciones secuenciales.

* Uno de tus colegas sugiere que no es seguro pasar datos sin procesar directamente al sistema de pedidos. De modo que añades un paso adicional de validación para sanear los datos de una solicitud.
* Más tarde, alguien se da cuenta de que el sistema es vulnerable al desciframiento de contraseñas por la fuerza. Para evitarlo, añades rápidamente una comprobación que filtra las solicitudes fallidas repetidas que vengan de la misma dirección IP.
* Otra persona sugiere que podrías acelerar el sistema devolviendo los resultados en caché en solicitudes repetidas que contengan los mismos datos, de modo que añades otra comprobación que permite a la solicitud pasar por el sistema únicamente cuando no hay una respuesta adecuada en caché.

<figure><img src=".gitbook/assets/problem2-es.png" alt=""><figcaption><p>Cuanto más crece el código, más se complica.</p></figcaption></figure>

El código de las comprobaciones, que ya se veía desordenado, se vuelve más y más abotargado cada vez que añades una nueva función. En ocasiones, un cambio en una comprobación afecta a las demás. Y lo peor de todo es que, cuando intentas reutilizar las comprobaciones para proteger otros componentes del sistema, tienes que duplicar parte del código, ya que esos componentes necesitan parte de las comprobaciones, pero no todas ellas.

El sistema se vuelve muy difícil de comprender y costoso de mantener. Luchas con el código durante un tiempo hasta que un día decides refactorizarlo todo.

### Solución

Al igual que muchos otros patrones de diseño de comportamiento, el **Chain of Responsibility** se basa en transformar comportamientos particulares en objetos autónomos llamados _manejadores_. En nuestro caso, cada comprobación debe ponerse dentro de su propia clase con un único método que realice la comprobación. La solicitud, junto con su información, se pasa a este método como argumento.

<mark style="background-color:yellow;">El patrón sugiere que vincules esos manejadores en una cadena. Cada manejador vinculado tiene un campo para almacenar una referencia al siguiente manejador de la cadena. Además de procesar una solicitud, los manejadores la pasan a lo largo de la cadena. La solicitud viaja por la cadena hasta que todos los manejadores han tenido la oportunidad de procesarla.</mark>

<mark style="background-color:yellow;">Y ésta es la mejor parte: un manejador puede decidir no pasar la solicitud más allá por la cadena y detener con ello el procesamiento.</mark>

En nuestro ejemplo de los sistemas de pedidos, un manejador realiza el procesamiento y después decide si pasa la solicitud al siguiente eslabón de la cadena. Asumiendo que la solicitud contiene la información correcta, todos los manejadores pueden ejecutar su comportamiento principal, ya sean comprobaciones de autenticación o almacenamiento en la memoria caché.

<figure><img src=".gitbook/assets/solution1-es (1).png" alt=""><figcaption><p>Los manejadores se alinean uno tras otro, formando una cadena.</p></figcaption></figure>

No obstante, hay una solución ligeramente diferente (y un poco más estandarizada) en la que, al recibir una solicitud, un manejador decide si puede procesarla. Si puede, no pasa la solicitud más allá. De modo que un único manejador procesa la solicitud o no lo hace ninguno en absoluto. Esta solución es muy habitual cuando tratamos con eventos en pilas de elementos dentro de una interfaz gráfica de usuario (GUI).

Por ejemplo, cuando un usuario hace clic en un botón, el evento se propaga por la cadena de elementos GUI que comienza en el botón, recorre sus contenedores (como formularios o paneles) y acaba en la ventana principal de la aplicación. El evento es procesado por el primer elemento de la cadena que es capaz de gestionarlo. Este ejemplo también es destacable porque muestra que siempre se puede extraer una cadena de un árbol de objetos.

<figure><img src=".gitbook/assets/solution2-es (3).png" alt=""><figcaption><p>Una cadena puede formarse a partir de una rama de un árbol de objetos.</p></figcaption></figure>

Es fundamental que todas las clases manejadoras implementen la misma interfaz. Cada manejadora concreta solo debe preocuparse por la siguiente que cuente con el método `ejecutar`. De esta forma puedes componer cadenas durante el tiempo de ejecución, utilizando varios manejadores sin acoplar tu código a sus clases concretas.

## Chain of Responsibility in Java

### Acceso filtrado <a href="#example-0-title" id="example-0-title"></a>

Este ejemplo muestra cómo una solicitud que contiene información de usuario pasa una cadena secuencial de manejadores que realizan varias acciones, como la autenticación, autorización y validación.

Este ejemplo es un poco diferente de la versión estándar del patrón establecida por varios autores. La mayoría de ejemplos del patrón se basan en la noción de buscar el manejador adecuado, lanzarlo y salir de la cadena a continuación. Pero aquí ejecutamos todos los manejadores hasta que hay uno que **no puede gestionar** una solicitud. Ten en cuenta que éste sigue siendo el patrón Chain of Responsibility, aunque el flujo es un poco distinto.

#### :open\_file\_folder: **middleware**

:page\_facing\_up: **middleware/Middleware.java (Interfaz de validación básica)**

:page\_facing\_up: **middleware/ThrottlingMiddleware.java (Comprueba el límite de cantidad de solicitudes)**

:page\_facing\_up: **middleware/UserExistsMiddleware.java (Comprueba las credenciales del usuario)**

:page\_facing\_up: **middleware/UserExistsMiddleware.java (Comprueba el papel del usuario)**

#### :open\_file\_folder: **server**

:page\_facing\_up: **server/Server.java (Objetivo de la autorización)**

:page\_facing\_up: **Demo.java (Código cliente)**

:link: [Chain of Responsibility in Java](https://github.com/dromero-7854/knowledge/tree/main/java-design-patterns-examples/src/chain\_of\_responsibility/example)

## Command

### Propósito

<mark style="background-color:yellow;">**Command**</mark> <mark style="background-color:yellow;">es un patrón de diseño de comportamiento que convierte una solicitud en un objeto independiente que contiene toda la información sobre la solicitud.</mark> Esta transformación te permite parametrizar los métodos con diferentes solicitudes, retrasar o poner en cola la ejecución de una solicitud y soportar operaciones que no se pueden realizar.

<figure><img src=".gitbook/assets/command-es.png" alt=""><figcaption></figcaption></figure>

### Problema <a href="#problem" id="problem"></a>

Imagina que estás trabajando en una nueva aplicación de edición de texto. Tu tarea actual consiste en crear una barra de herramientas con unos cuantos botones para varias operaciones del editor. Creaste una clase `Botón` muy limpia que puede utilizarse para los botones de la barra de herramientas y también para botones genéricos en diversos diálogos.

<figure><img src=".gitbook/assets/chain-of-responsibility.png" alt=""><figcaption><p>Todos los botones de la aplicación provienen de la misma clase.</p></figcaption></figure>

Aunque todos estos botones se parecen, se supone que hacen cosas diferentes. ¿Dónde pondrías el código para los varios gestores de clics de estos botones? La solución más simple consiste en crear cientos de subclases para cada lugar donde se utilice el botón. Estas subclases contendrán el código que deberá ejecutarse con el clic en un botón.

<figure><img src=".gitbook/assets/problem2 (1) (2).png" alt=""><figcaption><p>Muchas subclases de botón. ¿Qué puede salir mal?</p></figcaption></figure>

Pronto te das cuenta de que esta solución es muy deficiente. En primer lugar, tienes una enorme cantidad de subclases, lo cual no supondría un problema si no corrieras el riesgo de descomponer el código de esas subclases cada vez que modifiques la clase base `Botón`. Dicho de forma sencilla, tu código GUI depende torpemente del volátil código de la lógica de negocio.

<figure><img src=".gitbook/assets/problem3-es.png" alt=""><figcaption><p>Varias clases implementan la misma funcionalidad.</p></figcaption></figure>

Y aquí está la parte más desagradable. Algunas operaciones, como copiar/pegar texto, deben ser invocadas desde varios lugares. Por ejemplo, un usuario podría hacer clic en un pequeño botón “Copiar” de la barra de herramientas, o copiar algo a través del menú contextual, o pulsar `Ctrl+C` en el teclado.

Inicialmente, cuando tu aplicación solo tenía la barra de herramientas, no había problema en colocar la implementación de varias operaciones dentro de las subclases de botón. En otras palabras, tener el código para copiar texto dentro de la subclase `BotónCopiar` estaba bien. Sin embargo, cuando implementas menús contextuales, atajos y otros elementos, debes duplicar el código de la operación en muchas clases, o bien hacer menús dependientes de los botones, lo cual es una opción aún peor.

### Solución

El buen diseño de software a menudo se basa en el principio de separación de responsabilidades, lo que suele tener como resultado la división de la aplicación en capas. El ejemplo más habitual es tener una capa para la interfaz gráfica de usuario (GUI) y otra capa para la lógica de negocio. La capa GUI es responsable de representar una bonita imagen en pantalla, capturar entradas y mostrar resultados de lo que el usuario y la aplicación están haciendo. Sin embargo, cuando se trata de hacer algo importante, como calcular la trayectoria de la luna o componer un informe anual, la capa GUI delega el trabajo a la capa subyacente de la lógica de negocio.

El código puede tener este aspecto: un objeto GUI invoca a un método de un objeto de la lógica de negocio, pasándole algunos argumentos. Este proceso se describe habitualmente como un objeto que envía a otro una _solicitud_.

<figure><img src=".gitbook/assets/solution1-es (2).png" alt=""><figcaption><p>Los objetos GUI pueden acceder directamente a los objetos de la lógica de negocio.</p></figcaption></figure>

<mark style="background-color:yellow;">El patrón Command sugiere que los objetos GUI no envíen estas solicitudes directamente. En lugar de ello, debes extraer todos los detalles de la solicitud, como el objeto que está siendo invocado, el nombre del método y la lista de argumentos, y ponerlos dentro de una clase</mark> _<mark style="background-color:yellow;">comando</mark>_ <mark style="background-color:yellow;">separada con un único método que activa esta solicitud.</mark>

Los objetos de comando sirven como vínculo entre varios objetos GUI y de lógica de negocio. De ahora en adelante, el objeto GUI no tiene que conocer qué objeto de la lógica de negocio recibirá la solicitud y cómo la procesará. El objeto GUI activa el comando, que gestiona todos los detalles.

<figure><img src=".gitbook/assets/solution2-es (2).png" alt=""><figcaption><p>Acceso a la capa de lógica de negocio a través de un comando.</p></figcaption></figure>

El siguiente paso es hacer que tus comandos implementen la misma interfaz. Normalmente tiene un único método de ejecución que no acepta parámetros. Esta interfaz te permite utilizar varios comandos con el mismo emisor de la solicitud, sin acoplarla a clases concretas de comandos. Adicionalmente, ahora puedes cambiar objetos de comando vinculados al emisor, cambiando efectivamente el comportamiento del emisor durante el tiempo de ejecución.

Puede que hayas observado que falta una pieza del rompecabezas, que son los parámetros de la solicitud. Un objeto GUI puede haber proporcionado al objeto de la capa de negocio algunos parámetros. Ya que el método de ejecución del comando no tiene parámetros, ¿cómo pasaremos los detalles de la solicitud al receptor? <mark style="background-color:yellow;">Resulta que el comando debe estar preconfigurado con esta información o ser capaz de conseguirla por su cuenta.</mark>

<figure><img src=".gitbook/assets/solution3-es.png" alt=""><figcaption><p>Los objetos GUI delegan el trabajo a los comandos.</p></figcaption></figure>

Regresemos a nuestro editor de textos. Tras aplicar el patrón Command, ya no necesitamos todas esas subclases de botón para implementar varios comportamientos de clic. Basta con colocar un único campo dentro de la clase base `Botón` que almacene una referencia a un objeto de comando y haga que el botón ejecute ese comando en un clic.

Implementarás un puñado de clases de comando para toda operación posible y las vincularás con botones particulares, dependiendo del comportamiento pretendido de los botones.

Otros elementos GUI, como menús, atajos o diálogos enteros, se pueden implementar del mismo modo. Se vincularán a un comando que se ejecuta cuando un usuario interactúa con el elemento GUI. Como probablemente ya habrás adivinado, los elementos relacionados con las mismas operaciones se vincularán a los mismos comandos, evitando cualquier duplicación de código.

<mark style="background-color:yellow;">Como resultado, los comandos se convierten en una conveniente capa intermedia que reduce el acoplamiento entre las capas de la GUI y la lógica de negocio.</mark> ¡Y esto es tan solo una fracción de las ventajas que ofrece el patrón Command!

## Command in Java

### Comandos de editor de texto y deshacer <a href="#example-0-title" id="example-0-title"></a>

El editor de texto de este ejemplo crea nuevos objetos de comando cada vez que un usuario interactúa con él. Tras ejecutar sus acciones, un comando es empujado a la pila del historial.

Ahora, para realizar la operación deshacer (undo), la aplicación toma el último comando ejecutado del historial y, o bien realiza una acción inversa, o bien restaura el pasado estado del editor guardado por ese comando.

#### :open\_file\_folder: **commands**

:page\_facing\_up: **commands/Command.java (Comando base abstracto)**

:page\_facing\_up: **commands/CopyCommand.java (Copiar el texto seleccionado en el portapapeles)**

:page\_facing\_up: **commands/PasteCommand.java (Pegar texto desde el portapapeles)**

:page\_facing\_up: **commands/CutCommand.java (Cortar texto al portapapeles)**

:page\_facing\_up: **commands/CommandHistory.java (Historial del comando)**

#### :open\_file\_folder: **editor**

:page\_facing\_up: **editor/Editor.java (GUI del editor de texto)**

:page\_facing\_up: **Demo.java (Código cliente)**

:link: [Command in Java](https://github.com/dromero-7854/knowledge/tree/main/java-design-patterns-examples/src/command/example)

## Iterator

### Propósito

**Iterator** es un patrón de diseño de comportamiento que te permite recorrer elementos de una colección sin exponer su representación subyacente (lista, pila, árbol, etc.)

<figure><img src=".gitbook/assets/iterator-es.png" alt=""><figcaption></figcaption></figure>

### Problema

Las colecciones son de los tipos de datos más utilizados en programación. Sin embargo, una colección tan solo es un contenedor para un grupo de objetos.

<figure><img src=".gitbook/assets/problem1.png" alt=""><figcaption><p>Varios tipos de colecciones.</p></figcaption></figure>

La mayoría de las colecciones almacena sus elementos en simples listas, pero algunas de ellas se basan en pilas, árboles, grafos y otras estructuras complejas de datos.

Independientemente de cómo se estructure una colección, <mark style="background-color:yellow;">debe aportar una forma de acceder a sus elementos de modo que otro código pueda utilizar dichos elementos</mark>. Debe haber una forma de recorrer cada elemento de la colección sin acceder a los mismos elementos una y otra vez.

Esto puede parecer un trabajo sencillo si tienes una colección basada en una lista. En este caso sólo tienes que recorrer en bucle todos sus elementos. Pero, ¿cómo recorres secuencialmente elementos de una estructura compleja de datos, como un árbol? Por ejemplo, un día puede bastarte con un recorrido de profundidad de un árbol, pero, al día siguiente, quizá necesites un recorrido en anchura. Y, la semana siguiente, puedes necesitar otra cosa, como un acceso aleatorio a los elementos del árbol.

<figure><img src=".gitbook/assets/problem2.png" alt=""><figcaption><p>La misma colección puede recorrerse de varias formas diferentes.</p></figcaption></figure>

Añadir más y más algoritmos de recorrido a la colección nubla gradualmente su responsabilidad principal, que es el almacenamiento eficiente de la información. Además, puede que algunos algoritmos estén personalizados para una aplicación específica, por lo que incluirlos en una clase genérica de colección puede resultar extraño.

Por otro lado, el código cliente que debe funcionar con varias colecciones puede no saber cómo éstas almacenan sus elementos. No obstante, ya que todas las colecciones proporcionan formas diferentes de acceder a sus elementos, no tienes otra opción más que acoplar tu código a las clases de la colección específica.

### Solución

<mark style="background-color:yellow;">La idea central del patrón Iterator es extraer el comportamiento de recorrido de una colección y colocarlo en un objeto independiente llamado</mark> _<mark style="background-color:yellow;">iterador</mark>_<mark style="background-color:yellow;">.</mark>

<figure><img src=".gitbook/assets/solution1.png" alt=""><figcaption><p>Los iteradores implementan varios algoritmos de recorrido. Varios objetos iteradores pueden recorrer la misma colección al mismo tiempo.</p></figcaption></figure>

<mark style="background-color:yellow;">Además de implementar el propio algoritmo, un objeto iterador encapsula todos los detalles del recorrido, como la posición actual y cuántos elementos quedan hasta el final.</mark> Debido a esto, varios iteradores pueden recorrer la misma colección al mismo tiempo, independientemente los unos de los otros.

Normalmente, los iteradores aportan un método principal para extraer elementos de la colección. El cliente puede continuar ejecutando este método hasta que no devuelva nada, lo que significa que el iterador ha recorrido todos los elementos.

Todos los iteradores deben implementar la misma interfaz. Esto hace que el código cliente sea compatible con cualquier tipo de colección o cualquier algoritmo de recorrido, siempre y cuando exista un iterador adecuado. Si necesitas una forma particular de recorrer una colección, creas una nueva clase iteradora sin tener que cambiar la colección o el cliente.

## Iterator in Java

### Iteración en perfiles de redes sociales <a href="#example-0-title" id="example-0-title"></a>

En este ejemplo, el patrón Iterator se utiliza para recorrer perfiles sociales de una colección remota de una red social, sin exponer los detalles de la comunicación al código cliente.

#### :open\_file\_folder: **iterators**

:page\_facing\_up: **iterators/ProfileIterator.java (Define la interfaz del iterador)**

:page\_facing\_up: **iterators/FacebookIterator.java (Implementa la iteración por perfiles de Facebook)**

:page\_facing\_up: **iterators/LinkedInIterator.java (Implementa la iteración por perfiles de LinkedIn)**

#### :open\_file\_folder: **social\_networks**

:page\_facing\_up: **social\_networks/SocialNetwork.java (Define una interfaz común de red social)**

:page\_facing\_up: **social\_networks/Facebook.java (Facebook)**

:page\_facing\_up: **social\_networks/LinkedIn.java (LinkedIn)**

#### :open\_file\_folder: **profile**

:page\_facing\_up: **profile/Profile.java (Perfiles sociales)**

#### :open\_file\_folder: **spammer**

:page\_facing\_up: **spammer/SocialSpammer.java (Aplicación de envío de mensajes)**

:page\_facing\_up: **Demo.java (Código cliente)**

:link: [Iterator in Java](https://github.com/dromero-7854/knowledge/tree/main/java-design-patterns-examples/src/iterator/example)

## Mediator

### Propósito <a href="#intent" id="intent"></a>

<mark style="background-color:yellow;">**Mediator**</mark> <mark style="background-color:yellow;">es un patrón de diseño de comportamiento que te permite reducir las dependencias caóticas entre objetos. El patrón restringe las comunicaciones directas entre los objetos, forzándolos a colaborar únicamente a través de un objeto mediador.</mark>

<figure><img src=".gitbook/assets/mediator.png" alt=""><figcaption></figcaption></figure>

### Problema <a href="#problem" id="problem"></a>

Digamos que tienes un diálogo para crear y editar perfiles de cliente. Consiste en varios controles de formulario, como campos de texto, casillas, botones, etc.

<figure><img src=".gitbook/assets/problem1-es.png" alt=""><figcaption><p>Las relaciones entre los elementos de la interfaz de usuario pueden volverse caóticas cuando la aplicación crece.</p></figcaption></figure>

Algunos de los elementos del formulario pueden interactuar con otros. Por ejemplo, al seleccionar la casilla “tengo un perro” puede aparecer un campo de texto oculto para introducir el nombre del perro. Otro ejemplo es el botón de envío que tiene que validar los valores de todos los campos antes de guardar la información.

<figure><img src=".gitbook/assets/problem2 (1).png" alt=""><figcaption><p>Los elementos pueden tener muchas relaciones con otros elementos. Por eso, los cambios en algunos elementos pueden afectar a los demás.</p></figcaption></figure>

Al implementar esta lógica directamente dentro del código de los elementos del formulario, haces que las clases de estos elementos sean mucho más difíciles de reutilizar en otros formularios de la aplicación. Por ejemplo, no podrás utilizar la clase de la casilla dentro de otro formulario porque está acoplada al campo de texto del perro. O bien podrás utilizar todas las clases implicadas en representar el formulario de perfil, o no podrás usar ninguna en absoluto.

### Solución <a href="#solution" id="solution"></a>

<mark style="background-color:yellow;">El patrón Mediator sugiere que detengas toda comunicación directa entre los componentes que quieres hacer independientes entre sí. En lugar de ello, estos componentes deberán colaborar indirectamente, invocando un objeto mediador especial que redireccione las llamadas a los componentes adecuados. Como resultado, los componentes dependen únicamente de una sola clase mediadora, en lugar de estar acoplados a decenas de sus colegas.</mark>

En nuestro ejemplo del formulario de edición de perfiles, la propia clase de diálogo puede actuar como mediadora. Lo más probable es que la clase de diálogo conozca ya todos sus subelementos, por lo que ni siquiera será necesario que introduzcas nuevas dependencias en esta clase.

<figure><img src=".gitbook/assets/solution1-es.png" alt=""><figcaption><p>Los elementos UI deben comunicarse indirectamente, a través del objeto mediador.</p></figcaption></figure>

El cambio más significativo lo sufren los propios elementos del formulario. Pensemos en el botón de envío. Antes, cada vez que un usuario hacía clic en el botón, tenía que validar los valores de todos los elementos individuales del formulario. Ahora su único trabajo consiste en notificar al diálogo acerca del clic. Al recibir esta notificación, el propio diálogo realiza las validaciones o pasa la tarea a los elementos individuales. De este modo, en lugar de estar ligado a una docena de elementos del formulario, el botón solo es dependiente de la clase diálogo.

Puedes ir más lejos y reducir en mayor medida la dependencia extrayendo la interfaz común para todos los tipos de diálogo. La interfaz declarará el método de notificación que pueden utilizar todos los elementos del formulario para notificar al diálogo sobre los eventos que le suceden a estos elementos. Por lo tanto, ahora nuestro botón de envío debería poder funcionar con cualquier diálogo que implemente esa interfaz.

De este modo, el patrón Mediator te permite encapsular una compleja red de relaciones entre varios objetos dentro de un único objeto mediador. Cuantas menos dependencias tenga una clase, más fácil es modificar, extender o reutilizar esa clase.

## Mediator in Java

### Aplicación de notas <a href="#example-0-title" id="example-0-title"></a>

Este ejemplo muestra cómo organizar varios elementos GUI para que cooperen con la ayuda de un mediador sin que dependan los unos de los otros.

#### :open\_file\_folder: **components (Clases colegas)**

:page\_facing\_up: **components/Component.java**

:page\_facing\_up: **components/AddButton.java**

:page\_facing\_up: **components/DeleteButton.java**

:page\_facing\_up: **components/Filter.java**

:page\_facing\_up: **components/List.java**

:page\_facing\_up: **components/SaveButton.java**

:page\_facing\_up: **components/TextBox.java**

:page\_facing\_up: **components/Title.java**

#### :open\_file\_folder: **mediator**

:page\_facing\_up: **mediator/Mediator.java (Define la interfaz mediadora común)**

:page\_facing\_up: **mediator/Editor.java (Mediadora concreta)**

:page\_facing\_up: **mediator/Note.java (Una clase de nota)**

:page\_facing\_up: **Demo.java (Código de inicialización)**

:link: [Mediator in Java](https://github.com/dromero-7854/knowledge/tree/main/java-design-patterns-examples/src/chain\_of\_responsibility/example)

## Memento

### Propósito <a href="#intent" id="intent"></a>

<mark style="background-color:yellow;">**Memento**</mark> <mark style="background-color:yellow;"></mark><mark style="background-color:yellow;">es un patrón de diseño de comportamiento que te permite guardar y restaurar el estado previo de un objeto sin revelar los detalles de su implementación.</mark>

<figure><img src=".gitbook/assets/memento-es.png" alt=""><figcaption></figcaption></figure>

### Problema <a href="#problem" id="problem"></a>

Imagina que estás creando una aplicación de edición de texto. Además de editar texto, tu programa puede formatearlo, asi como insertar imágenes en línea, etc.

En cierto momento, decides permitir a los usuarios deshacer cualquier operación realizada en el texto. Esta función se ha vuelto tan habitual en los últimos años que hoy en día todo el mundo espera que todas las aplicaciones la tengan. Para la implementación eliges la solución directa. Antes de realizar cualquier operación, la aplicación registra el estado de todos los objetos y lo guarda en un almacenamiento. Más tarde, cuando un usuario decide revertir una acción, la aplicación extrae la última _instantánea_ del historial y la utiliza para restaurar el estado de todos los objetos.

<figure><img src=".gitbook/assets/problem1-es (2).png" alt=""><figcaption><p>Antes de ejecutar una operación, la aplicación guarda una instantánea del estado de los objetos, que más tarde se puede utilizar para restaurar objetos a su estado previo.</p></figcaption></figure>

Pensemos en estas instantáneas de estado. ¿Cómo producirías una, exactamente? Probablemente tengas que recorrer todos los campos de un objeto y copiar sus valores en el almacenamiento. Sin embargo, esto sólo funcionará si el objeto tiene unas restricciones bastante laxas al acceso a sus contenidos. Lamentablemente, la mayoría de objetos reales no permite a otros asomarse a su interior fácilmente, y esconden todos los datos significativos en campos privados.

Ignora ese problema por ahora y asumamos que nuestros objetos se comportan como hippies: prefieren relaciones abiertas y mantienen su estado público. Aunque esta solución resolvería el problema inmediato y te permitiría producir instantáneas de estados de objetos a voluntad, sigue teniendo algunos inconvenientes serios. En el futuro, puede que decidas refactorizar algunas de las clases editoras, o añadir o eliminar algunos de los campos. Parece fácil, pero esto también exige cambiar las clases responsables de copiar el estado de los objetos afectados.

