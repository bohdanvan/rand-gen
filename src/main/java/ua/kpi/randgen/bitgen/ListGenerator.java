package ua.kpi.randgen.bitgen;

import java.util.Arrays;
import java.util.List;

/**
 * Генератор чисел із списку. Для тестування.
 *
 * @author bvanchuhov
 */
public class ListGenerator implements BitGenerator, IntGenerator {

    private List<Integer> list;
    private int currentIndex;

    public ListGenerator(List<Integer> list) {
        this.list = list;
    }

    public ListGenerator(Integer... list) {
        this.list = Arrays.asList(list);
    }

    @Override
    public int generateBit() {
        return nextInt() % 2;
    }

    @Override
    public int nextInt(int bound) {
        return nextInt();
    }

    private int nextInt() {
        if (currentIndex == list.size()) {
            currentIndex = 0;
        }

        return list.get(currentIndex++);
    }
}
