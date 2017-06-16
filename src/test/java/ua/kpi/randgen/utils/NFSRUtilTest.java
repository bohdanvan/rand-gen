package ua.kpi.randgen.utils;

import org.junit.Test;
import ua.kpi.randgen.BitwiseNumber;
import ua.kpi.randgen.NFSRFunctionGenerator;

import static ua.kpi.randgen.utils.NFSRUtils.nonlinearity;

public class NFSRUtilTest {
    public static final int MEASUREMENTS_QUANTITY = 1;
    public static final int MIN_REGISTER_LENGTH = 13;
    public static final int MAX_REGISTER_LENGTH = 15;

    @Test
    public void testNonlinearity() throws Exception {
        for (int registerLength = MIN_REGISTER_LENGTH; registerLength <= MAX_REGISTER_LENGTH; registerLength++) {
            int sum = 0;
            for (int i = 0; i < MEASUREMENTS_QUANTITY; i++) {
                NFSRFunctionGenerator functionGenerator = new NFSRFunctionGenerator(registerLength);
                BitwiseNumber function = functionGenerator.generate();

                sum += nonlinearity(registerLength, function);
            }

            int avg = sum / MEASUREMENTS_QUANTITY;
            System.out.printf("%d -\t%d\n", registerLength, avg);
        }
    }
}