package ua.kpi.randgen.utils;

import ua.kpi.randgen.BitwiseNumber;
import ua.kpi.randgen.NFSRGenerator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Утилітний клас для роботи з двійковими послідовностями.
 *
 * @author bvanchuhov
 */
public final class BitwiseUtils {

    private BitwiseUtils() {}

    public static BigInteger setBit(BigInteger number, int bitValue, int pos) {
        return bitValue == 0 ? number.clearBit(pos) : number.setBit(pos);
    }

    public static int getBit(BigInteger number, int pos) {
        return number.testBit(pos) ? 1 : 0;
    }

    public static BigInteger changeBit(BigInteger number, int pos) {
        return number.testBit(pos) ? number.clearBit(pos) : number.setBit(pos);
    }

    public static String toBinaryString(BigInteger number) {
        if (number.bitLength() <= 63)
            return Long.toBinaryString(number.longValue());

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = number.bitLength() - 1; i >= 0; i--) {
            int bit = number.testBit(i) ? 1 : 0;
            stringBuilder.append(bit);
        }

        return stringBuilder.toString();
    }

    public static String toBinaryString(BigInteger number, int stringLength) {
        String bitwiseString = toBinaryString(number);
        int lengthDifference = stringLength - bitwiseString.length();
        if (lengthDifference <= 0)
            return bitwiseString;

        StringBuilder stringBuilder = new StringBuilder();
        while (lengthDifference-- > 0) {
            stringBuilder.append("0");
        }
        stringBuilder.append(bitwiseString);

        return stringBuilder.toString();
    }

    public static String toReversedBinaryString(BigInteger number) {
        return new StringBuilder(toBinaryString(number))
                .reverse()
                .toString();
    }

    public static String toReversedBinaryString(BigInteger number, int stringLength) {
        return new StringBuilder(toBinaryString(number, stringLength))
                .reverse()
                .toString();
    }

    public static List<BitwiseNumber> generateNumbers(NFSRGenerator generator, int numbersQuantity) {
        List<BitwiseNumber> randNumbers = new ArrayList<>(numbersQuantity);

        for (int i = 0; i < numbersQuantity; i++) {
            randNumbers.add(generator.generate());
        }

        return randNumbers;
    }

    public static List<String> generateStrings(NFSRGenerator generator, int stringsQuantity) {
        List<BitwiseNumber> randNumbers = generateNumbers(generator, stringsQuantity);

        return randNumbers.stream()
                .map(n -> n.toString())
                .collect(Collectors.toList());
    }

    public static int pow2(int exponent) {
        return 1 << exponent;
    }

    public static BigInteger bigPow2(int exponent) {
        return BigInteger.ONE.shiftLeft(exponent);
    }

    public static int zerosCount(BigInteger number) {
        return number.bitLength() - number.bitCount();
    }
}
