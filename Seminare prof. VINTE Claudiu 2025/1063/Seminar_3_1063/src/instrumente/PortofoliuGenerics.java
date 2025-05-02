package instrumente;

import java.util.*;

public class PortofoliuGenerics<T> {
    Map<String, T> portofoliu;

    public PortofoliuGenerics() {
        this.portofoliu = new HashMap<>();
    }

    public Map<String, T> getPortofoliu() {
        return portofoliu;
    }

    public void setPortofoliu(Map<String, T> portofoliu) {
        this.portofoliu = portofoliu;
    }

    public T getInstrument(String simbol) {
       return portofoliu.get(simbol);
    }

    public void adaugaInstrument(String simbol, T instrument) {
        portofoliu.put(simbol, instrument);
    }

    public <T> void afiseazaPortofoliu() {
        for (var entry : portofoliu.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
    }

    public <T> double valoarePortofoliu() {
        double valoare = 0.0;
        for (var entry : portofoliu.entrySet()) {
            if (entry.getValue() instanceof Actiune)
                valoare += ((Actiune)entry.getValue()).valoare();
            else
                valoare += ((Instrument)entry.getValue()).valoare();
        }
        return valoare;
    }
}
