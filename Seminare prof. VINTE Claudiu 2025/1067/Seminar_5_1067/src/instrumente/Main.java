package instrumente;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void salvarePortofoliuBinar(String caleFisier,
                                             PortofoliuGenerics<Instrument> portofoliu)
            throws IOException {
        if (new File(caleFisier).getParentFile() != null) {
            new File(caleFisier).getParentFile().mkdirs();
        }

        try (var fisier_binar = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(caleFisier))))
        {
            for(var entry : portofoliu.getPortofoliu().entrySet())
            {
                byte tip_instrument = 1; //cod pentru Instrument
                if(entry.getValue() instanceof Actiune)
                {
                    tip_instrument = 2; //cod pentru Actiune
                    fisier_binar.writeByte(tip_instrument);
                    fisier_binar.writeUTF(entry.getValue().getSimbol());
                    fisier_binar.writeDouble(((Actiune) entry.getValue()).
                            getProcentDividend());

                }
                else
                {
                    fisier_binar.writeByte(tip_instrument);
                    fisier_binar.writeUTF(entry.getValue().getSimbol());
                }

                //Scriere numar operatiuni
                int numar_operatiuni = entry.getValue().getOperatiuni().size();

                fisier_binar.writeInt(numar_operatiuni);
                for (var operatiune : entry.getValue().getOperatiuni())
                {
                    fisier_binar.writeUTF(operatiune.getTip().toString());
                    // TODO

                }
            }
        }
    }

    public static void salvarePortofoliuText(String caleFisier,
                                     PortofoliuGenerics<Instrument> portofoliu)
            throws IOException {
        if(new File(caleFisier).getParentFile() != null)
        {
            new File(caleFisier).getParentFile().mkdirs();
        }
        try (var fisier=new PrintWriter(new BufferedWriter
                (new FileWriter(caleFisier))))
        {
            for(var entry: portofoliu.getPortofoliu().entrySet())
            {
                fisier.write(entry.getValue().toString());
            }
        }

    }   public static PortofoliuGenerics<Instrument> incarcareFisierText(
            String caleFisier) throws FileNotFoundException {

        PortofoliuGenerics<Instrument> portofoliu = new PortofoliuGenerics<>();

        try (var scanner_fisier=new Scanner(new BufferedReader(
                new FileReader(caleFisier)))) {



            while (scanner_fisier.hasNext()) {
                String linie1 = scanner_fisier.nextLine();
                Scanner scanner_linie = new Scanner(linie1);
                scanner_linie.useDelimiter("[\\,]+");
                String simbol = scanner_linie.next();
                double valoare = Double.valueOf(scanner_linie.next());
                double procentDividend = -1;
                if (scanner_linie.hasNext()) {
                    procentDividend = Double.valueOf(scanner_linie.next());
                }
                Instrument instrument;
                if (procentDividend == -1) {
                    instrument = new Instrument();
                } else {
                    instrument = new Actiune();
                    ((Actiune) instrument).setProcentDividend(procentDividend);
                }
                instrument.setSimbol(simbol);

                String linie2 = scanner_fisier.nextLine();
                scanner_linie = new Scanner(linie2);
                scanner_linie.useDelimiter("[\\,]+");

                List<Instrument.Operatiune> operatiuni = new ArrayList<>();

                while (scanner_linie.hasNext()) {
                    operatiuni.add(new Instrument.Operatiune(TipOperatiune.valueOf(scanner_linie.next()),
                            LocalDate.of(scanner_linie.nextInt(), scanner_linie.nextInt(), scanner_linie.nextInt()),
                            Double.valueOf(scanner_linie.next()),
                            Integer.valueOf(scanner_linie.next())));
                }

                instrument.setOperatiuni(operatiuni);
                portofoliu.adaugaInstrument(simbol, instrument);
            }
        }
        return portofoliu;
    }

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

        for(var entry : portofoliu.getPortofoliu().entrySet()){
            LocalDate data_min = Collections.min(entry.getValue().getOperatiuni(),
                    new Comparator<Instrument.Operatiune>() {
                        @Override
                        public int compare(Instrument.Operatiune o1,
                                           Instrument.Operatiune o2) {

                            return o1.getData().compareTo(o2.getData());
                        }
                    }).getData();
            System.out.println(entry.getKey() + "\t" + data_min.toString());
        }

        //salvare portofoliu in fisier text
        try {
            salvarePortofoliuText("./Portofolii/Curente/Portofoliu_instrumente.csv",
                    portofoliu);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //citire portofoliu din fisier text
        PortofoliuGenerics<Instrument> portofoliu_din_text = new PortofoliuGenerics<>();
        try {
            portofoliu_din_text = incarcareFisierText
                    ("./Portofolii/Curente/Portofoliu_instrumente.csv");
            for(var entry : portofoliu_din_text.getPortofoliu().entrySet()){
                System.out.print(entry.getValue().toString());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
