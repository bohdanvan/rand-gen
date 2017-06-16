package ua.kpi.randgen;

import static ua.kpi.randgen.utils.NFSRUtils.isFullPeriodFunction;

/**
 * @author bvanchuhov
 */
public class Runner {

    public static void main(String[] args) {
        int registerLength = 12;
        for (int i = 0; i < 10000; i++) {
            NFSRFunctionGenerator functionGenerator = new NFSRFunctionGenerator(registerLength);
            BitwiseNumber function;

            function = functionGenerator.generate();

            System.out.print(i + ")\t");
            printFunctionInfo(registerLength, function);
        }
    }

    private static void printFunctionInfo(int registerLength, BitwiseNumber function) {
        System.out.println(function + " - " + isFullPeriodFunction(registerLength, function));
    }
}
