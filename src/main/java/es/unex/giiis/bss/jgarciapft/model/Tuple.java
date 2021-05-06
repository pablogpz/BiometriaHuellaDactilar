package es.unex.giiis.bss.jgarciapft.model;

public class Tuple<T, U> {

    private T first;
    private U second;

    public Tuple(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public U second() {
        return second;
    }

    public void setSecond(U second) {
        this.second = second;
    }
}
