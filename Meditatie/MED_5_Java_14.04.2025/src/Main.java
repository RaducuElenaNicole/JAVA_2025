import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, CameraHotel> camereMap = new HashMap<>();
        try(var fisier = new BufferedReader(new FileReader("src\\camereHotel.csv"))) {
            camereMap = fisier.lines().map(x -> new CameraHotel(
                    x.split(",")[0],
                    Integer.parseInt(x.split(",")[1]),
                    Float.parseFloat(x.split(",")[2]),
                    Integer.parseInt(x.split(",")[3])
            )).collect(Collectors.toMap(CameraHotel::getCodCamera, x -> x));


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } ;

        System.out.println("\n-------------------");
        for(Map.Entry<String, CameraHotel> cam: camereMap.entrySet()){
            System.out.println(cam.getKey() + " - " + cam.getValue());
        }

        System.out.println("\n-------------------");
        if(camereMap != null){
            camereMap.values()
                    .stream()
                    .filter(x -> x.getNrZileOcupate() > 365/2)
                    .forEach(System.out::println);
        }

        Map<String, CameraHotel> finalCamereMap = camereMap;
        camereMap.values()
                .stream()
                .collect(Collectors.groupingBy(
                        CameraHotel::getNrPaturi,
                        Collectors.averagingInt(CameraHotel::getNrZileOcupate)))
                .forEach((pat, medie) -> {
                    long contor = finalCamereMap.values().stream().filter(x -> x.getNrPaturi() == pat).count();
                    System.out.println("\n-------------------");
                    System.out.println("Camera cu " + pat + " paturi: " + contor + " si camere: " + medie + " zile de ocupare in medie pe an.");
                });

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("camereEficiente.dat"))) {
            List<CameraHotel> lista = camereMap.values().stream().filter(x -> x.getNrZileOcupate() > 365 * 0.7).toList();
            System.out.println("\n------------------- Fisierul camereEficiente.dat");
            for(var camera:lista){
                System.out.println(camera);
            }
            for(var camera: lista){
                oos.writeObject(camera);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List camereList = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("camereEficiente.dat"))){
            while (true) {
                try {
                    CameraHotel camera = (CameraHotel) ois.readObject();
                    camereList.add(camera);
                } catch (EOFException e) {
                    break; // s-a terminat fișierul
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n------------------- camereEficiente.dat");
        for(var camera: camereList){
            System.out.println(camera);
        }

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("camereEficienteLista.dat"))) {
            List<CameraHotel> lista = camereMap.values().stream().filter(x -> x.getNrZileOcupate() > 365 * 0.7).toList();
            System.out.println("\n------------------- Fisierul camereEficienteLista.dat");
            for(var camera:lista){
                System.out.println(camera);
            }
            oos.writeObject(lista);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<CameraHotel> lista = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("camereEficienteLista.dat"))){
            lista = (List<CameraHotel>)ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n------------------- camereEficienteLista.dat");
        for(var camera: lista){
            System.out.println(camera);
        }
    }
}
