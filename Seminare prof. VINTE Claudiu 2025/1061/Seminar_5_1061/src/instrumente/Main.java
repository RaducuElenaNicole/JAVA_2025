package instrumente;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    //salvare portofoliu in fisier text(csv)
    public static void salvarePortofoliu(String caleFisier,
                                         PortofoliuGenerics<Instrument>portofoliu) throws IOException {
        if(new File(caleFisier).getParentFile()!=null){
            new File(caleFisier).getParentFile().mkdirs();
        }
        try(var fisier=new PrintWriter(new BufferedWriter(new FileWriter(caleFisier)))){
            for(var entry: portofoliu.getPortofoliu().entrySet()){
                fisier.write(entry.getValue().toString());
            }
        }
    }
    public static PortofoliuGenerics<Instrument> citirePortofoliu(String caleFisier)
            throws FileNotFoundException {
        PortofoliuGenerics<Instrument> portofoliu = new PortofoliuGenerics<Instrument>();
        try(var fisierScanner=new Scanner(new BufferedReader(new FileReader(caleFisier)))){
            String linie1;
            String linie2;
            while(fisierScanner.hasNext()){
                Instrument i1;
                linie1 = fisierScanner.nextLine();
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

                linie2 = fisierScanner.nextLine();
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
        }
        return portofoliu;
    }
    public static void salvarePortofoliuBinar(String caleFisier,
                                              PortofoliuGenerics<Instrument>portofoliu) throws IOException {
        if(new File(caleFisier).getParentFile()!=null){
            new File(caleFisier).getParentFile().mkdirs();
        }
        try(var fisier=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(caleFisier))) ){
            for(var entry: portofoliu.getPortofoliu().entrySet()){
                if(entry.getValue() instanceof Actiune){
                    fisier.writeByte(2);//codul 2 este pt o actiune
                    fisier.writeUTF(entry.getKey());
                    fisier.writeDouble(((Actiune) entry.getValue()).getProcentDivident());

                }else{
                    fisier.writeByte(1);//codul 1 este pt un instrument
                    fisier.writeUTF(entry.getKey());

                }
                //salvam numarul de operatiuni
                fisier.writeInt(entry.getValue().getListaOperatiuni().size());
                for(var operatiune:entry.getValue().getListaOperatiuni()){
                    fisier.writeUTF(operatiune.getTip().toString());
                    fisier.writeInt(operatiune.getData().getYear());
                    fisier.writeInt(operatiune.getData().getMonthValue());
                    fisier.writeInt(operatiune.getData().getDayOfMonth());
                    fisier.writeDouble(operatiune.getPret());
                    fisier.writeInt(operatiune.getCantitate());
                }
            }
        }
    }
    public static PortofoliuGenerics<Instrument> citirePortofoliuBinar(String caleFisier)
            throws IOException {
        PortofoliuGenerics<Instrument> portofoliu = new PortofoliuGenerics<Instrument>();
        try(var fisier=new DataInputStream(new BufferedInputStream(new FileInputStream(caleFisier)))){
            while(true){
                byte tipInstrument=fisier.readByte();
                String symbol=fisier.readUTF();
                double procentDivident=-1;
                if(tipInstrument==2){
                    procentDivident=fisier.readDouble();
                }
                int nrOperatiuni=fisier.readInt();
                List<Instrument.Operatiune> listaOperatiuni=new ArrayList<>();
                for(int i=0;i<nrOperatiuni;i++){
                    Instrument.Operatiune operatiune=new Instrument.Operatiune(
                            TipOperatiune.valueOf(fisier.readUTF()),
                            LocalDate.of(fisier.readInt(),
                                    fisier.readInt(),
                                    fisier.readInt()),
                            fisier.readDouble(),fisier.readInt()
                    );
                    listaOperatiuni.add(operatiune);
                }
            }
        }
    }
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
        //sa se implementeze si sa se testeze clonare profunda
        for( var entry: portofoliu.getPortofoliu().entrySet()){
            LocalDate dataMinima= Collections.min(entry.getValue().getListaOperatiuni(),
                    new Comparator<Instrument.Operatiune>() {
                        @Override
                        public int compare(Instrument.Operatiune o1,
                                           Instrument.Operatiune o2) {
                            return o1.getData().compareTo(o2.getData());
                        }
                    }).getData();
            System.out.println(entry.getKey()+"\t"+dataMinima.toString());

        }
        System.out.println("Valoare portofoliu: "+portofoliu.valoarePortofoliu());
        //scriere in fisier text a portofoliului
        try {
            salvarePortofoliu("./Portofoliu/Curente/PortofoliuInstrumente.csv",portofoliu);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //incarcare portofoliu din fisier text
        PortofoliuGenerics<Instrument> portofoliuDinText=new PortofoliuGenerics<>();

        try {
            portofoliuDinText=citirePortofoliu("./Portofoliu/Curente/PortofoliuInstrumente.csv");
            for(var entry : portofoliuDinText.getPortofoliu().entrySet()){
                System.out.print(entry.getValue().toString());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //scriere in fisier binar
        try {
            salvarePortofoliuBinar("./Portofoliu/Curente/PortofoliuInstrumente.dat",portofoliu);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
