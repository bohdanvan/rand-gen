package ua.kpi.randgen;

import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.kpi.randgen.utils.BitwiseUtils.*;

public class BitwiseUtilTest {
    @Test
    public void testToBinaryString() throws Exception {
        int n = 1;
        assertThat(toBinaryString(BigInteger.valueOf(n)), is(Integer.toBinaryString(n)));
    }

    @Test
    public void testToReverseBinaryString() throws Exception {
        int n = 2;
        assertThat(toReversedBinaryString(BigInteger.valueOf(n)), is("01"));
    }

    @Test
    public void testZerosCount() throws Exception {
        BigInteger n = BigInteger.valueOf(0b11010110);
        assertThat(zerosCount(n), is(3));
    }
}