package instrumente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Instrument extends Object implements Evaluabil {
    private String symbol;
    private List<Operatiune> operatiuni;
    public Instrument()
    {
        this.symbol = "N/A";
        this.operatiuni = new ArrayList<>();
    }

    public Instrument(String symbol, List<Operatiune> operatiuni) {
        this.symbol = symbol;
        this.operatiuni = operatiuni;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<Operatiune> getOperatiuni() {
        return operatiuni;
    }

    public void setOperatiuni(List<Operatiune> operatiuni) {
        this.operatiuni = operatiuni;
    }
    @Override
    public double valoare()
    {
        double val = 0;
        for (var operatiune:this.operatiuni
             ) {
            val += operatiune.getPret()* operatiune.getCantitate()*operatiune.getTip().getDirectie();
        }
        return val;
    }


    @Override
    public String toString() {
//            return "Operatiune{" +
//                    "tip=" + tip +
//                    ", data=" + data +
//                    ", pret=" + pret +
//                    ", cantitate=" + cantitate +
//                    '}';
        StringBuilder rezultat = new StringBuilder();
        String separator = ",";
        rezultat.append(getSymbol());
        rezultat.append(separator);
        rezultat.append(valoare());
        rezultat.append(System.lineSeparator());
        for (var operatiune:this.operatiuni
             ) {
            rezultat.append(operatiune.toString());
        }
        rezultat.append(System.lineSeparator());
        return rezultat.toString();
    }


        public static final class Operatiune
    {
        private final TipOperatiune tip;
        private final LocalDate data;
        private final double pret;
        private final int cantitate;

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

        public int getCantitate() {
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
            StringBuilder rezultat = new StringBuilder();
            String separator =",";
            rezultat.append(getTip());rezultat.append(separator);
            rezultat.append(getData().getYear());rezultat.append(separator);
            rezultat.append(getData().getMonthValue());rezultat.append(separator);
            rezultat.append(getData().getDayOfMonth());rezultat.append(separator);
            rezultat.append(getPret());rezultat.append(separator);
            rezultat.append(getCantitate());rezultat.append(separator);
            return rezultat.toString();

        }
    }
}
