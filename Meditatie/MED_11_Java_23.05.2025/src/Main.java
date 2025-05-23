import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        List<Carte> listaCartiTXT = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("carti.txt"))){
            String linie = br.readLine();
            while(linie != null){
                String[] detaliiCarte = linie.split("\t");

                String cota = detaliiCarte[0];
                String titlu = detaliiCarte[1];
                String autor = detaliiCarte[2];
                int anAparitie = Integer.parseInt(detaliiCarte[3]);

                Carte c = new Carte(cota, autor, titlu, anAparitie);
                listaCartiTXT.add(c);

                linie = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n--------------------");

        for(Carte c: listaCartiTXT){
            System.out.println(c.toString());
        }

        System.out.println("\n-------------------- Carti publicate inainte de 1940");
        List<Carte> listaCarti_1940 = new ArrayList<>();
        for(Carte c: listaCartiTXT){
            if(c.getAnAparitie() < 1940) {
                listaCarti_1940.add(c);
            }
        }
        listaCarti_1940.stream().sorted().forEach(c ->
                System.out.printf("%-10s %-30s %-25s %-4d \n",
                        c.getCota(), c.getTitlu(), c.getAutor(), c.getAnAparitie())
        );

        System.out.println("\n--------------------");

        List<Carte> listaCartiSotata = listaCartiTXT.stream()
                .filter(carte -> carte.getAnAparitie() < 1940)
                .sorted().toList();
        for(Carte c: listaCartiSotata){
            System.out.println(c.toString());
        }

        System.out.println("\n-------------------- Cerinta 1");

        for(Carte c: listaCartiSotata){
            System.out.printf("%-10s %-30s %-25s %-4d \n",
                    c.getCota(), c.getTitlu(), c.getAutor(), c.getAnAparitie());
        }

        System.out.println("\n-------------------- Citire din baza de date");
        List<Imprumut> listaCititori = new ArrayList<>();
        try(Connection conexiuneBD = DriverManager.getConnection("jdbc:sqlite:biblioteca.db")){
            Statement stm = conexiuneBD.createStatement();
            ResultSet rezultat = stm.executeQuery("select * from imprumuturi");

            while(rezultat.next()){
                listaCititori.add(new Imprumut(
                        rezultat.getString(1),
                        rezultat.getString(2),
                        rezultat.getInt(3)
                ));
            }

        }
        for(Imprumut imprumut: listaCititori){
            System.out.println(imprumut.toString());
        }

        System.out.println("\n-------------------- Cerinta 2");
        for(Imprumut imprumut: listaCititori){
            System.out.println(imprumut.getNumeCititor());
        }

        Map<String, Integer> numeCititoriTotalZile = new HashMap<>();

        System.out.println("\n-------------------- Cerinta 3 - afisarea numarului total de zile de imprumut per nume");
        Map<String, List<Imprumut>> numeCititori = listaCititori
                .stream().collect(Collectors.groupingBy(Imprumut::getNumeCititor));
        for(var cititor : numeCititori.entrySet()){
            int nrZileImprumut = cititor.getValue().stream()
                    .mapToInt(Imprumut::getNrZileImprumut).sum();
            System.out.println(cititor.getKey() + " - " +  nrZileImprumut);
            System.out.println("       " + cititor.getKey());

            // sortare ---- ????
            numeCititoriTotalZile.put(cititor.getKey(), nrZileImprumut);

            // salvare sortate sau sa le scriem direct in fisier sortate
        }
        System.out.println("\n-------------------- Varianta 2 ANA - sortata crescator");
        numeCititoriTotalZile.values().stream()
                .sorted((x, y) -> x.compareTo(y))
                .forEach(System.out::println);

        System.out.println("\n--------------------");
        listaCititori.stream().map(Imprumut::getNumeCititor).distinct()
                .forEach(System.out::println);

        System.out.println("\n-------------------- Varianta 1 ANA - sortata descrescator");
        Map<String, Integer> cititoriZileTotal = new HashMap<>();
        for(var cititor : listaCititori){
            cititoriZileTotal.put(cititor.getNumeCititor(),
                    cititoriZileTotal
                            .getOrDefault(cititor.getNumeCititor(), 0) + cititor.getNrZileImprumut());
        }
        List<Map.Entry<String, Integer>> listaSortata =
                cititoriZileTotal.entrySet().stream()
                        .sorted((a, b)
                                                    -> a.getValue() - b.getValue())
                        .collect(Collectors.toList()).reversed();
        for(var x : listaSortata){
            System.out.println(x.getKey() + " - " + x.getValue());
        }

        System.out.println("\n------------------");
        for(var x : numeCititoriTotalZile.entrySet()){
            System.out.println(x.getKey() + " - " + x.getValue());
        }

        System.out.println("\n-------------------- Solutie Ileana");
        Map<String, Integer> cititoriZileTotal2 = new HashMap<>();
        for(var cititor : numeCititori.entrySet()){
            int nrZileTotal = cititor.getValue().stream()
                    .mapToInt(Imprumut::getNrZileImprumut).sum();
            cititoriZileTotal2.put(cititor.getKey(), nrZileTotal);
        }
        for(var c : cititoriZileTotal2.entrySet()){
            System.out.println(c.getKey() + " - " + c.getValue());
        }

        System.out.println("\n-------------------- Solutie Ileana - sortare descrescatoare");
        System.out.printf("%-30s %-25s\n", "Nume cititor", "Numarul total de zile imprumtuare");
        cititoriZileTotal2.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(cititor
                        -> System.out.printf("%-30s %-3d\n", cititor.getKey(), cititor.getValue()));

    }
}