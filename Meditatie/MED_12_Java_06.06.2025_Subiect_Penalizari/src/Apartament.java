package src;

public class Apartament implements Comparable<Apartament> {
    private int nrAp;
    private String nume;
    private float valoarePlata;

    public Apartament(int nrAp, String nume, float valoarePlata) {
        this.nrAp = nrAp;
        this.nume = nume;
        this.valoarePlata = valoarePlata;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Apartament{");
        sb.append("nrAp=").append(nrAp);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", valoarePlata=").append(valoarePlata);
        sb.append('}');
        return sb.toString();
    }

    public int getNrAp() {
        return nrAp;
    }

    public void setNrAp(int nrAp) {
        this.nrAp = nrAp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public float getValoarePlata() {
        return valoarePlata;
    }

    public void setValoarePlata(float valoarePlata) {
        this.valoarePlata = valoarePlata;
    }


    @Override
    public int compareTo(Apartament ap) {
       if(this.valoarePlata > ap.valoarePlata){
           return 1;
       }else if(this.valoarePlata < ap.valoarePlata){
           return -1;
       }
       return 0;
    }
}
