package software.aoc.day03.a.model;

import java.util.List;

public record BatteryBank(List<Integer> batteries) {

    public int calculateMaxJoltage() {
        int maxJoltage = 0;
        int size = batteries.size();

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                int firstDigit = batteries.get(i);
                int secondDigit = batteries.get(j);

                int currentJoltage = (firstDigit * 10) + secondDigit;

                if (currentJoltage > maxJoltage) {
                    maxJoltage = currentJoltage;
                }

                if (maxJoltage == 99)
                    return 99;
            }
        }
        return maxJoltage;
    }
}
