package ua.kpi;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created on 10.05.2015
 *
 * @author bvanchuhov
 */
public class BigIntegerTest {
    @Test
    public void testEquals() throws Exception {
        assertThat(ONE.equals(ONE), is(true));
    }
}
