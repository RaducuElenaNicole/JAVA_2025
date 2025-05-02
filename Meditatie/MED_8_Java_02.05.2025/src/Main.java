import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class Achizitie implements Comparable<Achizitie>, Serializable{
    private String codProdus;
    private int an;
    private int luna;
    private int zi;
    private int cantitateAchizitionata;
    private float pretUnitar;

    // constructor default
    public Achizitie() {
    }
    // constructor cu parametri pentru initializare
    public Achizitie(String codProdus, int an, int luna, int zi, int cantitateAchizitionata, float pretUnitar) {
        this.codProdus = codProdus;
        this.an = an;
        this.luna = luna;
        this.zi = zi;
        this.cantitateAchizitionata = cantitateAchizitionata;
        this.pretUnitar = pretUnitar;
    }
    // toString()
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Achizitie{");
        sb.append("codProdus='").append(codProdus).append('\'');
        sb.append(", an=").append(an);
        sb.append(", luna=").append(luna);
        sb.append(", zi=").append(zi);
        sb.append(", cantitateAchizitionata=").append(cantitateAchizitionata);
        sb.append(", pretUnitar=").append(pretUnitar);
        sb.append('}');
        return sb.toString();
    }
    // metode de acces
    public String getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(String codProdus) {
        this.codProdus = codProdus;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public int getLuna() {
        return luna;
    }

    public void setLuna(int luna) {
        this.luna = luna;
    }

    public int getZi() {
        return zi;
    }

    public void setZi(int zi) {
        this.zi = zi;
    }

    public int getCantitateAchizitionata() {
        return cantitateAchizitionata;
    }

    public void setCantitateAchizitionata(int cantitateAchizitionata) {
        this.cantitateAchizitionata = cantitateAchizitionata;
    }

    public float getPretUnitar() {
        return pretUnitar;
    }

    public void setPretUnitar(float pretUnitar) {
        this.pretUnitar = pretUnitar;
    }

    // alte cerinte/metode
    public float valoare(){
        return this.pretUnitar * this.cantitateAchizitionata;
    }

    @Override
    public int compareTo(Achizitie a) {
        float valoare1 = this.valoare();
        float valoare2 = a.valoare();

        if(valoare1 < valoare2){
            return -1;
        } else if (valoare1 > valoare2) {
            return 1;
        }else{
            return 0;
        }
    }
}

public class Main{
    public static void main(String[] args) throws IOException {
        List<Achizitie> listaAchizitii = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader("src\\achizitii.txt"))){
            String linie;
            while((linie = br.readLine()) != null){
                String[] token = linie.split(",");

                String codProdus = token[0];
                int an = Integer.parseInt(token[1]);
                int luna = Integer.parseInt(token[2]);
                int zi = Integer.parseInt(token[3]);
                int cantitateAchizitionata = Integer.parseInt(token[4]);
                float pretUnitar = Float.parseFloat(token[5]);

                Achizitie achi = new Achizitie(codProdus, an, luna, zi, cantitateAchizitionata, pretUnitar);
                listaAchizitii.add(achi);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-------------------------------");

        for(var achi: listaAchizitii){
            System.out.println(achi.toString() + " - Valoare achizitie: " + achi.valoare());
        }

        System.out.println("-------------------------------");

        for(var achi: listaAchizitii){
            if(achi.getZi() <= 15 && achi.getCantitateAchizitionata() > 100) {
                System.out.println(achi.toString() + " - Valoare achizitie: " + achi.valoare());
            }
        }

        System.out.println("-------------------------------");
        // ordine descrescatoare
        Map<String, List<Achizitie>> mapAchizitii = listaAchizitii.stream()
                .collect(Collectors.groupingBy(Achizitie::getCodProdus));
        for(var achi: mapAchizitii.entrySet()){
            long achizitiiNumarTotal = achi.getValue().size();
            float totalValoareAchizitii = (float) achi.getValue().stream()
                                            .mapToDouble(Achizitie::valoare).sum();
            System.out.println("Produs " + achi.getKey() + " -> Achizitii: "
                    + achizitiiNumarTotal + " | Valoarea totala: " + totalValoareAchizitii);
        }

        System.out.println("-------------------------------");

        try(ObjectOutputStream oos = new ObjectOutputStream
                (new FileOutputStream("produseFrecvente.dat"))){
//            for(var achi: mapAchizitii.entrySet()){
//                long achizitiiNumarTotal = achi.getValue().size();
//                if(achizitiiNumarTotal > 3){
//                    oos.writeObject(achi.getValue());
//                }
//            }
            for(Map.Entry<String, List<Achizitie>> achi: mapAchizitii.entrySet()){
                if(achi.getValue().size() > 3){
                    for(Achizitie a: achi.getValue()){
                        oos.writeObject(a);
                    }
                }
            }
        }

        System.out.println("-------------------------------");

       // List<Achizitie> listaAchizitiiFisierBinar = new ArrayList<>();
        //FileInputStream f = new FileInputStream("produseFrecvente.dat");
        try(ObjectInputStream ois = new ObjectInputStream
                (new FileInputStream("produseFrecvente.dat"))){
            while(true){
                try{
                Achizitie achi = (Achizitie) ois.readObject();
               // listaAchizitiiFisierBinar.add(achi);

                System.out.println(achi);
                }catch(EOFException e){
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Achizitie> frecvente = listaAchizitii.stream()
                .collect(Collectors.groupingBy(Achizitie::getCodProdus))
                .values().stream()
                .filter(l -> l.size() > 3).flatMap(List::stream).toList();

        try(ObjectOutputStream oos = new ObjectOutputStream
                (new FileOutputStream("produseFrecvente1.dat"))){
            oos.writeObject(frecvente);
        }


        System.out.println("-------------------------------");

        try(ObjectInputStream ois = new ObjectInputStream
                (new FileInputStream("produseFrecvente1.dat"))){
            List<Achizitie> listaAchi = (List<Achizitie>) ois.readObject();
            for(var a: listaAchi){
                System.out.println(a.toString());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}