package ua.kpi.randgen;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.kpi.randgen.NFSRGeneratorController.countFunctions;
import static ua.kpi.randgen.utils.BitwiseUtils.generateNumbers;
import static ua.kpi.randgen.utils.BitwiseUtils.pow2;

public class NFSRGeneratorControllerTest {
    @Test
    public void testFunctionPeriodical() throws Exception {
        int registerLength = 4;
        BitwiseNumber function = BitwiseNumber.parse("11111101");
        NFSRGenerator random = new NFSRGenerator(registerLength, function);
        random.setSeed(0);

        List<BitwiseNumber> numbers = generateNumbers(random, pow2(registerLength));
        numbers.stream().forEach(System.out::println);

        assertThat(isAllUnique(numbers), is(true));
    }

    private <T> boolean isAllUnique(List<T> list) {
        Set<T> set = new HashSet<>(list);

        return list.size() == set.size();
    }

    @Test
    public void testCountFunctions_2() throws Exception {
        assertThat(countFunctions(2), is(1L));
    }

    @Test
    public void testCountFunctions_3() throws Exception {
        assertThat(countFunctions(3), is(2L));
    }

    @Test
    public void testCountFunctions_4() throws Exception {
        assertThat(countFunctions(4), is(16L));
    }

    @Test
    public void testCountFunctions_5() throws Exception {
        assertThat(countFunctions(5), is(2048L));
    }
}