package ua.kpi.randgen;

import org.junit.Test;
import ua.kpi.randgen.bitgen.ListGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.kpi.randgen.utils.NFSRUtils.isFullPeriodFunction;

public class NFSRFunctionGeneratorTest {
    @Test
    public void testGenerateFunction() throws Exception {
        int registerLength = 4;

        for (int i = 0; i < 1000; i++) {
            NFSRFunctionGenerator functionGenerator = new NFSRFunctionGenerator(registerLength);
            BitwiseNumber function = functionGenerator.generate();

            System.out.println(i + " " + function);

            assertThat(isFullPeriodFunction(registerLength, function), is(true));
        }
    }

    @Test
    public void testGenerateFunction_fromExample() throws Exception {
        int registerLength = 4;
        NFSRFunctionGenerator functionGenerator = new NFSRFunctionGenerator(registerLength);
        functionGenerator.setFragmentIndexGenerator(new ListGenerator(1, 2, 0, 1, 3, 0));
        functionGenerator.setTransitionGenerator(new ListGenerator(0, 1, 0, 1));

        BitwiseNumber function = functionGenerator.generate();

        assertThat(function.toString(), is("0110001010011101"));
    }
}