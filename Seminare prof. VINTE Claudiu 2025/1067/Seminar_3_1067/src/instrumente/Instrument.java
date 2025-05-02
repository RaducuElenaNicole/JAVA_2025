package instrumente;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Instrument implements Evaluabil {
    private String simbol;
    private List<Operatiune> operatiuni;

    public Instrument() {
        this.simbol = null;
        this.operatiuni = new ArrayList<>();
    }

    public Instrument(String simbol, List<Operatiune> operatiuni) {
        this.simbol = simbol;
        this.operatiuni = operatiuni;
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public List<Operatiune> getOperatiuni() {
        return operatiuni;
    }

    public void setOperatiuni(List<Operatiune> operatiuni) {
        this.operatiuni = operatiuni;
    }

    @Override
    public double valoare(){
        double valoare = 0;
        for(var element : this.operatiuni)
        {
            valoare += element.getPret() * element.getCantitate() * element.getTip().getDirectia();
        }
        return valoare;
    }

    @Override
    public String toString() {
        String separator = ",";
        StringBuilder rezultat = new StringBuilder();
        rezultat.append(this.simbol); rezultat.append(separator);
        rezultat.append(valoare()); rezultat.append(System.lineSeparator());
        for(var element : this.operatiuni)
        {
            rezultat.append(element.toString());
        }

        return rezultat.toString();
    }

    public static final class Operatiune {
        private final TipOperatiune tip;
        private final LocalDate data;
        private final double pret;
        private final double cantitate;

        public Operatiune(TipOperatiune tip, LocalDate data, double pret, int cantitate) {
            this.tip = tip;
            this.data = data;
            this.pret = pret;
            this.cantitate = cantitate;
        }

        public TipOperatiune getTip() {
            return tip;
        }

        public LocalDate getData() {
            return data;
        }

        public double getPret() {
            return pret;
        }

        public double getCantitate() {
            return cantitate;
        }

        @Override
        public String toString() {
//            return "Operatiune{" +
//                    "tip=" + tip +
//                    ", data=" + data +
//                    ", pret=" + pret +
//                    ", cantitate=" + cantitate +
//                    '}';
            String separator = ",";
            StringBuilder rezultat = new StringBuilder();
            rezultat.append(getTip()); rezultat.append(separator);
            rezultat.append(getData().getYear()); rezultat.append(separator);
            rezultat.append(getData().getMonthValue()); rezultat.append(separator);
            rezultat.append(getData().getDayOfMonth()); rezultat.append(separator);

            rezultat.append(getPret()); rezultat.append(separator);
            rezultat.append(getCantitate()); rezultat.append(separator);

            return rezultat.toString();
        }


    }
}
