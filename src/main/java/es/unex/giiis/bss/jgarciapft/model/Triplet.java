package es.unex.giiis.bss.jgarciapft.model;

public class Triplet<T, U, V> extends Tuple<T, U> {

    private V third;

    public Triplet(T first, U second, V third) {
        super(first, second);
        this.third = third;
    }

    public V third() {
        return third;
    }

    public void setThird(V third) {
        this.third = third;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %s", first(), second(), third());
    }
}
