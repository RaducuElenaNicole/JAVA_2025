package clase;

public class Specializare {
    private int codSpecializare;
    private int numarLocuri;

    public Specializare(int codSpecializare, int numarLocuri) {
        this.codSpecializare = codSpecializare;
        this.numarLocuri = numarLocuri;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("clase.Specializare{");
        sb.append("codSpecializare=").append(codSpecializare);
        sb.append(", numarLocuri=").append(numarLocuri);
        sb.append('}');
        return sb.toString();
    }

    public int getCodSpecializare() {
        return codSpecializare;
    }

    public void setCodSpecializare(int codSpecializare) {
        this.codSpecializare = codSpecializare;
    }

    public int getNumarLocuri() {
        return numarLocuri;
    }

    public void setNumarLocuri(int numarLocuri) {
        this.numarLocuri = numarLocuri;
    }
}
