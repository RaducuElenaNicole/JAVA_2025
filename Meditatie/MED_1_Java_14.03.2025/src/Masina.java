import java.util.Arrays;
import java.util.Objects;

public class Masina implements Comparable<Masina> {
    private int id;
    private String denumire;
    public float pret;
    private int[] dotari;

    public Masina() {
        this.dotari = new int[2];
        this.dotari[0] = 1;
        this.dotari[1] = 2;
    }

    public Masina(int id, String denumire, float pret, int[] dotari) {
        this.id = id;
        this.denumire = denumire;
        this.pret = pret;
        // this.dotari = dotari; // shallow copy

        // deep copy
        if(dotari != null) {
            this.dotari = new int[dotari.length];
            for (int i = 0; i < dotari.length; i++) {
                this.dotari[i] = dotari[i];
            }
        }else{
            this.dotari = null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public int[] getDotari() {
        int[] dotariCopie = new int[dotari.length];
        for(int i = 0; i < dotari.length; i++){
            dotariCopie[i] = dotari[i];
        }
        return dotariCopie;
    }

    public void setDotari(int[] dotari) {
        if(dotari != null) {
            this.dotari = new int[dotari.length];
            for (int i = 0; i < dotari.length; i++) {
                this.dotari[i] = dotari[i];
            }
        }

        // Arrays.copyOf(dotari, dotari.length);
    }

    @Override
    public String toString() {
        return "Masina{" +
                "id = " + id +
                " | denumire = '" + denumire + '\'' +
                " | pret = " + pret +
                " | dotari = " + Arrays.toString(dotari) +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Masina copieMasina = new Masina();
        copieMasina.id = this.id;
        copieMasina.denumire = this.denumire;
        copieMasina.pret = this.pret;
        copieMasina.dotari = new int[this.dotari.length];
        for(int i = 0; i < copieMasina.dotari.length; i++){
            copieMasina.dotari[i] = this.dotari[i];
        }
        return copieMasina;
    }

    @Override
    public int compareTo(Masina m){
        if(id < m.id){
            return -1;
        }else if(id > m.id) {
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(denumire);
    }
}
