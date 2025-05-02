package instrumente;

import java.util.List;

public class Actiune extends Instrument implements Evaluabil{
    private double dividend;//% de divident 2.5 = 2.5%


    public Actiune() {
        super();
        this.dividend = -1;// prin conventie
    }

    public Actiune(String symbol, List<Operatiune> operatiuni, double dividend) {
        super(symbol, operatiuni);
        this.dividend = dividend;
    }

    public double getDividend() {
        return dividend;
    }

    public void setDividend(double dividend) {
        this.dividend = dividend;
    }

    @Override
    public double valoare()
    {
        double a=super.valoare();
        if(dividend>0)
            a+=super.valoare()*dividend/100;
        return a;
    }
    @Override
    public String toString() {
        StringBuilder rezultat = new StringBuilder();
        String separator = ",";
        rezultat.append(getSymbol());
        rezultat.append(separator);
        rezultat.append(valoare());
        rezultat.append(separator);
        rezultat.append(getDividend());
        rezultat.append(System.lineSeparator());
        for (var operatiune:this.getOperatiuni()
        ) {
            rezultat.append(operatiune.toString());
        }
        rezultat.append(System.lineSeparator());
        return rezultat.toString();
    }
}
