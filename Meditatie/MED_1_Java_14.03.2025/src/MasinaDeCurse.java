import java.util.Arrays;

public class MasinaDeCurse extends Masina{
    // extends =>
    // implements => interfata

    private int nrViteze;
    private float viteza;

    public MasinaDeCurse() {
    }

    public MasinaDeCurse(int id, String denumire, float pret, int[] dotari,
                         int nrViteze, float viteza) {

        super(id, denumire, pret, dotari);
        this.nrViteze = nrViteze;
        this.viteza = viteza;
    }

    @Override
    public String toString() {

        return "MasinaDeCurse{ id= " + super.getId() + ", denumire="
                + super.getDenumire() + ", pret= " + super.pret
                + ", dotari= " + Arrays.toString(super.getDotari()) +
                ", nrViteze=" + nrViteze +
                ", viteza=" + viteza +
                '}';
    }

}
