package ua.kpi.randgen;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BitwiseNumberTest {
    @Test
    public void testEquals() throws Exception {
        assertThat(BitwiseNumber.valueOf(10).equals(10), is(true));
    }

    @Test
    public void testOneBitNumber_1() throws Exception {
        assertThat(BitwiseNumber.oneBit(5, 2).toString(), is("00100"));
    }

    @Test
    public void testOneBitNumber_2() throws Exception {
        assertThat(BitwiseNumber.oneBit(5, 7).toString(), is("00000"));
    }

    @Test
    public void testOneZeroNumber() throws Exception {
        assertThat(BitwiseNumber.oneZero(5, 2).toString(), is("11011"));
    }

    @Test
    public void testAllBits() throws Exception {
        assertThat(BitwiseNumber.allBits(5).toString(), is("11111"));
    }

    @Test
    public void testShiftLeft() throws Exception {
        BitwiseNumber number = BitwiseNumber.parse("1110");
        BitwiseNumber afterShift = number.shiftLeft(1);

        assertThat(afterShift.toString(), is("1100"));
    }
}