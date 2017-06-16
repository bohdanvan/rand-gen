package ua.kpi.randgen;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static ua.kpi.randgen.utils.BitwiseUtils.generateStrings;

public class NFSRGeneratorTest {
    @Test
    @Ignore
    public void testGenerateBit() throws Exception {
        int registerLength = 3;
        BitwiseNumber function = BitwiseNumber.parse("01001011");
        NFSRGenerator generator = new NFSRGenerator(registerLength, function);
        generator.setSeed(0);

        List<String> randStrings = generateStrings(generator, 8);

        System.out.println(randStrings);

        assertThat(randStrings, hasItems(
                "001",
                "011",
                "111",
                "110",
                "101",
                "010",
                "100",
                "000"
        ));
    }


}