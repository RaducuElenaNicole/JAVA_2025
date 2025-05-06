import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// RADUCU Elena-Nicole, an3, recuperare, 1098E -> Subiect test nr. 22 (06/05/2025) Grupa 1067E (unde recuperez)

final class Cabina implements Comparable<Cabina>, Serializable {
    private String cod;
    private int nrPatPerCabina;
    private float tarif;
    private int nrZile;

    public Cabina() {
        this.cod = "Necunoscut";
        this.nrPatPerCabina = 0;
        this.tarif = 0;
        this.nrZile = 0;
    }

    public Cabina(String cod, int nrPatPerCabina, float tarif, int nrZile) {
        this.cod = cod;
        this.nrPatPerCabina = nrPatPerCabina;
        this.tarif = tarif;
        this.nrZile = nrZile;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getNrPatPerCabina() {
        return nrPatPerCabina;
    }

    public void setNrPatPerCabina(int nrPatPerCabina) {
        this.nrPatPerCabina = nrPatPerCabina;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public int getNrZile() {
        return nrZile;
    }

    public void setNrZile(int nrZile) {
        this.nrZile = nrZile;
    }

    @Override
    public String toString() {
        return "Cabine{" +
                "cod='" + cod + '\'' +
                ", nrPatPerCabina=" + nrPatPerCabina +
                ", tarif=" + tarif +
                ", nrZile=" + nrZile +
                '}';
    }

    public float tarifPerPat(){
        return this.tarif/this.nrPatPerCabina;
    }


    @Override
    public int compareTo(Cabina cabina) {
        // a < b => -1 | a > b => 1 | a == b => 0
        if(this.tarifPerPat() < cabina.tarifPerPat()){
            return -1;
        }else if(this.tarifPerPat() > cabina.tarifPerPat()){
            return 1;
        }else {
            return 0;
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, Cabina> mapCabinaFisier = new HashMap<>();
        try(BufferedReader br = new BufferedReader
                (new FileReader("src\\cabineCroaziera.txt"))){
            mapCabinaFisier = br.lines().map(x -> new Cabina(
                    x.split(",")[0], // cod
                    Integer.parseInt(x.split(",")[1]), // nrPatPerCabina
                    Float.parseFloat(x.split(",")[2]), // tarif
                    Integer.parseInt(x.split(",")[3]) // nrZile
            )).collect(Collectors.toMap(Cabina::getCod, x -> x));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n---------------- Citire din fisier (afisarea tuturor cabinelor)");

        for(var c: mapCabinaFisier.entrySet()){
            System.out.println(c.getValue().toString());
        }

        System.out.println("\n---------------- Cabine cu procentul anual mai mic de 45% ------- CERINTA 2");

        for(var c: mapCabinaFisier.entrySet()){
            float procentAnual = (100 * c.getValue().getNrZile())/365;
            if(procentAnual < 45) {
                System.out.println(c.getValue().toString() + " | Procent anual = " + procentAnual);
            }
        }

        System.out.println("\n---------------- Mapare dupa numarul de paturi per cabina ------- CERINTA 3");

        Map<Integer, List<Cabina>> mapCabineByPaturi = mapCabinaFisier.values().stream()
                .collect(Collectors.groupingBy(Cabina::getNrPatPerCabina));
        for(var c: mapCabineByPaturi.entrySet()){
            int nrCabine = c.getValue().size();
            int nrZileOcupate = c.getValue().stream().mapToInt(Cabina::getNrZile).sum();
            int nrZileOcupateMedie = nrZileOcupate/nrCabine;

            System.out.println("Cabina cu " + c.getKey() + " paturi -> " + nrCabine + " cabine, " + nrZileOcupateMedie + " zile de ocupare medie pe an");

           // double nrZileOcupateM = c.getValue().stream().mapToInt(Cabina::getNrZile).average().getAsDouble();
        }

        System.out.println("\n---------------- Mapare dupa numarul de paturi per cabina - Afisarea cabinelor");

        for(var c: mapCabineByPaturi.entrySet()){
            int nrCabine = c.getValue().size();
            int nrZileOcupate = c.getValue().stream().mapToInt(Cabina::getNrZile).sum();
            int nrZileOcupateMedie = nrZileOcupate/nrCabine;

            System.out.println("Cabina cu " + c.getKey() + " paturi -> " + nrCabine + " cabine, " + nrZileOcupateMedie + " zile de ocupare medie pe an");

            for(var c2: c.getValue()){
                System.out.println("  " + c2.toString());
            }
        }

        System.out.println("\n---------------- Salvarea in fisier binar a cabinelor cu numarul de zile ocupate mai mare de 75% ------- CERINTA 4");

        try(ObjectOutputStream oos = new ObjectOutputStream
                (new FileOutputStream("src\\cabineProfitabile.dat"))){
            for(var c: mapCabinaFisier.entrySet()){

                float procentAnual = (100 * c.getValue().getNrZile())/365;

                if(procentAnual > 75) {
                    oos.writeObject(c.getValue());
                }
            }
        }

        System.out.println("\n---------------- Citire din fisier binar  ------- CERINTA 5");

        try(ObjectInputStream ois = new ObjectInputStream
                (new FileInputStream("src\\cabineProfitabile.dat"))){
            while(true){
                try{
                    Cabina c = (Cabina) ois.readObject();

                    System.out.println(c.toString());

                }catch (EOFException e){
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        System.out.println("\n---------------- Citire din fisier binar + Afisare procent anual");

        try(ObjectInputStream ois = new ObjectInputStream
                (new FileInputStream("src\\cabineProfitabile.dat"))){
            while(true){
                try{
                    Cabina c = (Cabina) ois.readObject();

                    float procentAnual = (100 * c.getNrZile())/365;
                    System.out.println(c.toString() + " | Procent anual: " + procentAnual);

                }catch (EOFException e){
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        System.out.println("\n---------------- Verificare Clasa Cabina");
        Cabina c1 = new Cabina();
        System.out.println("c1 -> " + c1.toString());

        Cabina c2 = new Cabina("CAB_2023",2,450.5f,193);
        System.out.println("c2 -> " + c2.toString() + " | Tarif per pat " + c2.tarifPerPat());

        Cabina c3 = new Cabina("CAB_2024",2,475.5f,185);
        System.out.println("c3 -> " + c3.toString() + " | Tarif per pat " + c3.tarifPerPat());

        System.out.println("Verificare compareTo() -> " + c2.compareTo(c3));

        c2.setTarif(550.5f);
        System.out.println("c2 -> " + c2.toString() + " | Tarif per pat " + c2.tarifPerPat());
        System.out.println("Verificare compareTo() -> " + c2.compareTo(c3));

        c2.setTarif(475.5f);
        System.out.println("c2 -> " + c2.toString() + " | Tarif per pat " + c2.tarifPerPat());
        System.out.println("Verificare compareTo() -> " + c2.compareTo(c3));
    }
}
