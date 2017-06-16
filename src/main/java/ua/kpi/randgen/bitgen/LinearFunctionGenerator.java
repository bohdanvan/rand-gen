package ua.kpi.randgen.bitgen;

import ua.kpi.randgen.BitwiseNumber;

import java.util.Iterator;

import static ua.kpi.randgen.utils.NFSRUtils.calcFunctionLength;

/**
 * Генератор лінійних функцій.
 *
 * @author bvanchuhov
 */
public class LinearFunctionGenerator implements BitwiseNumberGenerator, Iterable<BitwiseNumber> {

    private int registerLength;
    private Iterator<BitwiseNumber> iterator;

    public LinearFunctionGenerator(int registerLength) {
        this.registerLength = registerLength;
        this.iterator = new LinearFunctionIterator(registerLength);
    }

    @Override
    public BitwiseNumber generate() {
        return iterator.next();
    }

    @Override
    public Iterator<BitwiseNumber> iterator() {
        return new LinearFunctionIterator(registerLength);
    }

    private static class LinearFunctionIterator implements Iterator<BitwiseNumber> {
        private int registerLength;
        private int functionLength;

        private BitwiseNumber coefs;

        public LinearFunctionIterator(int registerLength) {
            this.registerLength = registerLength;
            this.functionLength = calcFunctionLength(registerLength);

            this.coefs = BitwiseNumber.of(registerLength + 1);
        }

        @Override
        public boolean hasNext() {
            return !coefs.hasAllBits();
        }

        @Override
        public BitwiseNumber next() {
            BitwiseNumber function = generateFunction(coefs);
            coefs = coefs.increment();

            return function;
        }

        private BitwiseNumber generateFunction(BitwiseNumber coefs) {
            BitwiseNumber function = BitwiseNumber.of(functionLength);
            BitwiseNumber register = BitwiseNumber.of(registerLength);

            while (!register.hasAllBits()) {
                int bitValue = coefs.and(register).bitsXor();
                bitValue ^= coefs.getBit(coefs.length()-1);
                int pos = register.intValue();
                function = function.setBit(bitValue, pos);

                register = register.increment();
            }

            return function;
        }
    }
}
