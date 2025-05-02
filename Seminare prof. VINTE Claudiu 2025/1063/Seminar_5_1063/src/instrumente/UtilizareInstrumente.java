package instrumente;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class UtilizareInstrumente {
    public static void salvarePortofoliuText(String caleFisier,
                                             PortofoliuGenerics<Instrument> portofoliu)
                                             throws IOException {
        if (new File(caleFisier).getParentFile() != null) {
            new File(caleFisier).getParentFile().mkdirs();
        }
        try (var fisierText = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(caleFisier)))) {
            for (var entry:portofoliu.getPortofoliu().entrySet()) {
                fisierText.write(entry.getValue().toString());
            }
        }
    }

    public static PortofoliuGenerics<Instrument> incarcarePortofoliuText(String caleFisier)
            throws FileNotFoundException {
        PortofoliuGenerics<Instrument> portofoliu = new PortofoliuGenerics<>();

        try (var scannerFisier = new Scanner(new BufferedReader(new FileReader(caleFisier)))) {
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
        }
        return portofoliu;
    }

    public static void salvareFisierBinar(String caleFisier,
                                          PortofoliuGenerics<Instrument> portofoliu)
            throws IOException {
        if (new File(caleFisier).getParentFile() != null) {
            new File(caleFisier).getParentFile().mkdirs();
        }
        try (var fisierBinar = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(caleFisier)))) {
            for (var entry:portofoliu.getPortofoliu().entrySet()) {
                byte tipInstrument = 1; //1 inseamna obiect Instrument
                if (entry.getValue() instanceof Actiune) {
                    tipInstrument = 2; //2 inseamna obiect Actiune
                    fisierBinar.writeByte(tipInstrument);
                    fisierBinar.writeUTF(entry.getKey());
                    fisierBinar.writeDouble(((Actiune) entry.getValue()).getProcentDividend());
                } else {
                    fisierBinar.writeByte(tipInstrument);
                    fisierBinar.writeUTF(entry.getKey());
                }
                //Introducem numarul de operatiuni
                int nrOperatiuni = entry.getValue().getOperatiuni().size();
                fisierBinar.writeInt(nrOperatiuni);
                for (var operatiune : entry.getValue().getOperatiuni()) {
                    fisierBinar.writeUTF(operatiune.getTip().toString());
                    fisierBinar.writeInt(operatiune.getData().getYear());
                    fisierBinar.writeInt(operatiune.getData().getMonthValue());
                    fisierBinar.writeInt(operatiune.getData().getDayOfMonth());
                    fisierBinar.writeDouble(operatiune.getPret());
                    fisierBinar.writeInt(operatiune.getCantitate());
                }
            }
        }
    }

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
        // titparire valaore portofoliu
        System.out.println("Valoare portofoliu:" + "\t" +
                portofoliu.valoarePortofoliu());

        //tiparire instrument si data primei operatiune
        for (var entry:portofoliu.getPortofoliu().entrySet()) {
            LocalDate dataMinima = Collections.min(entry.getValue().getOperatiuni() ,
                    new Comparator<Instrument.Operatiune>() {
                        @Override
                        public int compare(Instrument.Operatiune o1,
                                           Instrument.Operatiune o2) {
                            return o1.getData().compareTo(o2.getData());
                        }
                    }).getData();
            System.out.println(entry.getKey() + "\t" + dataMinima);
        }
        //Salvare portofoliu in fisier text
        try {
            salvarePortofoliuText("./Portofolii/DeVineri/PortofoliuInstrumente.csv",
                                  portofoliu);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Incarcare portofoliu din fisier text
        PortofoliuGenerics<Instrument> portofiuDinText = new PortofoliuGenerics<>();
        try {
            System.out.println("Incarcare fisier text: ");
            portofiuDinText = incarcarePortofoliuText("./Portofolii/DeVineri/PortofoliuInstrumente.csv");
            for (var entry:portofiuDinText.getPortofoliu().entrySet())
            {
                System.out.print(entry.getValue().toString());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Salvare portofoliu in fisier binar
        try {
            salvareFisierBinar("./Portofolii/DeVineri/PortofoliuInstrumente.dat", portofoliu);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Incarcare portofoliu din fisier binar
        // utilizand DataInputStream
        //TODO

        //Salvare portofoliu in fisier binar ca obiecte Java serializate
        // utilizand ObjectOutputStream
        //TODO

        //Incarcare portofoliu din fisier binar ca obiecte Java serializate
        // utilizand ObjectInputStream
        //TODO
    }
}
