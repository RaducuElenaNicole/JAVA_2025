package instrumente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Instrument implements Evaluabil, Cloneable {
    private String symbol;
    private List<Operatiune> listaOperatiuni;

    public Instrument() {
        this.symbol = null;
        this.listaOperatiuni = new ArrayList<>();
    }

    public Instrument(String symbol, List<Operatiune> listaOperatiuni) {
        this.symbol = symbol;
        this.listaOperatiuni = listaOperatiuni;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<Operatiune> getListaOperatiuni() {
        return listaOperatiuni;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setListaOperatiuni(List<Operatiune> listaOperatiuni) {
        this.listaOperatiuni = listaOperatiuni;
    }

    @Override
    public double valoare() {
        double valoare = 0;
        for(var operatiune : this.listaOperatiuni){
            valoare+=(operatiune.getPret() * operatiune.getCantitate()) * operatiune.getTip().getDirectie();
        }
        return valoare;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String separator = ",";
        builder.append(this.symbol);builder.append(separator);
        builder.append(this.valoare());builder.append(System.lineSeparator());
        for(var operatiune : listaOperatiuni){
            builder.append(operatiune.toString());
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    public static final class Operatiune{
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

//        @Override
//        public String toString() {
//            return "Operatiune{" +
//                    "tip=" + tip +
//                    ", data=" + data +
//                    ", pret=" + pret +
//                    ", cantitate=" + cantitate +
//                    '}';
//        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            String separator = ",";
            builder.append(this.getTip());builder.append(separator);
            builder.append(this.data.getYear());builder.append(separator);
            builder.append(this.data.getMonthValue());builder.append(separator);
            builder.append(this.data.getDayOfMonth());builder.append(separator);
            builder.append(this.pret);builder.append(separator);
            builder.append(this.cantitate);builder.append(separator);

            return builder.toString();
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instrument that)) return false;

        return symbol.equals(that.symbol) && listaOperatiuni.equals(that.listaOperatiuni);
    }

    @Override
    public int hashCode() {
        int result = symbol.hashCode();
        result = 31 * result + listaOperatiuni.hashCode();
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

/*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instrument that)) return false;
        return Objects.equals(symbol, that.symbol) && Objects.equals(listaOperatiuni, that.listaOperatiuni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, listaOperatiuni);
    }*/
}
