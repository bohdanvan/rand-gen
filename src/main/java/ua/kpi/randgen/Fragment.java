package ua.kpi.randgen;

/**
 * @author bvanchuhov
 */
public class Fragment {

    private BitwiseNumber start;
    private BitwiseNumber finish;

    public static Fragment of(BitwiseNumber start, BitwiseNumber finish) {
        return new Fragment(start, finish);
    }

    private Fragment(BitwiseNumber start, BitwiseNumber finish) {
        this.start = start;
        this.finish = finish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fragment)) return false;

        Fragment fragment = (Fragment) o;

        if (start != null ? !start.equals(fragment.start) : fragment.start != null) return false;
        return !(finish != null ? !finish.equals(fragment.finish) : fragment.finish != null);
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (finish != null ? finish.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("<%s, %s>", start.toString(), finish.toString());
    }

    //----- Getters and Setters -----

    public BitwiseNumber getStart() {
        return start;
    }

    public BitwiseNumber getFinish() {
        return finish;
    }

    public Fragment changeStart(BitwiseNumber start) {
        this.start = start;
        return this;
    }

    public Fragment changeFinish(BitwiseNumber finish) {
        this.finish = finish;
        return this;
    }
}
