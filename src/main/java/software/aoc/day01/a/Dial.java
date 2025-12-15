package software.aoc.day01.a;

public class Dial {
    private int currentPosition;

    public Dial() {
        // Según el enunciado, el dial empieza apuntando al 50
        this.currentPosition = 50;
    }

    public void rotate(Order order) {
        if (order.getDirection() == 'R') {
            // Rotación a la derecha (suma)
            currentPosition = (currentPosition + order.getAmount()) % 100;
        } else if (order.getDirection() == 'L') {
            // Rotación a la izquierda (resta).
            int result = (currentPosition - order.getAmount()) % 100;
            if (result < 0) {
                result += 100;
            }
            currentPosition = result;
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}