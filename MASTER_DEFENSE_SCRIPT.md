# MASTER DEFENSE SCRIPT - ADVENT OF CODE 2025
> **Propósito**: Documento consolidado para la defensa del proyecto ante el tribunal.
> **Estructura**: Cada sección corresponde a un Día y está diseñada para ser explicada en **5 minutos**.

## Tabla de Contenidos
1. [Día 1: La Caja Fuerte (Inmutabilidad y Strategy)]( #cheatsheet--guía-de-defensa---día-1-la-caja-fuerte)
2. [Día 2: Gestión de Inventario (Streams y BigInteger)]( #cheatsheet--guía-de-defensa---día-2-gestión-de-inventario)
3. [Día 3: Energía Sub-óptima (Algoritmo Greedy)]( #cheatsheet--guía-de-defensa---día-3-energía-sub-óptima)
4. [Día 4: Gestión de Inventario (Simulación de Autómatas)]( #cheatsheet--guía-de-defensa---día-4-gestión-de-inventario)
5. [Día 5: Semillas y Fertilizante (Merge Intervals)]( #cheatsheet--guía-de-defensa---día-5-semillas-y-fertilizante-intervalos)
6. [Día 6: Compactador de Basura (Parseo Visual y DRY)]( #cheatsheet--guía-de-defensa---día-6-compactador-de-basura-parseo-visual)
7. [Día 7: Teletransportación (Simulación vs Agregación)]( #cheatsheet--guía-de-defensa---día-7-teletransportación-simulación-vs-agregación)
8. [Día 8: Problema de Parada (Kruskal y Union-Find)]( #cheatsheet--guía-de-defensa---día-8-problema-de-parada-grafos)
9. [Día 9: El Nuevo Teatro (Geometría Computacional)]( #cheatsheet--guía-de-defensa---día-9-el-nuevo-teatro-geometría-computacional)
10. [Día 10: Tubo de Rayos Catódicos (Álgebra Lineal / RREF)]( #cheatsheet--guía-de-defensa---día-10-tubo-de-rayos-catódicos-álgebra-lineal)
11. [Día 11: Generadores Termoeléctricos (Grafos, DFS y DP)]( #cheatsheet--guía-de-defensa---día-11-generadores-termoeléctricos-grafos-y-dp)
12. [Día 12: Granja de Árboles (Exact Cover & Backtracking)]( #cheatsheet--guía-de-defensa---día-12-granja-de-árboles-exact-cover--backtracking)

---
---

# Cheatsheet / Guía de Defensa - Día 1: La Caja Fuerte

> **Rol**: Estudiante defendiendo Diseño de Software.
> **Objetivo (5 minutos)**: Demostrar que no solo "resuelve" el problema, sino que está diseñado para soportar cambios (Parte A -> Parte B) usando Inmutabilidad y Patrones de Diseño.

---

## 1. Introducción (1 minuto)
"Buenos días. El problema del Día 1 simula una cerradura de seguridad giratoria.
Aunque parece un ejercicio de bucles simple, decidí enfocarlo como un ejercicio de **Modelado de Estado Inmutable** y **Desacoplamiento de Algoritmos**.
Quería evitar el típico código espagueti con variables globales y estados mutables modificados desde cualquier sitio."

---

## 2. Arquitectura y Decisiones (2 minutos)

### A. Inmutabilidad (`Dial` Record)
"Mi clase `Dial` es inmutable. No tengo un método `void setPosition()`.
Tengo `Dial rotate()`, que devuelve un **nuevo** dial.
*   **Defensa**: Esto elimina errores de efectos secundarios (side-effects). Si necesitara paralelizar el proceso o guardar un historial de estados para 'deshacer' (undo), esta estructura lo soporta nativamente sin locks."

### B. El Patrón Strategy (La Clave)
"Lo más interesante ocurrió al pasar de la Parte A a la Parte B.
*   **Parte A**: Solo importa dónde termina el dial.
*   **Parte B**: Importa cada paso intermedio.
En lugar de duplicar el Servicio o llenar el código de `if (isPartB)`, introduje una interfaz `CountingStrategy`.
El Servicio simplemente pregunta a la estrategia: *'¿Cuántos puntos vale este movimiento?'*.
*   La estrategia A (`simpleCheck`) dice: '0 o 1 (solo al final)'.
*   La estrategia B (`continuousCheck`) dice: 'N (cuenta cada paso)'."

---

## 3. Puntos Clave del Código (Snippet para señalar)

"Si se fija en el `SecurityService`, verá cómo uso Streams de Java de forma funcional. No hay bucles `for` tradicionales."

```java
// SecurityService.java
.reduce(
    new ProcessState(Dial.createDefault(), 0), // Estado Inicial
    (state, order) -> {
        // La Estrategia decide cuánto sumar, desacoplada de la lógica de giro
        int hits = strategy.count(state.dial(), order);
        
        // El giro produce un NUEVO estado, no muta el anterior
        Dial nextDial = state.dial().rotate(order);
        
        return new ProcessState(nextDial, state.totalCount() + hits);
    },
    ...
);
```

---

## 4. Preguntas de Examen (Posibles Q&A)

### P1: ¿Por qué usaste `Math.floorMod` en lugar de `%`?
*   **Respuesta**: "Porque en Java, el operador `%` puede devolver negativos. `-5 % 100` es `-5`, lo cual rompería el array circular. `Math.floorMod(-5, 100)` devuelve correctamente `95`. Es fundamental para la implementación correcta de aritmética modular."

### P2: ¿No es ineficiente crear un objeto `Dial` nuevo en cada giro?
*   **Respuesta**: "En Java moderno, la creación de objetos de vida corta (como este Record) es extremadamente barata (Eden Space collection). El beneficio en mantenibilidad y seguridad y la optimización del compilador (Escape Analysis) superan con creces el costo marginal de memoria."

### P3: ¿Cuál es la complejidad temporal de tu solución?
*   **Respuesta**:
    *   **Parte A**: **O(N)**. (N instruciones, O(1) por giro).
    *   **Parte B**: **O(N * M)**, donde M es la magnitud del giro. Como tengo que simular el paso-a-paso para contar visitas intermedias, depende de la longitud del arco recorrido."

---

## 5. Resumen Final
"En resumen, he transformado un script imperativo en un sistema orientado a objetos, robusto y extensible, preparado para futuros cambios en las reglas de puntuación sin necesidad de tocar el núcleo de la lógica de negocio."

---

## 6. Estructura de Archivos

### Model (`model`)
*   `Dial.java`: El corazón del dominio. Inmutable.
*   `Direction.java`: Enum para Izquierda/Derecha.
*   `Order.java`: DTO/Record para pasar instrucciones.
*   `CountingStrategy.java` (Parte B): Strategy para cambiar la lógica de conteo.

### Parser (`parser`)
*   `DialParser.java`: Convierte Strings "R50" en objetos `Order`.

### Service (`service`)
*   `SecurityService.java`: El orquestador que une el parser, el modelo y la estrategia.

---
---

# Cheatsheet / Guía de Defensa - Día 2: Inventario Fantasma

> **Rol**: Estudiante defendiendo Rendimiento y Escalabilidad.
> **Objetivo (5 minutos)**: Demostrar cómo manejar grandes volúmenes de datos sin colapsar la memoria (Lazy Evaluation).

---

## 1. Introducción (1 minuto)
"Buenos días. En el Día 2, el problema es analizar rangos de códigos de producto para encontrar patrones numéricos.
El reto oculto aquí no es el patrón en sí, sino la **Escalabilidad**. Los rangos pueden contener millones de IDs.
Si intentamos meterlos todos en una lista, el servidor explotaría (OutOfMemory).
Mi solución se centra en **Streams y Evaluación Perezosa**."

---

## 2. Decisiones de Arquitectura (2 minutos)

### A. Lazy Evaluation con Streams (`ProductRange`)
"Este es el punto más fuerte de mi diseño.
La clase `ProductRange` es un Record que guarda solo el inicio y fin (`Start`, `End`).
No contiene los números.
Cuando el servicio pide procesarlos, llamo a `.stream()`, que usa `Stream.iterate`.
*   **Defensa**: Profe, esto significa que los números se 'inventan' uno a uno en tiempo de CPU justo cuando se necesitan. Jamás residen todos en memoria RAM a la vez. Podría procesar un rango de 0 a Infinito y mi consumo de RAM seguiría siendo constante (O(1))."

### B. BigInteger
"Decidí usar `BigInteger` en lugar de `long`.
*   **Justificación**: Aunque es más lento, asegura completitud. En sistemas de inventario reales (como UUIDs numéricos), los identificadores a menudo superan los 64 bits. Preferí robustez lógica sobre micro-optimización de CPU."

### C. Strategy Pattern (`ValidationStrategy`)
"Igual que en el Día 1, la definición de 'ID Inválido' cambia.
*   **Parte A**: `exactTwice` (mitades iguales).
*   **Parte B**: `atLeastTwice` (patterns repetidos).
Inyecto la estrategia en el `InventoryService`. Esto respeta el Principio de Responsabilidad Única (SRP): el Servicio sabe *cómo recorrer* IDs, la Estrategia sabe *cómo validarlos*."

---

## 3. Puntos Clave del Código (Snippet para señalar)

"Fíjese en esta tubería (pipeline) de procesamiento. Es pura programación declarativa:"

```java
// InventoryService.java
return Pattern.compile(",")
    .splitAsStream(rawInput)             // 1. Stream de Strings "10-20"
    .map(parser::parse)                  // 2. Stream de Rangos
    .flatMap(ProductRange::stream)       // 3. EXPLOSIÓN CONTROLADA: Stream unidimensional de BigIntegers
    .filter(strategy::isInvalid)         // 4. Filtrado Polimórfico
    .reduce(BigInteger.ZERO, BigInteger::add); // 5. Reducción final
```

---

## 4. Preguntas de Examen (Posibles Q&A)

### P1: ¿Por qué `flatMap`?
*   **Respuesta**: "Porque tengo un Stream de Rangos (`Stream<ProductRange>`), pero necesito evaluar números individuales. `flatMap` toma cada rango y lo 'abre' o despliega en su contenido, aplanando todo en un único `Stream<BigInteger>` continuo."

### P2: ¿Qué pasa si el rango es muy grande?
*   **Respuesta**: "Nada. Como uso Streams, el procesamiento es **Lazy**. Si después pusiera un `.limit(10)`, el sistema solo generaría los 10 primeros números y pararía, aunque el rango fuera de mil millones. Nada se calcula hasta que el terminal operation (`reduce`) lo pide."

### P3: ¿Tu algoritmo de la Parte B es eficiente?
*   **Respuesta**: "Verificar si un string está formado por patrones repetidos implica probar divisores. Es costoso pero necesario. Gracias a dividir responsabilidad, pude optimizar solo la clase `ValidationStrategy` sin tocar el resto del código."

---

## 5. Resumen Final
"He diseñado un sistema de procesamiento de datos por streaming capaz de manejar volumen infinito de datos con memoria constante, desacoplando la lógica de obtención de datos de la lógica de validación de negocio."

---

## 6. Estructura de Archivos

### Model (`model`)
*   `ProductRange.java`: Define el intervalo. Generador de Streams.
*   `ValidationStrategy.java`: Interfaz funcional para reglas de negocio.

### Parser (`parser`)
*   `RangeParser.java`: Parsea "Start-End".

### Service (`service`)
*   `InventoryService.java`: Pipeline de procesamiento.

---
---

# Cheatsheet / Guía de Defensa - Día 3: Energía Sub-óptima

> **Rol**: Analista de Algoritmos.
> **Objetivo (5 minutos)**: Justificar por qué la solución Voraz (Greedy) es matemáticamente correcta y óptima en rendimiento.

---

## 1. Introducción (1 minuto)
"En el Día 3, el problema parece simple: 'Elige dígitos de una lista para formar el mayor número'.
*   Parte A: Elige 2. (Fácil, doble bucle).
*   Parte B: Elige 12.
El problema es combinatorio. Si intentamos todas las combinaciones, el tiempo explota.
Mi solución implementa un **Algoritmo Greedy (Voraz)** generalizado."

---

## 2. Arquitectura y Decisiones (2 minutos)

### A. El Algoritmo Greedy (`GreedySubsequenceStrategy`)
"La intuición es simple: Para tener el número más grande, quiero el dígito más grande posible al principio (izquierda).
En lugar de buscar recursivamente, hago lo siguiente para cada posición que necesito llenar:
1.  Miro el rango de dígitos disponibles (desde donde me quedé hasta el final - los que me faltan por coger).
2.  Escojo el dígito más alto. Si hay varios iguales (ej. dos 9s), cojo el primero que encuentro para dejar más opciones libres a la derecha.
3.  Repito."

### B. Generalización con Strategy
"No escribí dos algoritmos distintos.
La clase `GreedySubsequenceStrategy` recibe un parámetro `digitsToPick` (K).
*   **Parte A**: `new GreedySubsequenceStrategy(2)`
*   **Parte B**: `new GreedySubsequenceStrategy(12)`
Esto demuestra el poder de la abstracción. El mismo código resuelve el caso trivial y el complejo."

---

## 3. Puntos Clave del Código (Snippet para señalar)

"Aquí está la lógica de ventana deslizante del Greedy:"

```java
// GreedySubsequenceStrategy.java
for (int k = digitsToPick; k > 0; k--) {
    // Límite derecho: Debo dejar al menos k-1 elementos libres al final
    int searchEnd = n - k;

    // Busco el máximo en la ventana [currentSearchStart, searchEnd]
    for (int i = currentSearchStart; i <= searchEnd; i++) {
        int val = batteries.get(i);
        if (val == 9) { // Optimización: 9 es el máximo posible, break temprano
             bestDigit = 9; bestIndex = i; break; 
        }
        if (val > bestDigit) { ... }
    }
    
    result = append(bestDigit);
    currentSearchStart = bestIndex + 1; // Avanzo la ventana
}
```

---

## 4. Preguntas de Examen (Posibles Q&A)

### P1: ¿Por qué funciona Greedy aquí? ¿No necesitas Programación Dinámica?
*   **Respuesta**: "Funciona porque el problema tiene **Subestructura Óptima** y la **Propiedad de Elección Voraz**.
Al construir un número entero posicional, un dígito mayor en una posición más significativa (izquierda) *siempre* produce un número total mayor, sin importar qué venga después. Por tanto, maximizar el dígito actual localmente garantiza maximizar el resultado global. No es necesario 'mirar adelante' (backtracking)."

### P2: ¿Cuál es la complejidad?
*   **Respuesta**: "$O(K \times N)$, donde N es la longitud del banco y K los dígitos a elegir. Como K es constante pequeña (12), es efectivamente lineal $O(N)$."

---

## 5. Resumen Final
"He transformado un problema potencialmente exponencial en uno lineal aplicando una estrategia voraz justificada por las propiedades del sistema numérico decimal."

---

## 6. Estructura de Archivos

### Model (`model`)
*   `BatteryBank.java`: Value Object.
*   `JoltageStrategy.java`: Strategy base.

### Parser (`parser`)
*   `BatteryParser.java`: Parsea input.

### Service (`service`)
*   `EnergyService.java`: Aplica la estrategia y suma.


---
---

# Cheatsheet / Guía de Defensa - Día 4: Gestión de Inventario

> **Rol**: Ingeniero de Sistemas explicando una simulación física discreta.
> **Objetivo (5 minutos)**: Justificar el uso de objetos inmutables en una simulación de estados iterativos.

---

## 1. Introducción (1 minuto)
"En el Día 4, simulamos el vaciado de un almacén.
Imaginen una montaña de cajas vista desde arriba. Solo podemos sacar las que están en el borde o "sueltas" (pacos vecinos).
*   **Parte A**: ¿Qué puedo sacar *ahora*? (Análisis estático).
*   **Parte B**: Si saco esas, ¿cuáles quedan libres? Y así sucesivamente hasta vaciar el almacén. (Simulación dinámica)."

---

## 2. Decisiones de Arquitectura (2 minutos)

### A. Simulación por Pasos Discretos
"El problema no se puede resolver con una fórmula. Es un sistema dinámico: quitar una caja cambia la 'física' del entorno para las demás.
Por eso implementé un bucle `while(true)` en `LogisticsService`.
1.  Calculo candidatos (`findAccessibleCoordinates`).
2.  Si no hay, termino.
3.  Si hay, genero un **Nuevo Estado** del almacén sin esas cajas."

### B. Inmutabilidad del Estado (`Warehouse`)
"Esta es mi decisión de diseño más importante.
Podría haber modificado la matriz `boolean[][]` *in-place*. Sería más rápido en nanosegundos.
**PERO**, decidí hacer `removeRolls()` returning `new Warehouse(...)`.
*   **Defensa**: Profe, esto evita el clásico error de simulación donde borras una entidad en la it `i` y luego en la it `i+1` (del mismo turno) consultas esa celda y crees que está vacía, falseando la lógica de vecindad. Al separar `Estado Actual` de `Estado Siguiente`, la simulación es determinista y segura."

### C. Vecindad (Moore Neighborhood)
"Para definir la accesibilidad uso las 8 direcciones (N, S, E, O y diagonales). Un simple array `DIRECTIONS` en el modelo hace el código muy limpio comparado con 8 `if`s anidados."

---

## 3. Puntos Clave del Código (Snippet para señalar)

"Mire cómo el Servicio orquesta la simulación como una máquina de estados:"

```java
// LogisticsService.java
while (true) {
    // 1. Query: ¿Qué podemos quitar en este estado inmutable?
    List<Coordinate> toRemove = currentWarehouse.findAccessibleCoordinates();

    if (toRemove.isEmpty()) break; // Condición de parada

    totalRemoved += toRemove.size();

    // 2. Transición: Creo el siguiente estado del mundo
    currentWarehouse = currentWarehouse.removeRolls(toRemove);
}
```

---

## 4. Preguntas de Examen (Posibles Q&A)

### P1: ¿No es ineficiente copiar la matriz entera en cada paso?
*   **Respuesta**: "Sí, tiene un coste de memoria $O(N)$ por paso. Pero dado que el grid es 100x100 o similar, es despreciable (KBs) comparado con la seguridad que da la inmutabilidad. Si fuera una matriz de 10k x 10k, entonces optimizaría usando 'Double Buffering' (dos matrices que se alternan) en lugar de crear objetos nuevos."

### P2: ¿Por qué usas un `boolean[][]` y no un `Set<Coordinate>`?
*   **Respuesta**: "Por rendimiento de acceso. Consultar `grid[r][c]` es $O(1)$ inmediato y localidad de caché perfecta. Un `HashSet` tiene overhead de hashing y peor localidad de memoria. Para grids densos y acotados, la matriz primitiva gana."

---

## 5. Resumen Final
"He modelado el problema como una simulación de autómata celular (como el Juego de la Vida) donde las reglas de transición generan estados inmutables sucesivos, garantizando corrección lógica."

---

## 6. Estructura de Archivos

### Model (`model`)
*   `Warehouse.java`: Contenedor inmutable del Grid. Lógica de vecindad.
*   `Coordinate.java`: Record (x, y).

### Service (`service`)
*   `LogisticsService.java`: El bucle de simulación principal.

### Parser (`parser`)
*   `WarehouseParser.java`: Transforma texto en boolean matrix.

---
---

# Cheatsheet / Guía de Defensa - Día 5: Semillas y Fertilizante (Intervalos)

> **Rol**: Estudiante explicando su solución al Profesor.
> **Tono**: Matemático y eficiente. Justificando la optimización de rangos.

---

## 1. ¿De qué va el problema?
"Profesor, este es el clásico problema de **Unión de Intervalos** (como agendar reuniones en una sala).
Tenemos rangos de 'frescura' válidos (ej. `[1-10]`, `[5-15]`, `[20-25]`).
*   **Parte A**: Comprobación simple. ¿Cae el número `X` en alguno de estos rangos?
*   **Parte B**: Calcular cuántos números *únicos* hay en total. Si los rangos se solapan (`[1-10]` y `[5-15]`), no puedo simplemente sumar sus longitudes (10 + 11 = 21) porque contaría los números del 5 al 10 dos veces. Tengo que fusionarlos primero."

---

## 2. Decisiones de Arquitectura

### A. Objeto `FreshnessRange`
"Usé un `record` porque un rango es inmutable por definición.
Lo importante aquí es que implementa **`Comparable<FreshnessRange>`**."
*   **¿Por qué Comparable?**: "Para fusionar rangos eficientemente, necesito ordenarlos por su inicio. Si están desordenados, tendría que compararlos todos contra todos (O(N^2)). Al hacerlos comparables, puedo usar `Collections.sort()` para dejarlos listos para un barrido lineal."

### B. BigInteger
*   **Justificación**: "El enunciado insinuaba rangos masivos (como direcciones de memoria o identificadores de semillas). Usar `int` o `long` era arriesgado. Con `BigInteger`, la solución aguanta números de cualquier tamaño, aunque sacrifique un poco de velocidad de CPU."

---

## 3. El Algoritmo de Fusión (Merge Intervals)
"Profesor, si intentas hacer un array de booleanos `boolean[MAX_ID]`, te quedas sin memoria (Heap Space) instantáneamente si el ID es 50 mil millones.
Mi solución usa el algoritmo de **Línea de Barrido (Sweep Line)**:"

1.  **Ordenar**: `O(N log N)`. Ordeno los rangos por su punto de inicio.
2.  **Barrido Lineal**: `O(N)`. Itero una sola vez.
    *   Tengo un `currentRange`.
    *   Miro el `nextRange`.
    *   Si se solapan o **se tocan** (adyacentes), los fusiono: `current` crece.
    *   Si no, guardo `current` y empiezo uno nuevo con `next`.

### Detalle Crítico: Adyacencia
"Ojo con esto: `[1-5]` y `[6-10]`. No se solapan estrictamente, pero son continuos. Mi método `overlapsOrAdjoins` detecta esto (`this.end + 1 >= next.start`) para fusionarlos en un solo `[1-10]`."

---

## 4. Puntos Clave del Código (Snippet para señalar)

### A. Lógica de Fusión (En `FreshnessRange`)
"La lógica de solapamiento es sutil pero robusta. Comparo 'mi inicio con su fin' y 'su inicio con mi fin'."

```java
public boolean overlapsOrAdjoins(FreshnessRange other) {
    // Truco: Sumo 1 al final para detectar adyacencia ([1-5] y [6-10])
    BigInteger thisEndAdjusted = this.end.add(BigInteger.ONE);
    
    // Lógica estándar de intersección de intervalos
    return this.start.compareTo(otherEndAdjusted) <= 0 && 
           other.start.compareTo(thisEndAdjusted) <= 0;
}
```

### B. El Bucle Principal (En `CafeteriaService`)
"Aquí se ve la eficiencia. Un solo `for` loop después de ordenar."

```java
Collections.sort(ranges); // Paso 1: Ordenar

FreshnessRange current = ranges.get(0);
for (int i = 1; i < ranges.size(); i++) {
    FreshnessRange next = ranges.get(i);

    if (current.overlapsOrAdjoins(next)) {
        // Fusión: Me quedo con el inicio más bajo (ya lo tengo) y el fin más alto
        current = current.merge(next);
    } else {
        // Hueco encontrado: Guardo el anterior y sigo
        mergedRanges.add(current);
        current = next;
    }
}
mergedRanges.add(current); // No olvidar el último
```

---

## 5. Preguntas de Examen

*   **Profesor**: *¿Por qué no creaste un Set con todos los números válidos?*
    *   **Tú**: "Complejidad espacial. Un rango `[0 - 1.000.000.000]` ocuparía gigabytes en un Set. Mi solución ocupa lo que ocupen 2 objetos BigInteger (start y end). O(1) memoria por rango."
*   **Profesor**: *¿Qué complejidad temporal tiene tu solución Part B?*
    *   **Tú**: "**O(N log N)** dominada por el ordenamiento. El resto es lineal."

---

## 6. Resumen
"Evité la materialización de los datos (sets gigantes) y opté por una manipulación simbólica de los intervalos (fusión matemática), resolviendo el problema de escalabilidad espacial y temporal."

---

## 7. Estructura de Archivos

### Model (`model`)
*   `FreshnessRange.java`: Record con lógica `merge` y `overlaps`.

### Service (`service`)
*   `CafeteriaService.java`: Ejecuta el sort y el loop de fusión.

### Parser (`parser`)
*   `RangeParser.java`: (String -> BigInteger Pair).

---
---

# Cheatsheet / Guía de Defensa - Día 6: Compactador de Basura (Parseo Visual)

> **Rol**: Estudiante explicando su solución al Profesor.
> **Tono**: Ingenioso, destacando la reutilización de código en el parser.

---

## 1. ¿De qué va el problema?
"Profesor, tenemos una hoja de papel (archivo de texto) con 'islas' de problemas matemáticos separadas por espacios en blanco. Visualmente son bloques distintos.
*   **Parte A**: Identificar cada bloque y sumar sus números (leyendo normal, izquierda a derecha).
*   **Parte B**: Identificar los *mismos* bloques, pero ahora los números están escritos en vertical (columna a columna). ¡El parser tiene que cambiar su forma de leer!"

---

## 2. Decisiones de Arquitectura

### A. El Desafío del Parseo ("Islas")
"Lo difícil no es sumar, es saber *qué sumar*.
Mi `WorksheetParser` no lee línea por línea como siempre. Lee **Columna por Columna** para detectar fronteras."
*   *Algoritmo*: "Escaneo el ancho del archivo. Si encuentro una columna totalmente vacía, sé que un bloque ha terminado y otro va a empezar. Guardo `startCol` y `endCol`."

### B. Patrón Strategy (Vía Enum `Mode`)
"Para  no escribir dos parsers gigantes casi idénticos, encapsulé la lógica de extracción en un Enum `Mode`."
*   **HORIZONTAL_ROWS**: "Dentro del bloque delimitado, corto filas (`substring`) y parseo números."
*   **VERTICAL_COLS**: "Dentro del mismo bloque, itero columnas, construyendo los números verticalmente (`StringBuilder` char a char hacia abajo)."
*   **Beneficio**: "La lógica compleja de detección de bordes (islas) se escribe una sola vez. **DRY (Don't Repeat Yourself)** puro."

---

## 3. Puntos Clave del Código (Snippet para señalar)

### A. Detección de Bloques (Reutilizable)
"Mire cómo detecto las 'islas' agnósticamente del contenido:"

```java
// WorksheetParser.java
for (int col = 0; col < width; col++) {
    boolean isColEmpty = isColumnEmpty(lines, col);

    if (!isColEmpty && !insideProblem) {
        // Empieza una isla
        insideProblem = true;
        currentStartCol = col;
    } 
    else if (isColEmpty && insideProblem) {
        // Termina una isla -> Extraemos el problema
        problems.add(extractProblem(lines, currentStartCol, col - 1, mode));
        insideProblem = false;
    }
}
```

### B. Lectura Vertical (La novedad de la Parte B)
"Para leer verticalmente sin rotar toda la matriz (que sería lento), simplemente invierto mis bucles anidados:"

```java
// extractVertical
for (int c = startCol; c <= endCol; c++) {
    StringBuilder numBuilder = new StringBuilder();
    // Bajamos por la columna 'c'
    for (int r = 0; r < height - 1; r++) {
       char ch = lines.get(r).charAt(c);
       if (ch != ' ') numBuilder.append(ch);
    }
    // 'numBuilder' ahora tiene el número vertical (ej: "1\n2\n3" -> "123")
    operands.add(new BigInteger(numBuilder.toString()));
}
```

---

## 4. Preguntas de Examen

*   **Profesor**: *¿Por qué no usar `String.split("\\s+")`?*
    *   **Tú**: "Porque perdería la estructura 2D. Necesito saber qué número está encima de cuál. El split destruye la alineación vertical. Mi enfoque conserva la geometría de la hoja."
*   **Profesor**: *¿Cómo manejas si una línea es más corta que otras?*
    *   **Tú**: "Tengo métodos auxiliares `safeCharAt` y `safeSubstring` que devuelven espacios en blanco si intento acceder fuera de los límites del String. Esto evita `IndexOutOfBoundsException` en archivos irregulares."

## 5. Resumen
"He transformado un problema de procesamiento de texto desestructurado en un problema geométrico (detectar rectángulos vacíos), permitiendo cambiar la dirección de lectura (H vs V) sin tocar la lógica de detección de bloques."

---

## 6. Estructura de Archivos (Model-Parser-Service)

### Model
*   `MathProblem.java`: Record (args, operator).

### Parser
*   `WorksheetParser.java`: Detecta "Islas" y lee H/V.

### Service
*   `TrashCompactorService.java`: Ejecutor y acumulador.

---
---

# Cheatsheet / Guía de Defensa - Día 7: Teletransportación (Simulación vs Agregación)

> **Rol**: Estudiante explicando su solución al Profesor.
> **Tono**: Profundo, contrastando enfoques ingenuos vs optimizados.

---

## 1. ¿De qué va el problema?
"Profesor, imaginemos una máquina de Galton o un pachinko. Dejamos caer partículas desde arriba (`S`) por una red de tuberías.
*   **Parte A**: Simular una sola partícula. Abajo hay divisores (`^`) que la clonan (izquierda y derecha). Queremos contar cuántos divisores activos hay.
*   **Parte B**: Queremos calcular cuántas partículas salen por el fondo si la red es muy profunda. El crecimiento es exponencial ($2^N$), así que simularlas una a una es imposible."

---

## 2. Decisiones de Arquitectura

### A. La Red (`Manifold`)
"Separé el terreno de juego (`Manifold`) de la lógica.
Es una clase inmutable que esencialmente encapsula un `char[][]`.
*   *¿Por qué?*: Para no estar pasando `List<String>` por todos lados y centralizar preguntas como `isSplitter(r, c)` o `isInside(r, c)`."

### B. El Cambio de Paradigma (Parte A vs Parte B)
"Aquí está el quid de la práctica."

*   **Enfoque Parte A (Simulación Discreta)**:
    *   "Uso un `Set<Beam>`. Cada objeto `Beam` es una partícula `(row, col)`.
    *   Itero y muevo cada partícula.
    *   Funciona bien para 100 o 1000 partículas."

*   **Enfoque Parte B (Agregación de Estados)**:
    *   "Si hay 1 billón de partículas, no puedo tener 1 billón de objetos en memoria.
    *   Pero noté algo: **No me importa quién es la partícula, solo DÓNDE está**.
    *   En lugar de `List<Particle>`, uso `Map<Columna, Cantidad>`.
    *   Si tengo 5 millones de partículas en la columna 10 y caen a un divisor, simplemente sumo 5 millones a la columna 9 y 5 millones a la columna 11.
    *   Complejidad: Pasamos de Exponencial $O(2^N)$ a Polinómica $O(Filas \times Columnas)$."

---

## 3. Puntos Clave del Código (Snippet para señalar)

### A. El Mapa de Líneas Temporales (Aggregation)
"Fíjese como proceso la fila entera de golpe usando un Mapa (`activeTimelines`). La clave es la columna, el valor es el número de partículas."

```java
// TeleporterService.java
Map<Integer, BigInteger> nextRowTimelines = new HashMap<>();

for (Map.Entry<Integer, BigInteger> entry : activeTimelines.entrySet()) {
    int col = entry.getKey();
    BigInteger count = entry.getValue(); // ¡Tengo 'count' partículas aquí!

    // ... lógica de movimiento ...
    
    if (manifold.isSplitter(nextRow, col)) {
        // Bifurcación: Sumo 'count' a izquierda y derecha
        // El método merge es clave: si ya había partículas llegando ahí, las suma.
        nextRowTimelines.merge(col - 1, count, BigInteger::add);
        nextRowTimelines.merge(col + 1, count, BigInteger::add);
    }
}
```

### B. BigInteger
"De nuevo, `long` no es suficiente. $2^{100}$ es un número astronómico. `BigInteger` maneja la aritmética arbitraria necesaria para 'contar universos paralelos'."

---

## 4. Preguntas de Examen

*   **Profesor**: *¿Qué pasa si dos haces chocan en el mismo punto?*
    *   **Tú**: "En la Parte A, como uso un `Set<Beam>`, se fusionan automáticamente (dejan de ser dos entidades distintas). En la Parte B, el método `Map.merge(..., BigInteger::add)` suma sus cantidades. Matemáticamente es lo mismo: interferencia constructiva."
*   **Profesor**: *¿Podrías haber usado recursividad?*
    *   **Tú**: "En la Parte A sí (DFS), pero en la Parte B la recursividad simple recorrería el mismo sub-árbol millones de veces. Tendría que haber añadido **Memoización** (cachear resultados). Mi enfoque iterativo (fila a fila) es básicamente Programación Dinámica Bottom-Up."

## 5. Resumen
"Identifiqué que las partículas eran indistinguibles y fungibles. Eso me permitió cambiar la simulación de agentes individuales por una simulación de flujo de fluidos (densidades), resolviendo el problema de la explosión combinatoria."

---

## 6. Estructura de Archivos (Model-Parser-Service)

### Model
*   `Beam.java`: Partícula individual (coord).
*   `Manifold.java`: El tablero/grid inmutable.

### Parser
*   `ManifoldParser.java`: Carga el grid.

### Service
*   `TeleporterService.java`: Motor de simulación (Discreta y Agregada).

---
---

# Cheatsheet / Guía de Defensa - Día 8: Problema de Parada (Grafos)

> **Rol**: Estudiante explicando su solución al Profesor.
> **Tono**: Académico, centrado en Teoría de Grafos Clásica (MST).

---

## 1. ¿De qué va el problema?
"Profesor, tenemos un conjunto de antenas (puntos 3D). Queremos conectarlas con cables, pero el cable es caro (peso = distancia).
*   **Parte A**: Simulamos conectar solo los `K` cables más cortos y vemos qué grupos de antenas se forman (Componentes Conexos).
*   **Parte B**: Queremos encontrar la **Arista Crítica**. Es decir, el cable específico que hace que, por fin, *todas* las antenas estén conectadas en una sola red unificada.
    *   *Traducción*: Esto es buscar la última arista añadida en un Árbol de Expansión Mínima (Minimum Spanning Tree - MST)."

---

## 2. Decisiones de Arquitectura

### A. Estructura de Datos (`UnionFind` / DSU)
"Podría haber usado BFS para ver si el grafo está conectado en cada paso, pero eso es lentísimo ($O(Edges \times Nodes)$).
Usé **Union-Find (Disjoint Set Union)**.
*   Es la estructura perfecta para gestión de componentes dinámicos.
*   Operaciones `union` y `find` son casi instantáneas ($O(\alpha(N))$ - Inversa de Ackermann).
*   Encapsulé toda la lógica fea de arrays (`parent[]`, `size[]`) en la clase `UnionFind`."

### B. Separación de Responsabilidades
*   `Point3D`: Solo sabe de geometría (distancias).
*   `Connection`: Representa una arista `(u, v, peso)`. Implementa `Comparable` para ordenarse sola.
*   `PlaygroundService`: Es el cerebro que ejecuta el algoritmo de Kruskal.

---

## 3. El Algoritmo: Kruskal
"Para resolver la Parte B, implementé el algoritmo de Kruskal de manual:"

1.  **Generar Aristas**: Creo conexiones entre todos los pares de puntos ($N^2$ aristas).
2.  **Ordenar**: Las ordeno de menor a mayor distancia.
3.  **Iterar y Unir**:
    *   Cojo la arista más barata.
    *   Si los nodos ya están conectados (`uf.find(u) == uf.find(v)`), la ignoro (formaría un ciclo, redundante).
    *   Si no, los uno (`uf.union(u, v)`).
4.  **Condición de Parada**: "El momento exacto en que mi estructura `UnionFind` me dice que solo queda **1 componente** (`uf.getCount() == 1`), sé que esa arista que acabo de añadir es la crítica. Paro y devuelvo el resultado."

---

## 4. Puntos Clave del Código (Snippet para señalar)

### A. Union-Find con Path Compression
"Esta línea es la que hace que el algoritmo vuele. Al buscar el padre, actualizo el puntero directamente a la raíz."

```java
public int find(int p) {
    if (parent[p] == p) return p;
    // Compresión de caminos recursiva
    parent[p] = find(parent[p]); 
    return parent[p];
}
```

### B. El Bucle de Kruskal (Service)
"Mire qué limpio queda el bucle principal. No hay lambdas complejas, solo lógica de grafos pura."

```java
for (Connection conn : allConnections) {
    // Si están en grupos separados...
    if (uf.find(conn.u()) != uf.find(conn.v())) {
        uf.union(conn.u(), conn.v()); // ¡Conectar!

        // ¿Ya es un solo grafo unificado?
        if (uf.getCount() == 1) {
            // ¡Eureka! Esta es la última arista necesaria.
            return calculateResult(points, conn);
        }
    }
}
```

---

## 5. Preguntas de Examen

*   **Profesor**: *¿Qué complejidad tiene tu algoritmo?*
    *   **Tú**: "$O(E \log E)$ donde $E$ es el número de aristas. El cuello de botella es ordenar las aristas. Las operaciones de Union-Find son despreciables."
*   **Profesor**: *¿Por qué no usaste el algoritmo de Prim?*
    *   **Tú**: "Prim es bueno para grafos densos, pero Kruskal es más intuitivo para este problema específico de 'ir añadiendo cables baratos hasta conectar todo'. La condición de parada (`count == 1`) es trivial de chequear en Kruskal con DSU, mientras que en Prim tendría que llevar la cuenta de nodos visitados."

## 6. Resumen
"Modelé el problema como la búsqueda de un MST. La clave del rendimiento fue el uso de la estructura de datos Union-Find con optimización de compresión de caminos, permitiendo procesar miles de conexiones instantáneamente."

---

## 7. Estructura de Archivos (Model-Parser-Service)

### Model
*   `Point3D.java`: Geometría.
*   `Connection.java`: Arista del grafo (Edge).
*   `UnionFind.java`: Estructura Disjoint Set Union (DSU) eficiente.

### Parser
*   `PointParser.java`: Lee los puntos.

### Service
*   `PlaygroundService.java`: Algoritmo de Kruskal.

---
---

# Cheatsheet / Guía de Defensa - Día 9: El Nuevo Teatro (Geometría Computacional)

> **Rol**: Estudiante explicando su solución al Profesor.
> **Tono**: Geométrico, enfocado en manejo de coordenadas y algoritmos espaciales.

---

## 1. ¿De qué va el problema?
"Profesor, tenemos un teatro con forma irregular (un polígono definido por vértices).
*   **Parte A**: Buscamos el rectángulo de área máxima que se puede formar uniendo dos vértices cualesquiera. (Fuerza bruta simple).
*   **Parte B**: El desafío real. Solo valen rectángulos que estén **totalmente contenidos dentro del polígono**. Si un rectángulo de área gigante corta una pared o se sale, no vale."

---

## 2. Decisiones de Arquitectura

### A. Encapsulamiento del Polígono (`Polygon`)
"No quería un `main` lleno de `if (x > ... && y < ...)`.
Toda la matemática sucia está escondida en la clase `Polygon`.
El método clave es `containsRectangle`. Si devuelve `true`, el servicio confía ciegamente."

### B. Pruning / Poda Lógica
"En la Parte B, verificar si un rectángulo está dentro es caro (matemáticamente).
Así que optimicé el bucle:
*   Primero calculo el área candidata.
*   Si `candidata <= maxAreaActual`, **ni me molesto en validar la geometría**. `continue`.
*   Esto ahorra miles de comprobaciones de Ray Casting innecesarias."

---

## 3. El Algoritmo: Ray Casting (Rayo al Infinito)
"Para saber si el rectángulo está dentro, compruebo dos cosas:
1.  **Intersección**: ¿Algún lado del rectángulo cruza algún muro del teatro? Si sí -> FUERA.
2.  **Contención (El caso Corner-Case)**:
    *   *Profesor*: "¿Qué pasa si el rectángulo es pequeño y no toca ninguna pared, pero está en el patio exterior (polígono cóncavo)?"
    *   *Tú*: "Uso **Ray Casting** desde el centro del rectángulo. Lanzo un rayo horizontal hacia el infinito y cuento cuántas paredes cruza.
        *   Impar = DENTRO.
        *   Par = FUERA."

---

## 4. Puntos Clave del Código (Snippet para señalar)

### A. Ray Casting (Point in Polygon)
"Aquí está la implementación del teorema. Lanzo un rayo hacia la derecha (`x < segX`) y cuento cruces."

```java
// Polygon.java
private boolean isPointInside(double x, double y) {
    int intersections = 0;
    for (Segment edge : edges) {
        if (edge.isVertical()) { // simplificación: solo bordes verticales
            // ... (chequeo de rangos Y)
            if (y > segMinY && y < segMaxY && x < segX) {
                intersections++; // ¡Rayo cruzó pared!
            }
        }
    }
    return (intersections % 2) != 0; // Impar = Dentro
}
```

### B. Validación Completa
"No basta con mirar el centro. Hay que mirar intersecciones de bordes también."

```java
public boolean containsRectangle(Tile t1, Tile t2) {
    // 1. Verificar si alguna pared corta el rectángulo
    for (Segment edge : edges) {
        if (edge.intersectsInterior(minX, maxX, minY, maxY)) return false;
    }
    // 2. Verificar que el centroide esté dentro (para evitar falsos positivos externos)
    return isPointInside(midX, midY);
}
```

---

## 5. Preguntas de Examen

*   **Profesor**: *¿Qué complejidad tiene tu algoritmo?*
    *   **Tú**: "$O(N^3)$. Dos bucles para elegir par de vértices ($N^2$) y dentro, un bucle sobre las aristas para validar ($N$). Como $N$ (número de vértices) es bajo, es aceptable. Si $N$ fuera 1 millón, necesitaría un algoritmo de Barrido (Sweep Line)."
*   **Profesor**: *¿Por qué usas `double` para el centroide?*
    *   **Tú**: "Porque el punto medio entre dos enteros `(3, 4)` es `3.5`. Si usara `int`, perdería precisión y el rayo podría fallar justo en el borde."

## 6. Resumen
"Pasé de un problema combinatorio simple a uno geométrico robusto, implementando manualmente algoritmos de intersección y Ray Casting para garantizar la validez topológica de la solución."

---

## 7. Estructura de Archivos (Model-Parser-Service)

### Model
*   `Tile.java`: Vértice 2D.
*   `Polygon.java`: Lógica geométrica (contains, intersects).

### Parser
*   `TileParser.java`: Carga vértices.

### Service
*   `TheaterService.java`: Optimizador de áreas.

---
---

# Cheatsheet / Guía de Defensa - Día 10: Tubo de Rayos Catódicos (Álgebra Lineal)

> **Rol**: Estudiante de doctorado explicando su modelo al Profesor.
> **Tono**: Matemático y riguroso.

---

## 1. ¿De qué va el problema?
"Profesor, tenemos un sistema de máquinas tipo Arcade.
Pulsar el botón $X$ suma un vector $(a_x, a_y)$ a la garra.
Queremos llegar exactamente al punto $(P_x, P_y)$.
*   **Parte A**: Minimizar el coste (Tokens) para llegar. Espacio de búsqueda pequeño.
*   **Parte B**: Los objetivos están lejimísimos ($10^{13}$). La búsqueda en anchura (BFS) o Dijkstra moriría por falta de memoria/tiempo. Necesitamos una solución analítica."

---

## 2. Decisiones de Arquitectura

### A. Modelado Matemático (Sistemas de Ecuaciones)
"En lugar de ver esto como un grafo de estados, lo vi como un sistema de ecuaciones lineales:"
$$
A \cdot \text{presses}_A + B \cdot \text{presses}_B = \text{Target}
$$

### B. El Solver (`EquationSolver`)
"Implementé **Eliminación Gaussiana** para llevar la matriz a su forma escalonada reducida por filas (**RREF**)."
*   *¿Por qué RREF?*: "Nos dice inmediatamente si el sistema tiene solución única, ninguna solución (inconsistente) o infinitas soluciones (variables libres)."
*   *Separation of Concerns*: "El `Solver` no sabe de 'botones' o 'garras'. Solo sabe de matrices `double[][]`. Es reutilizable para cualquier problema lineal."

### C. Manejo de Variables Libres (Backtracking)
"Si el sistema es indeterminado (ej. dos botones hacen lo mismo, o hay más botones que dimensiones), uso **Recursividad** sobre las variables libres para buscar la *solución entera* óptima."

---

## 3. Puntos Clave del Código (Snippet para señalar)

### A. Eliminación Gaussiana (El núcleo matemático)
"Este es el algoritmo clásico que aprendimos en Álgebra 1. Hacemos ceros debajo y encima del pivote."

```java
// EquationSolver.java - RREF
for (int i = 0; i < numRows; i++) {
    if (i != pivotRow) {
        double factor = matrix[i][col];
        // Restar la fila del pivote a las demás para anular la columna
        for (int j = col; j <= numCols; j++)
            matrix[i][j] -= factor * matrix[pivotRow][j];
    }
}
```

### B. Validación de Enteros
"Como el problema exige pulsar botones un número entero de veces, no me valen soluciones como `3.5`. Verifico si la solución propuesta es entera dentro de un margen de error (Epsilon)."

```java
if (Math.abs(val - Math.round(val)) > EPSILON)
    return Long.MAX_VALUE; // Solución inválida (no entera)
```

---

## 4. Preguntas de Examen

*   **Profesor**: *¿Por qué usas `double` y EPSILON si la respuesta debe ser exacta?*
    *   **Tú**: "Porque Gaussian Elimination introduce errores de punto flotante al dividir. Usar `double` me permite resolver el sistema rápido, y luego simplemente compruebo si el resultado está 'suficientemente cerca' de un entero (`Math.abs(x - round(x)) < 1e-4`). Implementar fracciones (`RationalNumber`) habría sido overkill."
*   **Profesor**: *¿Cuál es la complejidad?*
    *   **Tú**: "Para la Parte B (sistema determinado 2x2), es **O(1)**. Constante matemática pura. Por eso mi solución tarda lo mismo si el target está a 10 metros o a 10 billones de años luz."

## 5. Resumen
"Transformé un problema de búsqueda combinatoria en uno de Álgebra Lineal Computacional, implementando un solver RREF genérico capaz de manejar sistemas determinados e indeterminados con restricciones de integridad."

---

## 6. Estructura de Archivos (Model-Parser-Service)

### Model
*   `Machine.java`: Coeficientes (Ax, Ay, Bx, By, Tx, Ty).

### Parser
*   `MachineParser.java`: Regex para extraer números.

### Service
*   `FactoryService.java`: Solver algebraico O(1).

---
---

# Cheatsheet / Guía de Defensa - Día 11: Generadores Termoeléctricos (Grafos y DP)

> **Rol**: Estudiante explicando su solución al Profesor.
> **Tono**: Deductivo, explicando cómo descomponer un problema complejo en sub-problemas simples.

---

## 1. ¿De qué va el problema?
"Profesor, tenemos una red de generadores conectados (un Grafo Dirigido).
*   **Parte A**: Contar cuántos caminos distintos existen desde el nodo 'start' (`you`) hasta el nodo 'end' (`out`). Como el grafo es un DAG (no tiene ciclos), los caminos son finitos, pero pueden ser millones.
*   **Parte B**: Ahora nos obligan a pasar por dos puntos de control específicos ('Waypoints') antes de salir. El problema es encontrar el número de caminos válidos que visitan ambos."

---

## 2. Decisiones de Arquitectura

### A. Estructura de Datos (`Network`)
"Es un grafo simple representado como una Lista de Adyacencia (`Map<String, List<String>>`).
Dado un nodo, puedo pedirle inmediatamente sus vecinos (`getOutputs`)."

### B. El Algoritmo: DFS con Memoización
"No usé Dijkstra ni BFS, porque no busco el camino *más corto*, busco *todos* los caminos.
Una búsqueda en profundidad (DFS) es lo natural.
*   *El Truco*: **Memoización**.
    *   Si desde el nodo `C` hay 5.000 formas de llegar al final...
    *   Y llego a `C` desde `A` y desde `B`...
    *   No quiero recalcular esas 5.000 rutas dos veces.
    *   Guardo `memo.put("C", 5000)` y la próxima vez lo devuelvo en O(1)."

### C. Descomposición del Problema (Parte B)
"En lugar de modificar el algoritmo de búsqueda para rastrear estados complejos ('¿He visitado A? ¿He visitado B?'), usé lógica combinatoria simple.
Para visitar A y B, solo hay dos rutas lógicas:
1.  Inicio -> A -> B -> Final
2.  Inicio -> B -> A -> Final

Calculo los segmentos por separado (`countPathsBetween`) y multiplico las posibilidades."

---

## 3. Puntos Clave del Código (Snippet para señalar)

### A. El Núcleo Recursivo (ReactorService)
"Aquí se ve la recursión con caché. Si ya resolví este nodo, devuelvo el valor guardado. Si no, sumo los caminos de mis hijos."

```java
private BigInteger countRecursive(String current, String target, Network network, Map<String, BigInteger> memo) {
    if (current.equals(target)) return BigInteger.ONE; // Base case: Encontré 1 camino
    if (memo.containsKey(current)) return memo.get(current); // Cache hit

    BigInteger total = BigInteger.ZERO;
    for (String neighbor : network.getOutputs(current)) {
        // Recursión: Caminos desde el vecino hasta el final
        total = total.add(countRecursive(neighbor, target, network, memo));
    }

    memo.put(current, total); // Cache save
    return total;
}
```

### B. Combinatoria de Segmentos (Parte B)
"Matemática pura. El número de caminos de X a Z pasando por Y es `Caminos(X->Y) * Caminos(Y->Z)`."

```java
// Camino 1: Start -> WP1 -> WP2 -> End
BigInteger path1 = countPathsBetween(start, wp1, network)
        .multiply(countPathsBetween(wp1, wp2, network))
        .multiply(countPathsBetween(wp2, end, network));
```

---

## 4. Preguntas de Examen

*   **Profesor**: *¿Por qué usas `BigInteger`?*
    *   **Tú**: "Explosión combinatoria. En un grafo pequeño, el número de rutas distintas puede crecer factorialmente. Un `long` se desborda con grafos de apenas 60-70 nodos si están muy conectados."
*   **Profesor**: *¿Qué pasa si hay ciclos en el grafo?*
    *   **Tú**: "Mi solución asume que es un **DAG** (Acíclico). Si hubiera ciclos, la recursión entraría en `StackOverflowError`. Tendría que añadir un `Set<String> visited` en la recursión para detectar bucles, y decidir si un bucle cuenta como infinitos caminos o no."

## 5. Resumen
"Reduje un problema de 'navegación compleja con restricciones' a una serie de problemas simples de 'conteo de caminos', unidos mediante multiplicación combinatoria y optimizados con programación dinámica."

---

## 6. Estructura de Archivos (Model-Parser-Service)

### Model
*   `Network.java`: Grafo (Map<String, List<String>>).

### Parser
*   `NetworkParser.java`: Parsea "Node -> Neighbors".

### Service
*   `ReactorService.java`: DFS con Memoización para contar rutas.

---
---

# Cheatsheet / Guía de Defensa - Día 12: Granja de Árboles (Exact Cover & Backtracking)

> **Rol**: Estudiante de máster explicando su algoritmo de optimización al Profesor.
> **Tono**: Sofisticado, centrando la discusión en la NP-Completitud y las optimizaciones de bajo nivel (BitSets).

---

## 1. ¿De qué va el problema?
"Profesor, es un problema clásico de **Tiling** o **Packing** (Teselado).
Tenemos un grid rectangular y varias piezas de Tetris (poliominós).
El objetivo es encajarlas todas sin que se solapen y sin dejar huecos (o dejándolos si es Loose Packing)."
*   **Complejidad**: Es un problema NP-Completo. Probar todas las combinaciones a mano es inviable.

---

## 2. Decisiones de Arquitectura

### A. Estrategia Dinámica (`PackingService`)
"No uso un solo algoritmo. Primero analizo la entrada:
1.  Calculo el área total de las piezas.
2.  **Caso Perfect Match** (`AreaPiezas == AreaGrid`): Uso `ExactCoverSolver`. Más rápido porque cada celda *debe* estar ocupada.
3.  **Caso Loose Packing** (`AreaPiezas < AreaGrid`): Uso `LoosePackingSolver`. Permite huecos vacíos."

### B. Representación del Tablero (`BitSet`)
"Aquí está la optimización clave.
En lugar de una matriz `int[][]` o `boolean[][]`, aplano el grid a 1 dimensión y uso un **`BitSet`**.
*   *Ventaja*: Verificar colisiones (choque de piezas) se convierte en una operación lógica **bitwise AND** (`board & mask`). Es órdenes de magnitud más rápido que bucles anidados."

---

## 3. El Algoritmo: Backtracking con Poda (Heurística Exact Cover)

"Implementé una variante del **Algorithm X** de Knuth."

1.  **Selección de Celda (Heurística)**: "No coloco piezas al azar. Busco siempre la **primera celda vacía** (`firstEmpty = board.nextClearBit(0)`). Si no la lleno AHORA, no podré llenarla después (porque el algoritmo avanza). Esto poda el árbol de búsqueda masivamente."
2.  **Pre-cálculo de Variantes**: "Antes de empezar, genero todas las rotaciones y traslaciones posibles de cada pieza (`PlacementGenerator`)."
3.  **Recursión**:
    *   Intento poner una pieza candidata en `firstEmpty`.
    *   Si encaja (`!intersects`), marco los bits (`board.or`).
    *   Llamo recursivamente.
    *   Si falla, desmarco (`board.xor`) <- **Backtracking**.

---

## 4. Puntos Clave del Código (Snippet para señalar)

### A. Selección de Candidatos Optimizada
"Guardo las piezas pre-calculadas en un mapa indexado por la celda donde empiezan (`mapByStartBit`). Así, cuando necesito llenar la celda 0, solo iter sobre las piezas que *pueden* empezar en la 0."

```java
// ExactCoverSolver.java
int firstEmpty = board.nextClearBit(0); // Encuentra el hueco más urgente

List<Placement> candidates = mapByStartBit[firstEmpty]; // Solo piezas relevantes

for (Placement p : candidates) {
    if (inventory[p.pieceId()] > 0) { // ¿Me quedan de estas piezas?
        // Operación Bitwise ultra-rápida
        if (!board.intersects(p.mask())) {
            board.or(p.mask());     // Place
            // ... recurse ...
            board.xor(p.mask());    // Backtrack (Remove)
        }
    }
}
```

### B. Paralelismo (`PackingService`)
"Como cada puzzle es independiente, uso Streams paralelos para aprovechar todos los núcleos de la CPU."

```java
return input.tasks().parallelStream() // <- Multithreading gratis
        .filter(task -> solveTask(...))
        .count();
```

---

## 5. Preguntas de Examen

*   **Profesor**: *¿Por qué no usar Dancing Links (DLX) puro?*
    *   **Tú**: "DLX (matrices enlazadas cuádruples) es excelente pero muy complejo de implementar y debugar en el tiempo limitado. Mi enfoque de `BitSet` con la heurística de 'rellenar el primer hueco' consigue un rendimiento similar para grids de este tamaño (64 bits) con un código mucho más mantenible."
*   **Profesor**: *¿Qué pasa si una pieza tiene agujeros?*
    *   **Tú**: "El `BitSet` lo maneja nativamente. La máscara de bits simplemente tendrá ceros en medio. La lógica de colisión no cambia."

## 6. Resumen
"Implementé un solver de cobertura exacta altamente optimizado a nivel de bits, utilizando heurísticas de orden de búsqueda para reducir el espacio de estados y paralelismo para maximizar el throughput."

---

## 7. Estructura de Archivos (Model-Parser-Service)

### Model
*   `Shape.java`: Pieza y sus rotaciones.
*   `Placement.java`: Bitmask de la pieza.

### Parser
*   `PuzzleParser.java`: Carga el puzzle.

### Service
*   `PackingService.java`: Dispatcher de estrategias.
*   `ExactCoverSolver.java`: Backtracking + BitSets.
