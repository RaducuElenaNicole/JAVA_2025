import java.util.Collections;
import java.util.List;

class Produs {
    private final int cod;
    private final String denumire;
    private final List<Tranzactie> tranzactii;

    public Produs(int cod, String denumire, List<Tranzactie> tranzactii) {
        this.cod = cod;
        this.denumire = denumire;
        this.tranzactii = Collections.unmodifiableList(tranzactii);
    }

    public int getCod() {
        return cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public List<Tranzactie> getTranzactii() {
        return tranzactii;
    }

    public int getStoc() {
        return tranzactii.stream()
                .mapToInt(Tranzactie::getDeltaStoc)
                .sum();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Produs{");
        sb.append("cod=").append(cod);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", tranzactii=").append(tranzactii);
        sb.append('}');
        return sb.toString();
    }
}
