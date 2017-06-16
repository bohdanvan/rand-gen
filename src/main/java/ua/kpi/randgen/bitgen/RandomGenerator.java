package ua.kpi.randgen.bitgen;

import java.util.Random;

/**
 * Генератор псеводовипадкових двійкових послідовностей.
 *
 * @author bvanchuhov
 */
public class RandomGenerator implements BitGenerator, IntGenerator {

    private Random random = new Random();

    @Override
    public int generateBit() {
        return random.nextInt(2);
    }

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
