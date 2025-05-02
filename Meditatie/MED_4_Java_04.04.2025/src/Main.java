import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Proiect> listaProiecte = new ArrayList<>();
        try(var fisier = new BufferedReader(new FileReader("D:\\FACULTATE\\RESTANTE\\2.2 JAVA recuperare\\meditatie\\MED_4_Java_04.04.2025\\src\\proiecte.csv"))){
            listaProiecte = fisier.lines().map(linie -> new Proiect(
                    Integer.parseInt(linie.split(",")[0]),
                    linie.split(",")[1],
                    linie.split(",")[2],
                    linie.split(",")[3],
                    Double.parseDouble(linie.split(",")[4]),
                    Integer.parseInt(linie.split(",")[5])
            )).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(var lst:listaProiecte){
            System.out.println(lst.toString());
        }

        double bugetTotal = 0;
        for(var lst:listaProiecte){
            bugetTotal = bugetTotal + lst.getBuget();
        }
        System.out.println("Bugetul total: " + bugetTotal);

        double bugetTotal2 = listaProiecte.stream().mapToDouble(Proiect::getBuget).sum();
        System.out.println("Bugetul total: " + bugetTotal2);

        Map<String, List<String>> departamente = listaProiecte.stream().collect(Collectors.groupingBy(Proiect::getDepartament, Collectors.mapping(Proiect::getAcronim, Collectors.toList())));
        departamente.forEach((departament, acronime) ->
                System.out.println(departament + ": " + acronime) );

        listaProiecte.sort((a, b) -> b.getNrMembrii() - a.getNrMembrii());
        for(var lst:listaProiecte){
            System.out.println(lst.toString());
        }

        try(BufferedWriter bw = new BufferedWriter((new FileWriter("src\\valoare_proiecte.txt")))){
            for(var lst:listaProiecte){
                bw.write(lst.getCod() + ",");
                bw.write(lst.getAcronim() + ",");
                bw.write(lst.getBuget() + ",");
                bw.write(lst.getNrMembrii() + "\n");
            }
        }

        try(ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream("src\\proiecte.dat"))){
            ous.writeObject(listaProiecte);
        }

        List<Proiect>colectieProiecte=new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src\\proiecte.dat"))){
             colectieProiecte = (List<Proiect>) ois.readObject();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Map<Integer,String> mapProiecte=colectieProiecte.stream().collect(Collectors.toMap(Proiect::getCod, p -> p.getAcronim() + " | " + p.getBuget()));
        System.out.println(mapProiecte);
    }
}
