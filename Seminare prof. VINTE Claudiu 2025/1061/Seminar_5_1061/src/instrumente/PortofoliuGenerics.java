package instrumente;

import java.util.HashMap;
import java.util.Map;

public class PortofoliuGenerics<T> {
    private Map<String, T> portofoliu;

    public PortofoliuGenerics() {
        this.portofoliu = new HashMap<>();
    }

    public PortofoliuGenerics(Map<String, T> portofoliu) {
        this.portofoliu = portofoliu;
    }

    public Map<String, T> getPortofoliu() {
        return portofoliu;
    }

    public void setPortofoliu(Map<String, T> portofoliu) {
        this.portofoliu = portofoliu;
    }

    public void adaugaInstrument(String symbol, T valoare){
        portofoliu.put(symbol, valoare);
    }

    public T getInstrument(String symbol){
        return portofoliu.get(symbol);
    }

    public double valoarePortofoliu(){
        double valoare = 0;

        for(var entry : portofoliu.entrySet()){
            if(entry.getValue() instanceof Actiune){
                valoare+=((Actiune) entry.getValue()).valoare();
            }else{
                valoare+=((Instrument) entry.getValue()).valoare();
            }
        }

        return valoare;
    }
}
