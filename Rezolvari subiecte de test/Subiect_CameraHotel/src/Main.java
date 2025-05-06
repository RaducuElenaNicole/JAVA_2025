import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class Camera implements Comparable<Camera> {
    private String codCamera;
    private int nrPaturi;
    private float tarifPerNoapte;
    private int nrZileOcupatePerAn;

    public Camera(String codCamera, int nrPaturi, float tarifPerNoapte, int nrZileOcupatePerAn) {
        this.codCamera = codCamera;
        this.nrPaturi = nrPaturi;
        this.tarifPerNoapte = tarifPerNoapte;
        this.nrZileOcupatePerAn = nrZileOcupatePerAn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Camera{");
        sb.append("codCamera='").append(codCamera).append('\'');
        sb.append(", nrPaturi=").append(nrPaturi);
        sb.append(", tarifPerNoapte=").append(tarifPerNoapte);
        sb.append(", nrZileOcupatePerAn=").append(nrZileOcupatePerAn);
        sb.append('}');
        return sb.toString();
    }

    public String getCodCamera() {
        return codCamera;
    }

    public void setCodCamera(String codCamera) {
        this.codCamera = codCamera;
    }

    public int getNrPaturi() {
        return nrPaturi;
    }

    public void setNrPaturi(int nrPaturi) {
        this.nrPaturi = nrPaturi;
    }

    public float getTarifPerNoapte() {
        return tarifPerNoapte;
    }

    public void setTarifPerNoapte(float tarifPerNoapte) {
        this.tarifPerNoapte = tarifPerNoapte;
    }

    public int getNrZileOcupatePerAn() {
        return nrZileOcupatePerAn;
    }

    public void setNrZileOcupatePerAn(int nrZileOcupatePerAn) {
        this.nrZileOcupatePerAn = nrZileOcupatePerAn;
    }

    public float tarifPerPat(){
        return this.tarifPerNoapte / this.nrPaturi;
    }

    @Override
    public int compareTo(Camera cam) {
        // a < b => -1 | a > b => 1 | a = b => 0
        if(this.tarifPerNoapte < cam.tarifPerNoapte){
            return -1;
        } else if (this.tarifPerNoapte > cam.tarifPerNoapte) {
            return 1;
        }else {
            return 0;
        }
    }
}

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Map<String, Camera> mapCamere = new HashMap<>();
        try(BufferedReader br = new BufferedReader
                (new FileReader("src\\camereHotel.txt"))){
            mapCamere = br.lines().map(x -> new Camera(
                    x.split(",")[0], // cod camera
                    Integer.parseInt(x.split(",")[1]), // nr paturi
                    Float.parseFloat(x.split(",")[2]), // tarif per noapte
                    Integer.parseInt(x.split(",")[3]) // zile ocupate
            )).collect(Collectors.toMap(Camera::getCodCamera, x -> x));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(var c: mapCamere.entrySet()){
            System.out.println(c.toString());
        }

        System.out.println("\n--------------------");

        Map<Integer, List<Camera>> mapCamereByNrPaturi =
                mapCamere.values().stream()
                        .collect(Collectors.groupingBy(Camera::getNrPaturi));
        for(var c: mapCamereByNrPaturi.entrySet()){
            int nrCamere = c.getValue().size();
            int nrZile = c.getValue().stream()
                    .mapToInt(Camera::getNrZileOcupatePerAn).sum();
            int nrZileMediu = nrZile / nrCamere;
            System.out.println("Numar paturi camera: " + c.getKey()
                                + " -> Numar camere: " + nrCamere
                                + " | Numar de zile mediu: " + nrZileMediu);
            for(var c2: c.getValue()){
                System.out.println("   " + c2.toString());
            }
        }

        System.out.println("\n--------------------");

        // De facut salvarea in fisier binar si citirea din fisier binar 
    }
}
