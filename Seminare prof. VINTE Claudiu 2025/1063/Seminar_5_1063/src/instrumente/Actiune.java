package instrumente;

import java.util.List;

public class Actiune extends Instrument implements Evaluabil {

  private double procentDividend; //O sa fie cu semnificatia de procent

  public Actiune() {
    super();
    procentDividend = -1;
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
    valoare += (procentDividend * valoare) /100;
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
    rezultat.append(this.valoare()); rezultat.append(separator);
    rezultat.append(this.procentDividend);
    rezultat.append(System.lineSeparator());

    for (var operatiune : this.getOperatiuni()) {
      rezultat.append(operatiune.toString());
    }
    rezultat.append(System.lineSeparator());

    return rezultat.toString();
  }





}
