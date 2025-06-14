package clase;

public class Optiune {
    private int codLiceu;
    private int codSpecializare;

    public Optiune(int codLiceu, int codSpecializare) {
        this.codLiceu = codLiceu;
        this.codSpecializare = codSpecializare;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("clase.Optiune{");
        sb.append("codLiceu=").append(codLiceu);
        sb.append(", codSpecializare=").append(codSpecializare);
        sb.append("}");
        return sb.toString();
    }

    public int getCodLiceu() {
        return codLiceu;
    }

    public void setCodLiceu(int codLiceu) {
        this.codLiceu = codLiceu;
    }

    public int getCodSpecializare() {
        return codSpecializare;
    }

    public void setCodSpecializare(int codSpecializare) {
        this.codSpecializare = codSpecializare;
    }
}
