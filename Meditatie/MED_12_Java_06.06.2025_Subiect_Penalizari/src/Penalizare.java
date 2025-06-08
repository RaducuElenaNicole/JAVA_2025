package src;

public class Penalizare {
    private int nrAp;
    private Float valoare;

    public Penalizare(int nrAp, Float valoare) {
        this.nrAp = nrAp;
        this.valoare = valoare;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Penalizare{");
        sb.append("nrAp=").append(nrAp);
        sb.append(", valoare=").append(valoare);
        sb.append('}');
        return sb.toString();
    }

    public int getNrAp() {
        return nrAp;
    }

    public void setNrAp(int nrAp) {
        this.nrAp = nrAp;
    }

    public Float getValoare() {
        return valoare;
    }

    public void setValoare(Float valoare) {
        this.valoare = valoare;
    }
}
