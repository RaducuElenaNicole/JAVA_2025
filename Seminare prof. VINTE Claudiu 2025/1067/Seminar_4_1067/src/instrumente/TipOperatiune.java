package instrumente;

public enum TipOperatiune {
    CUMPARARE ("CUMPARARE"),
    VANZARE ("VANZARE");

    private String valoare;
    private int directia;

    TipOperatiune(String valoare) {
        this.valoare = valoare;
        if (this.valoare.equalsIgnoreCase("CUMPARARE"))
            this.directia = 1;
        else
            directia = -1;
    }

    public String getValoare() {
        return valoare;
    }

    public void setValoare(String valoare) {
        this.valoare = valoare;
    }

    public int getDirectia() {
        return directia;
    }

    public void setDirectia(int directia) {
        this.directia = directia;
    }
}
