import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    public static void main(String[] args) throws FileNotFoundException {
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

        System.out.println("\n-------------");

        for(var t: mapTrotinetaFisier.entrySet()){
            System.out.println(t.getValue());
        }

        System.out.println("\n-------------");

        for(var t: mapTrotinetaFisier.entrySet()){
            if(t.getValue().getVitezaMedie() > 50) {
                System.out.println(t.getValue());
            }
        }

        System.out.println("\n-------------");

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

    }
}
