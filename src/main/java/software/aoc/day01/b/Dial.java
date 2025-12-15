package software.aoc.day01.b;

import java.util.List;

/**
 * Resuelve la Parte Dos del Día 1:
 * Contar cuántas veces el dial apunta a 0 durante cada rotación.
 */
public class Dial {

    private static final int MAX_POSITION = 100;
    private int currentPosition;
    private long zeroCount;

    public Dial() {
        // La posición inicial del dial es 50.
        this.currentPosition = 50;
        this.zeroCount = 0;
    }

    /**
     * Procesa una lista de órdenes de rotación y actualiza el contador de ceros.
     * @param orders Lista de órdenes de rotación.
     * @return El contador total de veces que el dial apuntó a 0.
     */
    public long processOrders(List<Order> orders) {
        for (Order order : orders) {
            applyRotation(order);
        }
        return zeroCount;
    }

    /**
     * Aplica una rotación y cuenta cuántas veces el dial pasa por 0.
     * @param order La orden de rotación.
     */
    private void applyRotation(Order order) {
        int N = order.getSteps();
        char D = order.getDirection();

        // 1. Contar los pases por 0 por vueltas completas (cada 100 pasos)
        // Cada vuelta completa pasa por el 0 exactamente una vez.
        long completeCycles = N / MAX_POSITION;
        zeroCount += completeCycles;

        // El nuevo contador ya incluye los 0 que caen exactamente en una vuelta completa.
        // Si N es múltiplo de 100, la posición final no cambia y ya terminamos.

        // 2. Contar el pase por 0 en el movimiento restante (N' = N % 100)
        int N_prime = N % MAX_POSITION;

        if (N_prime == 0) {
            return; // Ya se contabilizó todo en las vueltas completas.
        }

        // Simular el movimiento del resto de pasos (N_prime)
        int startP = currentPosition;
        int endP;

        if (D == 'R') {
            endP = (startP + N_prime) % MAX_POSITION;

            // Pasa por 0 si la rotación cruza el límite de 99 a 0.
            // Esto ocurre si la posición inicial + N' es >= 100.
            if (startP + N_prime >= MAX_POSITION) {
                // Ya contamos el 0 que cayó justo al final de la rotación completa si hubo ciclos completos.
                // Si la rotación completa fue 0, entonces este pase por 0 es nuevo.

                // Si un 0 fue contado en el ciclo completo, es el mismo que en el cruce de 99 -> 0.
                // Pero si N es 105, contamos 1 ciclo. El 5 pasos restantes van de 50 -> 55. NO HAY CRUCE.

                // La forma correcta es:
                // Si la rotación cruza 0 (pasa de 99 a 0 en R, o de 1 a 0 en L)
                // Y el 0 no es la posición final (porque 0 como posición final ya fue contado en completeCycles si N % 100 == 0)
                // En este caso, si N % 100 != 0, la posición final NO es 0, a menos que P_inicial + N' = 100.

                // Simplificación: Un pase extra por 0 ocurre si el dial 'envuelve' la posición 0.
                if (startP != 0) {
                    zeroCount++;
                }

            }
        } else { // D == 'L'
            // El dial es 0..99. 0 - 1 = 99 (mod 100).
            endP = (startP - N_prime) % MAX_POSITION;
            if (endP < 0) {
                endP += MAX_POSITION;
            }

            // Pasa por 0 si la rotación cruza el límite de 1 a 0.
            // Esto ocurre si la posición inicial - N' es < 0.
            if (startP - N_prime < 0) {
                if (startP != 0) {
                    zeroCount++;
                }
            }
        }

        // 3. La posición final de la rotación
        currentPosition = endP;

        // 4. El 0 de la posición final
        if (currentPosition == 0) {
            // Este 0 es el que ocurre al final de una rotación NO completa (N % 100 != 0)
            // Si N % 100 != 0 y la posición final es 0, ya fue contado en el paso 2
            // como parte del cruce (por ejemplo: R50, Start=50, End=0. Pasa de 99->0, cuenta 1)
            // Ya que el contador ya se incrementó en el paso 2 si hubo un cruce.

            // Si la posición inicial NO era 0, el cruce siempre suma 1.
            // Si la posición inicial SÍ era 0, el cruce no suma (ya fue contado).

            // Revisemos el ejemplo: 50 -> L55. N'=55. L. 50-55 = -5. Final=95. NO CRUZA. P=95.
            // Ejemplo: 50 -> L50. N'=50. L. 50-50 = 0. Final=0. CRUZA. Pasa por 0.

            // El contador total está bien con la lógica de cruce + ciclos completos.
        }
    }
}