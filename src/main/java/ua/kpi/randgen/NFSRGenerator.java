package ua.kpi.randgen;

import ua.kpi.randgen.bitgen.BitGenerator;
import ua.kpi.randgen.bitgen.BitwiseNumberGenerator;

import java.math.BigInteger;

import static ua.kpi.randgen.utils.BitwiseUtils.pow2;

/**
 * Реалізація регістру зсуву з нелінійним зворотнім зв’язком (NFSR).
 *
 * @author bvanchuhov
 */
public class NFSRGenerator implements BitwiseNumberGenerator, BitGenerator {

    private final int registerLength;
    private final BitwiseNumber function;
    private BitwiseNumber shiftRegister;

    /**
     * Конструктор.
     * @param registerLength довжина регістру.
     * @param function нелінійна функція оберненого зв’язку.
     */
    public NFSRGenerator(int registerLength, BitwiseNumber function) {
        this.registerLength = registerLength;
        this.function = function;
        this.shiftRegister = BitwiseNumber.of(registerLength);
    }

    public void setSeed(BigInteger seed) {
        shiftRegister = BitwiseNumber.of(registerLength, seed);
    }

    public void setSeed(int seed) {
        setSeed(BigInteger.valueOf(seed));
    }

    public void setSeed(BitwiseNumber seed) {
        setSeed(seed.getValue());
    }

    /**
     * Генерує двійковий рядок.
     * @return згенерований двійковий рядок.
     */
    @Override
    public BitwiseNumber generate() {
        generateBit();

        return shiftRegister;
    }

    /**
     * Генерує один біт.
     * @return згенерований біт.
     */
    @Override
    public int generateBit() {
        int generatedBit = generateFunctionBit();

        shiftRegister = shiftRegister.shiftLeft(1)
                .setBit(generatedBit, 0);

        return shiftRegister.getBit(0);
    }

    private int generateFunctionBit() {
        int functionIndex = shiftRegister.intValue();
        return function.getBit(functionIndex);
    }

    /**
     * Генерує двійковий рядок заданої довжини.
     * @param numberLength довжина двійкового рядка.
     * @return згенерований двійковий рядок.
     */
    public BitwiseNumber generateBitwiseNumber(int numberLength) {
        BitwiseNumber bitwiseNumber = BitwiseNumber.of(numberLength);

        for (int i = 0; i < numberLength; i++) {
            int generatedBit = generateBit();
            bitwiseNumber = bitwiseNumber.setBit(generatedBit, i);
        }

        return bitwiseNumber;
    }

    /**
     * Повертає період повторення для заданого генератора.
     * @return період повторення генератора.
     */
    public int getPeriod() {
        return pow2(registerLength);
    }

    /**
     * Повертає поточний стан регістру зсуву.
     * @return поточний стан регістру зсуву.
     */
    public BitwiseNumber getRegister() {
        return shiftRegister;
    }
}
