package es.unex.giiis.bss.jgarciapft.model;

public enum MinutiaTypes {
    CUT(1), BIFURCATION(3), INVALID(0);

    private final int val;

    MinutiaTypes(int val) {
        this.val = val;
    }

    public static MinutiaTypes fromVal(int val) {
        return val == CUT.val ? CUT : val == BIFURCATION.val ? BIFURCATION : INVALID;
    }

    public int num() {
        return val;
    }
}
