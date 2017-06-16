package ua.kpi.randgen.bitgen;

import org.junit.Test;
import ua.kpi.randgen.BitwiseNumber;

public class LinearFunctionGeneratorTest {
    @Test
    public void testGenerateLinearFunction() throws Exception {
        int registerLength = 3;
        LinearFunctionGenerator functionGenerator = new LinearFunctionGenerator(registerLength);

        for (BitwiseNumber function : functionGenerator) {
            System.out.println(function);
        }
    }
}