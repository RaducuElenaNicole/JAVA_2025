package instrumente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UtilizareInstrumente {

    public static void main(String[] args) {
        PortofoliuGenerics<Instrument> portofoliu = new PortofoliuGenerics<>();
        Scanner scannerFisier = new Scanner(System.in);
        while(scannerFisier.hasNext())
        {
            String linie1 = scannerFisier.nextLine();
            Scanner scannerLinie = new Scanner(linie1);
            scannerLinie.useDelimiter("[\\,]+");
            String simbol = scannerLinie.next();
//            double valoare = scannerLinie.nextDouble();
            double valoare = Double.valueOf(scannerLinie.next());
            double procentDividend = -1;
            if(scannerLinie.hasNext())
//                procentDividend = scannerLinie.nextDouble();
             procentDividend = Double.valueOf(scannerLinie.next());

            Instrument instrument;
            if(procentDividend == -1)
                instrument = new Instrument();
            else{
                instrument = new Actiune();
                ((Actiune)instrument).setProcentDividend(procentDividend);
            }
            instrument.setSimbol(simbol);
            String linie2 = scannerFisier.nextLine();
            scannerLinie = new Scanner(linie2);
            scannerLinie.useDelimiter("[\\,]+");
            List<Instrument.Operatiune> operatiuni = new ArrayList<>();
            while(scannerLinie.hasNext())
            {
                Instrument.Operatiune operatiune = new Instrument.Operatiune(
                        TipOperatiune.valueOf(scannerLinie.next()),
                                LocalDate.of(scannerLinie.nextInt(),
                                        scannerLinie.nextInt(),
                                        scannerLinie.nextInt()),
                        Double.parseDouble(scannerLinie.next()),
                        scannerLinie.nextInt());
                operatiuni.add(operatiune);
            }
            instrument.setOperatiuni(operatiuni);
            portofoliu.adaugaInstrument(simbol,instrument);
        }
        for (var entry:portofoliu.getPortofoliu().entrySet())
        {
            System.out.print(entry.getValue().toString());
        }
    }
}
