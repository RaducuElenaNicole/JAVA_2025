package instrumente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException{
        Scanner scan = new Scanner(System.in);
        String linie1;
        String linie2;
        PortofoliuGenerics<Instrument> portofoliu = new PortofoliuGenerics<Instrument>();
        while(scan.hasNext()){
            Instrument i1;
            linie1 = scan.nextLine();
            Scanner scanLinie = new Scanner(linie1);
            scanLinie.useDelimiter("[\\,]+");
            String symbol = scanLinie.next(); //simbolul
            //consum valoarea
            double valoare = Double.valueOf(scanLinie.next());
            double procentDivident = -1;
            if(scanLinie.hasNext()){
                procentDivident = Double.valueOf(scanLinie.next());
            }

            if(procentDivident == -1){
                i1 = new Instrument();
            }else{
                i1 = new Actiune();
                ((Actiune) i1).setProcentDivident(procentDivident);
            }

            i1.setSymbol(symbol);

            linie2 = scan.nextLine();
            scanLinie = new Scanner(linie2);
            scanLinie.useDelimiter("[\\,]+");
            ArrayList<Instrument.Operatiune> operatiuni = new ArrayList<>();
            while(scanLinie.hasNext()){
                operatiuni.add(new Instrument.Operatiune(
                        TipOperatiune.valueOf(scanLinie.next()),
                        LocalDate.of(scanLinie.nextInt(),scanLinie.nextInt(),scanLinie.nextInt()),
                        Double.parseDouble(scanLinie.next()),
                        scanLinie.nextInt()
                ));
            }
            i1.setListaOperatiuni(operatiuni);
            portofoliu.adaugaInstrument(i1.getSymbol(), i1);
        }
        for(var entry : portofoliu.getPortofoliu().entrySet()){
            System.out.print(entry.getValue().toString());
        }

        Instrument clona = (Instrument) portofoliu.getInstrument("OMV").clone();
        System.out.println(clona.toString());

    }
}
