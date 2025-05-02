package instrumente;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main (String []args)
    {
        PortofoliuGenerics<Instrument> portofoliu = new PortofoliuGenerics<>();
    Scanner  scanner_fisier=new Scanner(System.in);
    while(scanner_fisier.hasNext()){
        String linie1=scanner_fisier.nextLine();
        Scanner scanner_linie=new Scanner(linie1);
        scanner_linie.useDelimiter("[\\,]+");
        String simbol=scanner_linie.next();
        double valoare=Double.parseDouble(scanner_linie.next());// facem conversie explicita catre double pt ca crapa compilatorul
        double procentDividend=-1;
        if(scanner_linie.hasNext())
            procentDividend=Double.parseDouble(scanner_linie.next());// facem conversie explicita catre double pt ca crapa compilatorul
        Instrument instrument;
        if(procentDividend==-1)
            instrument=new Instrument();
        else {
            instrument = new Actiune();
            ((Actiune) instrument).setDividend(procentDividend);// facem conversie explicita catre double pt ca crapa compilatorul
        }
        instrument.setSymbol(simbol);
        String linie2 = scanner_fisier.nextLine();
        scanner_linie=new Scanner(linie2);
        scanner_linie.useDelimiter("[\\,]+");
        //CUMPARARE,2022,4,3,200.0,3
        List<Instrument.Operatiune> lista = new ArrayList<>();
        while (scanner_linie.hasNext())
        {
            // instantiem o operatiune (derivata din instrument, si o cosnturim cu construcotul cu pametrii)
            Instrument.Operatiune operatiune = new Instrument.Operatiune(
                    TipOperatiune.valueOf(scanner_linie.next()),// setez numele
                    LocalDate.of(scanner_linie.nextInt() // an
                                ,scanner_linie.nextInt() // luna
                                ,scanner_linie.nextInt()),// zi
                    Double.parseDouble(scanner_linie.next()), //valoare (facem conversie explicita catre double pt ca crapa compilatorul
                    scanner_linie.nextInt());// cantitate
            lista.add(operatiune);
        }
        instrument.setOperatiuni(lista);
        portofoliu.adaugaInstrument(simbol,instrument);
        //System.out.print(instrument.toString());
    }
        for (var entry:
             portofoliu.getPortofoliu().entrySet()) {
            System.out.print(entry.getValue().toString());
        }
        System.out.println("Valoare portofoliu:"+portofoliu.valoarePortofoliu());


    }
}
