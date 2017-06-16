package ua.kpi.randgen.utils;

import ua.kpi.randgen.BitwiseNumber;
import ua.kpi.randgen.NFSRGenerator;
import ua.kpi.randgen.bitgen.LinearFunctionGenerator;

import java.math.BigInteger;

import static java.math.BigInteger.ZERO;
import static ua.kpi.randgen.utils.BitwiseUtils.pow2;

/**
 * @author bvanchuhov
 */
public final class NFSRUtils {

    private NFSRUtils() {}

    public static int calcFunctionLength(int registerLength) {
        return pow2(registerLength);
    }

    public static BitwiseNumber conjugate(BitwiseNumber number) {
        return number.changeBit(number.length() - 1);
    }

    public static boolean isFullPeriodFunction(int registerLength, BitwiseNumber function) {
        int valuesQuantity = pow2(registerLength);
        BigInteger generatedValuesBitmap = ZERO;

        NFSRGenerator generator = new NFSRGenerator(registerLength, function);
        generator.setSeed(0);
        for (int i = 0; i < valuesQuantity; i++) {
            BitwiseNumber generatedValue = generator.generate();
            int generatedInt = generatedValue.intValue();

            if (generatedValuesBitmap.testBit(generatedInt))
                return false;

            generatedValuesBitmap = generatedValuesBitmap.setBit(generatedInt);
        }
        return true;
    }

    public static int hammingCode(BitwiseNumber n) {
        return n.bitCount();
    }

    public static int hammingDistance(BitwiseNumber n1, BitwiseNumber n2) {
        return n1.xor(n2).bitCount();
    }

    public static int nonlinearity(int registerLength, BitwiseNumber function) {
        int minHammingDistance = Integer.MAX_VALUE;

        LinearFunctionGenerator linearFunctionGenerator = new LinearFunctionGenerator(registerLength);
        for (BitwiseNumber linearFunction : linearFunctionGenerator) {
            int hammingDistance = hammingDistance(function, linearFunction);
            if (minHammingDistance > hammingDistance) {
                minHammingDistance = hammingDistance;
            }
        }

        return minHammingDistance;
    }
}
