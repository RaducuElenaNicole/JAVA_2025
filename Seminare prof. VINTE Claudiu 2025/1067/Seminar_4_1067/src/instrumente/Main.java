package instrumente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner_fisier = new Scanner(System.in);
        PortofoliuGenerics<Instrument> portofoliu = new PortofoliuGenerics<>();


        while (scanner_fisier.hasNext())
        {
            String linie1 = scanner_fisier.nextLine();
            Scanner scanner_linie = new Scanner(linie1);
            scanner_linie.useDelimiter("[\\,]+");
            String simbol = scanner_linie.next();
            double valoare = Double.valueOf(scanner_linie.next());
            double procentDividend = -1;
            if(scanner_linie.hasNext()) {
                procentDividend = Double.valueOf(scanner_linie.next());
            }
            Instrument instrument;
            if(procentDividend == -1) {
                instrument = new Instrument();
            }
            else {
                instrument = new Actiune();
                ((Actiune)instrument).setProcentDividend(procentDividend);
            }
            instrument.setSimbol(simbol);

            String linie2 = scanner_fisier.nextLine();
            scanner_linie = new Scanner(linie2);
            scanner_linie.useDelimiter("[\\,]+");

            List<Instrument.Operatiune> operatiuni = new ArrayList<>();

            while (scanner_linie.hasNext())
            {
                operatiuni.add(new Instrument.Operatiune(TipOperatiune.valueOf(scanner_linie.next()),
                        LocalDate.of(scanner_linie.nextInt(),scanner_linie.nextInt(),scanner_linie.nextInt()),
                        Double.valueOf(scanner_linie.next()),
                        Integer.valueOf(scanner_linie.next())));
            }

            instrument.setOperatiuni(operatiuni);
            portofoliu.adaugaInstrument(simbol, instrument);
        }
        //tiparire portofoliu
        for(var entry : portofoliu.getPortofoliu().entrySet()){
            System.out.print(entry.getValue().toString());
        }

        //valoare portofoliu
        System.out.println("Valoare portofoliu: " + portofoliu.valoarePortofoliu());

    }
}
