package instrumente;

public enum TipOperatiune {
    CUMPARARE("CUMPARARE"),
    VANZARE("VANZARE");

    private String valoare;
    private int directia; // -1 sau 1

    TipOperatiune(String valoare) {
        this.valoare = valoare;
        if(this.valoare.equalsIgnoreCase("cumparare")){
            this.directia = 1;
        }else{
            this.directia = -1;
        }
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
