package ua.kpi.randgen;

import ua.kpi.randgen.bitgen.BitGenerator;
import ua.kpi.randgen.bitgen.BitwiseNumberGenerator;
import ua.kpi.randgen.bitgen.IntGenerator;
import ua.kpi.randgen.bitgen.RandomGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static ua.kpi.randgen.BitwiseNumber.*;
import static ua.kpi.randgen.utils.NFSRUtils.*;

/**
 * Реалізація алгоритму генерації нелінійних функцій для NFSR.
 *
 * @author bvanchuhov
 */
public class NFSRFunctionGenerator implements BitwiseNumberGenerator {

    private int registerLength;
    private int functionLength;
    private BitGenerator transitionGenerator = new RandomGenerator();
    private IntGenerator fragmentIndexGenerator = new RandomGenerator();

    //----- Inner fields -----

    private List<Fragment> fragmentList;
    private BitwiseNumber function;

    private Fragment fragmentI;
    private Fragment fragmentJ;
    private BitwiseNumber Xn0;
    private BitwiseNumber Xn1;

    public NFSRFunctionGenerator(int registerLength) {
        this.registerLength = registerLength;
        this.functionLength = calcFunctionLength(registerLength);
    }

    @Override
    public BitwiseNumber generate() {
        initFields();
        initFragmentSetAndFunction();

        int counter = functionLength / 2 - 2;
        while (counter-- > 0) {
            fragmentJ = retrieveRandomFragment();

            BitwiseNumber current = fragmentJ.getFinish();
            BitwiseNumber conjugateCurrent = conjugate(current);
            Fragment currentFragment = Fragment.of(current, conjugateCurrent);

            Optional<Fragment> fragmentIOptional = findFragmentWithFinish(conjugateCurrent);
            fragmentI = getOrCreateFragment(fragmentIOptional,
                    Fragment.of(conjugateCurrent, conjugateCurrent));

            Xn0 = current.shiftLeft(1);
            Xn1 = Xn0.xor(1);

            int transitionType = getTransitionType();

            if (transitionType == 0) {
                firstTransitionType(currentFragment);
            } else {
                secondTransitionType(currentFragment);
            }
        }

        if (!isFullPeriodFunction(registerLength, function))
            return generate();
        return function;
    }

    private void initFields() {
        function = of(functionLength);
        fragmentList = new LinkedList<>();
    }

    private void initFragmentSetAndFunction() {
        BitwiseNumber start1 = oneBit(registerLength, registerLength - 1);  // {1,0,...,0}
        BitwiseNumber finish1 = oneBit(registerLength, 0);                  // {0,...,0,1}
        fragmentList.add(Fragment.of(start1, finish1));

        BitwiseNumber start2 = oneZero(registerLength, registerLength - 1); // {0,1,...,1}
        BitwiseNumber finish2 = oneZero(registerLength, 0);                 // {1,...,1,0}
        fragmentList.add(Fragment.of(start2, finish2));

        function = function.setBit(0, start1.intValue());                   // f({1,0,...,0}) = 0
        function = function.setBit(1, 0);                                   // f({0,0,...,0}) = 1

        function = function.setBit(1, start2.intValue());                   // f({0,1,...,1}) = 1
        function = function.setBit(0, allBits(registerLength).intValue());  // f({1,...,1,1}) = 0
    }

    private void firstTransitionType(Fragment currentFragment) {
        function = function.setBit(1, currentFragment.getStart().intValue());
        function = function.setBit(0, currentFragment.getFinish().intValue());

        Optional<Fragment> fragmentQOptional = findFragmentWithStart(Xn1);
        if (fragmentQOptional.isPresent()) {
            Fragment fragmentQ = fragmentQOptional.get();

            fragmentList.remove(fragmentQ);
            fragmentJ = fragmentJ.changeFinish(fragmentQ.getFinish());
        } else {
            fragmentJ = fragmentJ.changeFinish(Xn1);
        }

        Optional<Fragment> fragmentGOptional = findFragmentWithStart(Xn0);
        if (fragmentGOptional.isPresent()) {
            Fragment fragmentG = fragmentGOptional.get();

            fragmentList.remove(fragmentG);
            fragmentI = fragmentI.changeFinish(fragmentG.getFinish());
        } else {
            fragmentI = fragmentI.changeFinish(Xn0);
        }
    }

    private void secondTransitionType(Fragment currentFragment) {
        function = function.setBit(0, currentFragment.getStart().intValue());
        function = function.setBit(1, currentFragment.getFinish().intValue());

        Optional<Fragment> fragmentQOptional = findFragmentWithStart(Xn0);
        if (fragmentQOptional.isPresent()) {
            Fragment fragmentQ = fragmentQOptional.get();

            fragmentList.remove(fragmentQ);
            fragmentJ = fragmentJ.changeFinish(fragmentQ.getFinish());
        } else {
            fragmentJ = fragmentJ.changeFinish(Xn0);
        }

        Optional<Fragment> fragmentGOptional = findFragmentWithStart(Xn1);
        if (fragmentGOptional.isPresent()) {
            Fragment fragmentG = fragmentGOptional.get();

            fragmentList.remove(fragmentG);
            fragmentI = fragmentI.changeFinish(fragmentG.getFinish());
        } else {
            fragmentI = fragmentI.changeFinish(Xn1);
        }
    }

    private int getTransitionType() {
        int transitionType;

        if (Xn0.equals(fragmentJ.getStart()) || Xn1.equals(fragmentI.getStart())) {
            transitionType = 0;
        } else if (Xn1.equals(fragmentJ.getStart()) || Xn0.equals(fragmentI.getStart())) {
            transitionType = 1;
        } else {
            transitionType = transitionGenerator.generateBit();
        }

        return transitionType;
    }

    private Fragment getOrCreateFragment(Optional<Fragment> fragmentOptional, Fragment elseFragment) {
        Fragment resultFragment;

        if (fragmentOptional.isPresent()) {
            resultFragment = fragmentOptional.get();
        } else {
            resultFragment = elseFragment;
            fragmentList.add(resultFragment);
        }

        return resultFragment;
    }

    private Optional<Fragment> findFragmentWithStart(BitwiseNumber start) {
        return fragmentList.stream()
                .filter(f -> f.getStart().equals(start))
                .findFirst();
    }

    private Optional<Fragment> findFragmentWithFinish(BitwiseNumber finish) {
        return fragmentList.stream()
                .filter(f -> f.getFinish().equals(finish))
                .findFirst();
    }

    private Fragment retrieveRandomFragment() {
        int randIndex = fragmentIndexGenerator.nextInt(fragmentList.size());

        return fragmentList.get(randIndex);
    }

   //----- Setters -----

    public void setTransitionGenerator(BitGenerator transitionGenerator) {
        this.transitionGenerator = transitionGenerator;
    }

    public void setFragmentIndexGenerator(IntGenerator fragmentIndexGenerator) {
        this.fragmentIndexGenerator = fragmentIndexGenerator;
    }
}
