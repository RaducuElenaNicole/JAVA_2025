public enum Tip {
    ORAL("ORAL"),
    SCRIS("SCRIS"),
    MIXT("MIXT"),
    GRILA("GRILA"),
    NECUNOSCUT("NECUNOSCUT");

    String valoare;

    Tip(String valoare) {
        this.valoare = valoare;
    }

    public String getValoare() {
        return valoare;
    }

    public void setValoare(String valoare) {
        this.valoare = valoare;
    }
}
