package hyenas.Models;

import java.util.Objects;

/**
 * Represents pair for use with Dijkstra's algorithm.
 * @author saikrishna
 * @param <A> the first item
 * @param <B> the second item
 */
public class ABPair<A, B> {
    /**
     * The first item.
     */
    private final A a;
    /**
     * The second item.
     */
    private final B b;

    /**
     * Initializes an ABPair.
     * @param pa the first item
     * @param pb the second item
     */
    public ABPair(A pa, B pb) {
        this.a = pa;
        this.b = pb;
    }

    /**
     * Get data A of the pair.
     * @return data A
     */
    public A getA() {
        return a;
    }

    /**
     * Get data B of the pair.
     * @return data B
     */
    public B getB() {
        return b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ABPair)) {
            return false;
        }
        ABPair<A, B> that = (ABPair<A, B>) obj;
        return that.a.equals(a) && that.b.equals(b);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.a);
        hash = 49 * hash + Objects.hashCode(this.b);
        return hash;
    }

}
