package src;

public class Titlu {
    private String simbol;
    private String denumire;

    public Titlu(String simbol, String denumire) {
        this.simbol = simbol;
        this.denumire = denumire;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Titlu{");
        sb.append("simbol='").append(simbol).append('\'');
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
}
