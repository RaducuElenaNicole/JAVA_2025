package Subiect_5_16.src;

import java.util.List;

public class Specialitate {
    private String denumire;
    private List<Manevra> manevre;

    public Specialitate(String denumire, List<Manevra> manevre) {
        this.denumire = denumire;
        this.manevre = manevre;
    }

    @Override
    public String toString() {
        StringBuilder rezultat = new StringBuilder("Specialitate -> Denumire: " + this.denumire +
                " | Manevre: \n");
        for(var manevra : this.manevre){
            rezultat.append("   ").append(manevra.toString()).append("\n");
        }
        return rezultat.toString();
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public List<Manevra> getManevre() {
        return manevre;
    }

    public void setManevre(List<Manevra> manevre) {
        this.manevre = manevre;
    }
}
