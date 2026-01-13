package software.aoc.day02.a.service;

import java.math.BigInteger;

public class IdValidator {

    public boolean isInvalidId(BigInteger id) {
        String idStr = id.toString();
        int length = idStr.length();

        if (length % 2 != 0) {
            return false;
        }

        int mid = length / 2;
        String firstHalf = idStr.substring(0, mid);
        String secondHalf = idStr.substring(mid);

        return firstHalf.equals(secondHalf);
    }
}
