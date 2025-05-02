package instrumente;

public enum TipOperatiune {
    CUMPARARE("CUMPARARE"),
    VANZARE("VANZARE");

    private final String valoare;
    private final int directie;

    TipOperatiune(String valoare) {
        this.valoare = valoare;
        if (this.valoare.equalsIgnoreCase("CUMPARARE"))
            this.directie = 1;
        else
            this.directie = -1;
    }

    public int getDirectie() {
        return directie;
    }
}
