public enum Tip {
    ORAL("ORAL"),
    SCRIS("SCRIS"),
    SCRIS_CU_EVALUARE_ORALA("SCRIS CU EVALUARE ORALA"),
    GRILA("GRILA"),
    MIXT("MIXT"),
    NECUNOSCUT("NECUNOSCUT");

    String valoare;

    Tip(String valoare) {
        this.valoare = valoare;
    }
}
