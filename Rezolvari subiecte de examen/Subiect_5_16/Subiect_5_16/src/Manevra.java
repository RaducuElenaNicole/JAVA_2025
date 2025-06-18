package Subiect_5_16.src;

public class Manevra {
    private int codManevra;
    private int durata;
    private float tarif;

    public Manevra(int codManevra, int durata, float tarif) {
        this.codManevra = codManevra;
        this.durata = durata;
        this.tarif = tarif;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Manevra{");
        sb.append("codManevra=").append(codManevra);
        sb.append(", durata=").append(durata);
        sb.append(", tarif=").append(tarif);
        sb.append('}');
        return sb.toString();
    }

    public int getCodManevra() {
        return codManevra;
    }

    public void setCodManevra(int codManevra) {
        this.codManevra = codManevra;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }
}
