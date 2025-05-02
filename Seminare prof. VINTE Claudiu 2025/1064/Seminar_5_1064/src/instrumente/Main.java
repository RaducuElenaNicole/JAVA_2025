package instrumente;


import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void salavarePortofoliuBinar(String caleFisier,
                      PortofoliuGenerics<Instrument> portofoliu) throws IOException {
        if(new File(caleFisier).getParentFile()!=null)
        {
            new File(caleFisier).getParentFile().mkdirs();
        }
        try(
            var fisierBinar = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(caleFisier)))
                )
        {
            for (var entry:portofoliu.getPortofoliu().entrySet()
                 ) {
                if (entry.getValue() instanceof Actiune) {
                    fisierBinar.writeByte(2); // 2 inseamna Actiune
                    fisierBinar.writeUTF(entry.getKey().toString());
                    fisierBinar.writeDouble(((Actiune) entry.getValue()).getDividend());
                }
                else {
                    fisierBinar.writeByte(1); // 1 inseamna instrument
                    fisierBinar.writeUTF(entry.getKey().toString());
                }
                // scriem nr de operatiuni pt fiecare instrument
                fisierBinar.writeInt(entry.getValue().getOperatiuni().size());
                for (var operatiune:entry.getValue().getOperatiuni()
                     ) {
                    fisierBinar.writeUTF(operatiune.getTip().toString());
                    fisierBinar.writeInt(operatiune.getData().getYear());
                    fisierBinar.writeInt(operatiune.getData().getMonthValue());
                    fisierBinar.writeInt(operatiune.getData().getDayOfMonth());
                    fisierBinar.writeDouble(operatiune.getPret());
                    fisierBinar.writeDouble(operatiune.getCantitate());

                }
            }
        }
    }
    public static void salvarePortofoliuText(String caleFisier,
                     PortofoliuGenerics<Instrument> portofoliu)
            throws IOException {
        if(new File(caleFisier).getParentFile()!=null)
        {
            new File(caleFisier).getParentFile().mkdirs();

        }
        try (var fisier = new PrintWriter(
                new BufferedWriter(new FileWriter(caleFisier))))
        {
            for (var entry:
                    portofoliu.getPortofoliu().entrySet()) {
                fisier.write(entry.getValue().toString());
            }
        }
    }
    public static PortofoliuGenerics<Instrument> incarcarePortofoliuBinar (
            String caleFisier
    ) throws IOException {
        PortofoliuGenerics<Instrument> portofoliu = new PortofoliuGenerics<>();
        try
            (var fisierBinar = new DataInputStream(new BufferedInputStream(new FileInputStream(caleFisier))))
            {
                while (true)
                {

                    byte tipInstrument = fisierBinar.readByte();
                    String simbol = fisierBinar.readUTF();
                    double dividend = -1;
                    if(tipInstrument==2)
                    {
                        dividend = fisierBinar.readDouble();
                    }
                    // citim nr operatiuni
                    int nrOperatiuni = fisierBinar.readInt();
                    // lista goala operatiuni de populat

                    if(dividend !=-1)
                    {

                    }
                }
            }

        return portofoliu;
    }
    public static PortofoliuGenerics<Instrument> incarcarePortofoliuText(
            String caleFisier) throws FileNotFoundException {
        PortofoliuGenerics<Instrument> portofoliu = new PortofoliuGenerics<>();
        try (Scanner scanner_fisier = new Scanner(
                new BufferedReader(new FileReader(caleFisier)))) {
            while (scanner_fisier.hasNext()) {
                String linie1 = scanner_fisier.nextLine();
                Scanner scanner_linie = new Scanner(linie1);
                scanner_linie.useDelimiter("[\\,]+");
                String simbol = scanner_linie.next();
                double valoare = Double.parseDouble(scanner_linie.next());// facem conversie explicita catre double pt ca crapa compilatorul
                double procentDividend = -1;
                if (scanner_linie.hasNext())
                    procentDividend = Double.parseDouble(scanner_linie.next());// facem conversie explicita catre double pt ca crapa compilatorul
                Instrument instrument;
                if (procentDividend == -1)
                    instrument = new Instrument();
                else {
                    instrument = new Actiune();
                    ((Actiune) instrument).setDividend(procentDividend);// facem conversie explicita catre double pt ca crapa compilatorul
                }
                instrument.setSymbol(simbol);
                String linie2 = scanner_fisier.nextLine();
                scanner_linie = new Scanner(linie2);
                scanner_linie.useDelimiter("[\\,]+");
                //CUMPARARE,2022,4,3,200.0,3
                List<Instrument.Operatiune> lista = new ArrayList<>();
                while (scanner_linie.hasNext()) {
                    // instantiem o operatiune (derivata din instrument, si o cosnturim cu construcotul cu pametrii)
                    Instrument.Operatiune operatiune = new Instrument.Operatiune(
                            TipOperatiune.valueOf(scanner_linie.next()),// setez numele
                            LocalDate.of(scanner_linie.nextInt() // an
                                    , scanner_linie.nextInt() // luna
                                    , scanner_linie.nextInt()),// zi
                            Double.parseDouble(scanner_linie.next()), //valoare (facem conversie explicita catre double pt ca crapa compilatorul
                            scanner_linie.nextInt());// cantitate
                    lista.add(operatiune);
                }
                instrument.setOperatiuni(lista);
                portofoliu.adaugaInstrument(simbol, instrument);
            }
        }
        return portofoliu;
    }

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

        for (var entry:
                portofoliu.getPortofoliu().entrySet()) {
            LocalDate dataMinima = Collections.min(entry.getValue().getOperatiuni(),
                    new Comparator<Instrument.Operatiune>() {
                @Override
                public int compare(Instrument.Operatiune o1, Instrument.Operatiune o2) {
                    return o1.getData().compareTo(o2.getData());
                }
            }).getData();
                System.out.println(entry.getKey()+"\t"+dataMinima.toString());
            }
        // salvare portofoliu in fisier text(.csv)
        try {
            salvarePortofoliuText("./Portofoliu/Noi/PortofoliuInstrumente.csv"
                    ,portofoliu);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PortofoliuGenerics<Instrument> portofoliuDinText = new PortofoliuGenerics<>();

        try {
            portofoliuDinText = incarcarePortofoliuText("./Portofoliu/Noi/PortofoliuInstrumente.csv");
            for (var entry:
                    portofoliuDinText.getPortofoliu().entrySet()) {
                System.out.print(entry.getValue().toString());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //salvare portofoliu in fisier binar
        try {
            salavarePortofoliuBinar("./Portofoliu/Noi/PortofoliuInstrumente.dat",portofoliu);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    }

