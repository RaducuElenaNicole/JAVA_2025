package instrumente;

public enum TipOperatiune {
    CUMPARARE ("CUMPARARE"),
    VANZARE ("VANZARE");
    private String valoare;
    private int directie;

    TipOperatiune(String valoare) {
        this.valoare = valoare;
        if (this.valoare.equalsIgnoreCase("CUMPARARE"))
            this.directie=1;
        else this.directie=-1;
    }

    public String getValoare() {
        return valoare;
    }

    public void setValoare(String valoare) {
        this.valoare = valoare;
    }

    public int getDirectie() {
        return directie;
    }

    public void setDirectie(int directie) {
        this.directie = directie;
    }
}
