package ua.kpi.randgen;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.math.BigInteger.ZERO;
import static ua.kpi.randgen.utils.BitwiseUtils.pow2;
import static ua.kpi.randgen.utils.NFSRUtils.calcFunctionLength;
import static ua.kpi.randgen.utils.NFSRUtils.isFullPeriodFunction;

/**
 * Created on 10.05.2015
 *
 * @author bvanchuhov
 */
public class NFSRGeneratorController {

    public static void main(String[] args) {
//        List<Pair<Integer, Integer>> functionsQuantity = countFunctions(1, 5);
//        System.out.println(functionsQuantity);

//        System.out.println(countFunctions(4));

//        printGeneratedValues(2);
        printFunctions(3);

        printGeneratedValues(3, BitwiseNumber.parse("01001011"));

        System.out.println(isFullPeriodFunction(4, BitwiseNumber.parse("0111000010001111")));
    }

    public static List<Pair<Integer, Long>> countFunctions(int fromRegisterLength, int toRegisterLength) {
        List<Pair<Integer, Long>> functionQuantitiesCounter = new ArrayList<>();

        for (int registerLength = fromRegisterLength; registerLength <= toRegisterLength; registerLength++) {
            long functionsQuantity = countFunctions(registerLength);
            functionQuantitiesCounter.add(Pair.of(registerLength, functionsQuantity));
        }

        return functionQuantitiesCounter;
    }

    public static long countFunctions(int registerLength) {
        int functionLength = calcFunctionLength(registerLength);
        BitwiseNumber function = BitwiseNumber.of(functionLength);

        long counter = 0;
        do {
            if (isFullPeriodFunction(registerLength, function)) {
                counter++;
            }
            function = function.increment();

            if (function.mod(BigInteger.valueOf(1_000_000)).equals(ZERO)) { //--------------------------------------------------
                System.out.printf("%s\t->\t%d\n", function.getValue(), counter);
            }
        } while (!function.hasAllZero());

        return counter;
    }

    public static List<BitwiseNumber> findFunctions(int registerLength) {
        List<BitwiseNumber> functions = new ArrayList<>();
        int functionLength = calcFunctionLength(registerLength);
        BitwiseNumber function = BitwiseNumber.of(functionLength);

        do {
            if (isFullPeriodFunction(registerLength, function)) {
                functions.add(function);
            }
            function = function.increment();

            if (function.getBit(10) == 1) {
                System.out.println("good");
            }
        } while (!function.hasAllZero());

        System.out.println(functions);

        return functions;
    }



    //--------------------------------------------------

    public static void printFunctions(int registerLength) {
        List<BitwiseNumber> functions = findFunctions(registerLength);
        int counter = 0;
        for (BitwiseNumber function : functions) {
            System.out.printf("%d)\t%20s\t%d\n", counter++, function, function.zeroCount());
        }

//        Map<Integer, Integer> zeroQuantities = countZeroQuantities(functions);
//        System.out.println("Zero quantities:");
//        int functionLength = functions.get(0).length();
//        for (Map.Entry<Integer, Integer> integerIntegerEntry : zeroQuantities.entrySet()) {
//            int zeroCount = integerIntegerEntry.getKey();
//            int bitCount = functionLength - zeroCount;
//
//            System.out.printf("%d\t-\t'0'=%d,\t'1'=%d\n",
//                    integerIntegerEntry.getValue(),
//                    zeroCount,
//                    bitCount);
//        }
    }

    private static Map<Integer, Integer> countZeroQuantities(List<BitwiseNumber> bitwiseNumbers) {
        Map<Integer, Integer> zeroQuantitiesMap = new TreeMap<>();

        for (BitwiseNumber bitwiseNumber : bitwiseNumbers) {
            int zeroQuantity = bitwiseNumber.zeroCount();
            if (zeroQuantitiesMap.containsKey(zeroQuantity)) {
                zeroQuantitiesMap.put(zeroQuantity, zeroQuantitiesMap.get(zeroQuantity) + 1);
            } else {
                zeroQuantitiesMap.put(zeroQuantity, 1);
            }
        }

        return zeroQuantitiesMap;
    }

    private static int countEvenZeros(List<BitwiseNumber> bitwiseNumbers) {
        return (int) bitwiseNumbers.stream()
                .map(s -> s.zeroCount() % 2 == 0)
                .count();
    }

    public static void printGeneratedValues(int registerLength) {
        int functionLength = calcFunctionLength(registerLength);
        BitwiseNumber function = BitwiseNumber.of(functionLength);

        do {
            printGeneratedValues(registerLength, function);

            function = function.add(1);
        } while (!function.hasAllZero());
    }

    public static void printGeneratedValues(int registerLength, BitwiseNumber function) {
        NFSRGenerator generator = new NFSRGenerator(registerLength, function);
        generator.setSeed(0);

        System.out.printf("----- %s -----\n", function);
        int valuesQuantity = pow2(registerLength);
        for (int i = 0; i < valuesQuantity; i++) {
            System.out.println(generator.generate());
        }
    }
}
