import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

final class Trotineta implements Comparable<Trotineta>, Serializable {
    private String id;
    private float distantaTotala;
    private float vitezaMedie;

    public Trotineta(String id, float distantaTotala, float vitezaMedie) {
        this.id = id;
        this.distantaTotala = distantaTotala;
        this.vitezaMedie = vitezaMedie;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Trotineta{");
        sb.append("id='").append(id).append('\'');
        sb.append(", distantaTotala=").append(distantaTotala);
        sb.append(", vitezaMedie=").append(vitezaMedie);
        sb.append('}');
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getDistantaTotala() {
        return distantaTotala;
    }

    public void setDistantaTotala(float distantaTotala) {
        this.distantaTotala = distantaTotala;
    }

    public float getVitezaMedie() {
        return vitezaMedie;
    }

    public void setVitezaMedie(float vitezaMedie) {
        this.vitezaMedie = vitezaMedie;
    }

    public int egalitateTrotinetaByDistanta(Trotineta t1, Trotineta t2){
        float diferentaDistanta = t1.distantaTotala - t2.distantaTotala;
        if(Math.abs(diferentaDistanta) < 10){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Trotineta trotineta = (Trotineta) o;
//        return Float.compare(distantaTotala, trotineta.distantaTotala) == 0
//                && Float.compare(vitezaMedie, trotineta.vitezaMedie) == 0
//                && Objects.equals(id, trotineta.id);

        return (Math.abs(this.distantaTotala - trotineta.distantaTotala) < 10);
    }

    @Override
    public int compareTo(Trotineta trotineta) {
        if((this.distantaTotala - trotineta.distantaTotala) >= 10){
            return 1;
        } else if ((this.distantaTotala - trotineta.distantaTotala) <= -10) {
            return -1;
        }else{
            return 0;
        }
    }

    // a < b => return -1
    // a > b => return 1
    // a == b => return 0
}

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("\n------------- Citire din fisier txt sub forma de dictionar (map)");

        Map<String, Trotineta> mapTrotinetaFisier = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src\\trotinete.txt"))){
            mapTrotinetaFisier = br.lines().map(x -> new Trotineta(
                    x.split(",")[0], // id
                    Float.parseFloat(x.split(",")[1]), // distanta
                    Float.parseFloat(x.split(",")[2]) // viteza
            )).collect(Collectors.toMap(Trotineta::getId, a -> a));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(var t: mapTrotinetaFisier.entrySet()){
            System.out.println(t.getValue());
        }

        System.out.println("\n------------- Citire fisier cu un camp Date ");

        List<Trotineta> listaTrotineta = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src\\trotinete2.txt"))){
            String linie = br.readLine();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            while(linie != null){
                String[] detaliiTrotineta = linie.split(",");

                String id = detaliiTrotineta[0];
                float distanta = Float.parseFloat(detaliiTrotineta[1]);
                float viteza = Float.parseFloat(detaliiTrotineta[2]);

                Trotineta troti = new Trotineta(id, distanta, viteza);
                listaTrotineta.add(troti);

                Date dataTrotiFisier = formatter.parse(detaliiTrotineta[3]);

                System.out.println("Data: " + dataTrotiFisier);

                linie = br.readLine();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for(var t:listaTrotineta){
            System.out.println(t.toString());
        }

        System.out.println("\n------------- Afisare trotiente care indeplinesc o conditie");

        for(var t: mapTrotinetaFisier.entrySet()){
            if(t.getValue().getVitezaMedie() > 50) {
                System.out.println(t.getValue());
            }
        }

        System.out.println("\n------------- Mapare trotinete By viteza");

        Map<Float, List<Trotineta>> mapTrotinetaByViteza = mapTrotinetaFisier.values()
                .stream().collect(Collectors.groupingBy(Trotineta::getVitezaMedie));
        for(var troti: mapTrotinetaByViteza.entrySet()){
            int nrTrotinete = troti.getValue().size();
            float sumaDistante = (float) troti.getValue().stream()
                    .mapToDouble(Trotineta::getDistantaTotala).sum();
            System.out.println("Viteza medie: " + troti.getKey()
                    + " -> Nr trotinete: " + nrTrotinete
                    + " | Suma distante parcurse: " + sumaDistante);
            for(var t: troti.getValue()){
                System.out.println("   " + t.toString());
            }
        }

        System.out.println("\n------------- Salvare fisier binar");

        try(ObjectOutputStream oos = new ObjectOutputStream
                (new FileOutputStream("src\\trotineteRapide.dat"))){
            for(var t: mapTrotinetaFisier.entrySet()){
                if(t.getValue().getVitezaMedie() > 14 && t.getValue().getVitezaMedie() <= 50) {
                    oos.writeObject(t.getValue());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Salvare detalii trotinete care indeplinesc o conditie intr-un fisier binar!");

        System.out.println("\n------------- Citire din fisier binar");

        try(ObjectInputStream ois = new ObjectInputStream
                (new FileInputStream("src\\trotineteRapide.dat"))){
            while(true){
                try{
                    Trotineta t = (Trotineta) ois.readObject();
                    System.out.println(t.toString());

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }catch(EOFException e){
                    break; // s-a terminat fisier => se iese fortat din while
                }
            }
        }

    }
}
