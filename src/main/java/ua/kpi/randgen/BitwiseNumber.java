package ua.kpi.randgen;

import ua.kpi.randgen.utils.BitwiseUtils;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static ua.kpi.randgen.utils.BitwiseUtils.*;

/**
 * Двійковий рядок.
 *
 * @author bvanchuhov
 */
public final class BitwiseNumber implements Comparable<BitwiseNumber> {

    private final int length;
    private final BigInteger valuesQuantity;
    private final BigInteger value;

    public static BitwiseNumber valueOf(BigInteger value) {
        return new BitwiseNumber(value.bitLength(), value);
    }

    public static BitwiseNumber valueOf(int value) {
        return valueOf(BigInteger.valueOf(value));
    }

    public static BitwiseNumber parse(String string) {
        int length = string.length();
        BigInteger value = ZERO;

        for (int strIndex = length - 1, valueIndex = 0; strIndex >= 0; strIndex--, valueIndex++) {
            int bit = string.charAt(strIndex) == '1' ? 1 : 0;
            value = BitwiseUtils.setBit(value, bit, valueIndex);
        }

        return new BitwiseNumber(length, value);
    }

    public static BitwiseNumber of(int length) {
        return new BitwiseNumber(length);
    }

    public static BitwiseNumber of(int length, BigInteger value) {
        return new BitwiseNumber(length, value);
    }

    public static BitwiseNumber of(int length, int value) {
        return new BitwiseNumber(length, BigInteger.valueOf(value));
    }

    public static BitwiseNumber oneBit(int length, int bitPos) {
        BigInteger value = ZERO.setBit(bitPos);
        return new BitwiseNumber(length, value);
    }

    public static BitwiseNumber oneZero(int length, int zeroPos) {
        return new BitwiseNumber(length, ZERO.setBit(zeroPos).not());
    }

    public static BitwiseNumber allBits(int length) {
        return new BitwiseNumber(length, ZERO.setBit(length).subtract(ONE));
    }

    private BitwiseNumber(int length, BigInteger value, BigInteger valuesQuantity) {
        this.length = length;
        this.valuesQuantity = valuesQuantity;
        this.value = value;
    }

    private BitwiseNumber(int length) {
        this(length, ZERO, bigPow2(length));
    }

    private BitwiseNumber(int length, BigInteger value) {
        this(length, correctValue(value, bigPow2(length)), bigPow2(length));
    }

    private BitwiseNumber(BitwiseNumber bitwiseNumber) {
        this(bitwiseNumber.length, bitwiseNumber.value, bitwiseNumber.valuesQuantity);
    }

    private static BigInteger correctValue(BigInteger value, BigInteger valuesQuantity) {
        return value.mod(valuesQuantity);
    }

    public int length() {
        return length;
    }

    public BigInteger getValue() {
        return value;
    }

    public String toString() {
        return toBinaryString(value, length);
    }

    public String toReverseString() {
        return toReversedBinaryString(value, length);
    }

    public int getBit(int pos) {
        return BitwiseUtils.getBit(value, pos);
    }

    public BitwiseNumber setBit(int bitValue, int pos) {
        return new BitwiseNumber(length, BitwiseUtils.setBit(value, bitValue, pos));
    }

    public BitwiseNumber setBit(int pos) {
        return new BitwiseNumber(length, value.setBit(pos));
    }

    public BitwiseNumber clearBit(int pos) {
        return new BitwiseNumber(length, value.clearBit(pos));
    }

    public BitwiseNumber setLastBit() {
        return setBit(1, length - 1);
    }

    public BitwiseNumber shiftLeft(int shiftPos) {
        return new BitwiseNumber(length, value.shiftLeft(shiftPos).mod(valuesQuantity));
    }

    public BitwiseNumber changeBit(int pos) {
        if (pos >= length) return this;

        return new BitwiseNumber(length, BitwiseUtils.changeBit(value, pos));
    }

    public BitwiseNumber xor(int x) {
        return new BitwiseNumber(length, value.xor(BigInteger.valueOf(x)));
    }

    public BitwiseNumber xor(BitwiseNumber other) {
        int newLength = Math.max(this.length, other.length);
        BigInteger newValue = value.xor(other.value);
        return new BitwiseNumber(newLength, newValue);
    }

    public BitwiseNumber and(BitwiseNumber other) {
        int newLength = Math.max(this.length, other.length);
        BigInteger newValue = value.and(other.value);
        return new BitwiseNumber(newLength, newValue);
    }

    public int bitsXor() {
        return bitCount() % 2 == 0 ? 0 : 1;
    }

    public int intValue() {
        return value.intValue();
    }

    public int bitCount() {
        return value.bitCount();
    }

    public int zeroCount() {
        return length() - value.bitCount();
    }

    public boolean hasAllZero() {
        return value.bitCount() == 0;
    }

    public boolean hasAllBits() {
        return zeroCount() == 0;
    }

    public BitwiseNumber mod(BitwiseNumber module) {
        return mod(module.value);
    }

    public BitwiseNumber mod(BigInteger module) {
        BigInteger newValue = value.mod(module);
        return new BitwiseNumber(length, newValue);
    }

    public BitwiseNumber add(int i) {
        BigInteger newValue = value.add(BigInteger.valueOf(i));
        return new BitwiseNumber(length, newValue);
    }

    public BitwiseNumber increment() {
        return new BitwiseNumber(length, value.add(ONE));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BitwiseNumber)) return false;

        BitwiseNumber that = (BitwiseNumber) o;

        if (length != that.length) return false;
        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        int result = length;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(BitwiseNumber o) {
        return value.compareTo(o.value);
    }
}
