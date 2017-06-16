package ua.kpi.randgen;

import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ua.kpi.randgen.BitwiseNumber.parse;

public class FragmentTest {
    @Test
    public void testFragmentSet() throws Exception {
        Set<Fragment> fragmentSet = new LinkedHashSet<>();
        Fragment fragment = Fragment.of(parse("0100"), parse("0011"));

        fragmentSet.add(Fragment.of(parse("0111"), parse("1011")));
        fragmentSet.add(Fragment.of(parse("0110"), parse("0010")));
        fragmentSet.add(Fragment.of(parse("0100"), parse("0011")));
        fragmentSet.add(Fragment.of(parse("0101"), parse("0011")));

        assertThat(fragmentSet.contains(fragment), is(true));
    }
}