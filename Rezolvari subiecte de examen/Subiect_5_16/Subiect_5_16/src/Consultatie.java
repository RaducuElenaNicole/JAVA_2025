package Subiect_5_16.src;

public class Consultatie {
    private String denumireSpecializare;
    private int codManevra;
    private int numarManevre;

    public Consultatie(String denumireSpecializare, int codManevra, int numarManevre) {
        this.denumireSpecializare = denumireSpecializare;
        this.codManevra = codManevra;
        this.numarManevre = numarManevre;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Consultatie{");
        sb.append("denumireSpecializare='").append(denumireSpecializare).append('\'');
        sb.append(", codManevra=").append(codManevra);
        sb.append(", numarManevre=").append(numarManevre);
        sb.append('}');
        return sb.toString();
    }

    public String getDenumireSpecializare() {
        return denumireSpecializare;
    }

    public void setDenumireSpecializare(String denumireSpecializare) {
        this.denumireSpecializare = denumireSpecializare;
    }

    public int getCodManevra() {
        return codManevra;
    }

    public void setCodManevra(int codManevra) {
        this.codManevra = codManevra;
    }

    public int getNumarManevre() {
        return numarManevre;
    }

    public void setNumarManevre(int numarManevre) {
        this.numarManevre = numarManevre;
    }
}
