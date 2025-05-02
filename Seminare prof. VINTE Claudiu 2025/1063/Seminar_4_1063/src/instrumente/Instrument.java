package instrumente;

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
    public double valoare() {
        double valoare = 0.0;
        for (var operatiune : operatiuni) {
            valoare += operatiune.getPret() * operatiune.getCantitate() *
                    operatiune.getTip().getDirectie();
        }

        return valoare;
    }

    @Override
    public String toString() {
//        return "Instrument{" +
//                "simbol='" + simbol + '\'' +
//                ", operatiuni=" + operatiuni +
//                '}';
        StringBuilder rezultat = new StringBuilder();
        String separator = ",";

        rezultat.append(this.getSimbol()); rezultat.append(separator);
        rezultat.append(this.valoare()); rezultat.append(System.lineSeparator());
        for (var operatiune : operatiuni) {
            rezultat.append(operatiune.toString());
        }
        rezultat.append(System.lineSeparator());

        return rezultat.toString();
    }

    public static final class Operatiune {
        private final TipOperatiune tip;
        private final LocalDate data;
        private final double pret;
        private final int cantitate;

        public Operatiune(TipOperatiune tip, LocalDate data,
                          double pret, int cantitate) {
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

        public int getCantitate() {
            return cantitate;
        }

        @Override
        public String toString() {
            StringBuilder rezultat = new StringBuilder();
            String separator = ",";

            rezultat.append(this.getTip()); rezultat.append(separator);
            rezultat.append(this.getData().getYear()); rezultat.append(separator);
            rezultat.append(this.getData().getMonthValue()); rezultat.append(separator);
            rezultat.append(this.getData().getDayOfMonth()); rezultat.append(separator);
            rezultat.append(this.getPret()); rezultat.append(separator);
            rezultat.append(this.getCantitate()); rezultat.append(separator);

            return rezultat.toString();
        }
    }
}
