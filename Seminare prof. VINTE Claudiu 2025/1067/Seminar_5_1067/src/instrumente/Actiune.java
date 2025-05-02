package instrumente;

import java.util.List;

public class Actiune extends Instrument implements Evaluabil{
    private double procentDividend; //2.5 -> 2.5%

    public Actiune() {
        super();
        this.procentDividend = -1;
    }

    public Actiune(String simbol, List<Operatiune> operatiuni, double procentDividend) {
        super(simbol, operatiuni);
        this.procentDividend = procentDividend;
    }

    public double getProcentDividend() {
        return procentDividend;
    }

    public void setProcentDividend(double procentDividend) {
        this.procentDividend = procentDividend;
    }

    @Override
    public double valoare() {
        double valoare = super.valoare();
        return valoare += valoare * procentDividend/100;
    }

    @Override
    public String toString() {
//        return "Actiune{" +
//                "procentDividend=" + procentDividend +
//                '}';
        String separator = ",";
        StringBuilder rezultat = new StringBuilder();
        rezultat.append(getSimbol()); rezultat.append(separator);
        rezultat.append(valoare()); rezultat.append(separator);
        rezultat.append(this.procentDividend);
        rezultat.append(System.lineSeparator());
        for(var element : getOperatiuni())
        {
            rezultat.append(element.toString());
        }
        rezultat.append(System.lineSeparator());

        return rezultat.toString();
    }

}
