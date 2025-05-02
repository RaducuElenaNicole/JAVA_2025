package instrumente;

import java.util.List;

public class Actiune extends Instrument implements Evaluabil{
    private double procentDivident; //2.5 inseamna 2.5%

    public Actiune() {
        super();
        this.procentDivident = -1;
    }

    public Actiune(String symbol, List<Operatiune> listaOperatiuni, double procentDivident) {
        super(symbol, listaOperatiuni);
        this.procentDivident = procentDivident;
    }

    public double getProcentDivident() {
        return procentDivident;
    }

    public void setProcentDivident(double procentDivident) {
        this.procentDivident = procentDivident;
    }

    @Override
    public double valoare() {
        return super.valoare() * this.procentDivident / 100;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String separator = ",";
        builder.append(super.getSymbol());builder.append(separator);
        builder.append(this.valoare());builder.append(separator);
        builder.append(this.procentDivident);builder.append(System.lineSeparator());
        for(var operatiune : super.getListaOperatiuni()){
            builder.append(operatiune.toString());
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }
}
