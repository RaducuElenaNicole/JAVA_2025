
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

final class Angajat {
    private String nume;
    private String departament;
    private int salariu;

    public Angajat() {
    }

    public Angajat(String nume, String departament, int salariu) {
        this.nume = nume;
        this.departament = departament;
        this.salariu = salariu;
    }

    public String getNume() {
        return nume;
    }

    public String getDepartament() {
        return departament;
    }

    public int getSalariu() {
        return salariu;
    }


    @Override
    public String toString() {
        return "Angajat{" +
                "nume='" + nume + '\'' +
                ", departament='" + departament + '\'' +
                ", salariu=" + salariu +
                '}';
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, Angajat> listaAngajati = new HashMap<>();
        try(var fisier = new BufferedReader(new FileReader("angajati.txt"))){
            listaAngajati = fisier.lines().map(x -> new Angajat(
                    x.split(";")[0],
                    x.split(";")[1],
                    Integer.parseInt(x.split(";")[2])
            )).collect(Collectors.toMap(Angajat::getNume, x->x));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Map.Entry<String, Angajat> element: listaAngajati.entrySet()){
            System.out.println(element.getKey() + ": " + element.getValue());
        }

        long nrTotalAngajati = listaAngajati.values()
                .stream()
                .count();
        System.out.println("Nr total de angajati: " + nrTotalAngajati);

        Scanner scanner = new Scanner(System.in);
        String departamentCautat = scanner.nextLine();
        List<Angajat> angajatiCautati = listaAngajati.values()
                .stream()
                .filter(x -> x.getDepartament().equals(departamentCautat))
                .sorted(Comparator.comparing(Angajat::getSalariu).reversed())
                .collect(Collectors.toList());
        for(var angajat: angajatiCautati){
            System.out.println("Numele angajatului: " + angajat.getNume() +
                    " - salariul: " + angajat.getSalariu());
        }

        Map<String, List<Angajat>> mapDepartament = listaAngajati.values()
                .stream()
                .collect(Collectors.groupingBy(Angajat::getDepartament));
        for(var angajat: mapDepartament.entrySet()){
            System.out.println(angajat);
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("departamente.txt"))) {
            Map<String, List<Integer>> mapMediePerDepartament = listaAngajati
                    .values()
                    .stream()
                    .collect(Collectors.groupingBy(Angajat::getDepartament,
                            Collectors.mapping(Angajat::getSalariu, Collectors.toList())));
            for (var salariu : mapMediePerDepartament.entrySet()) {
                System.out.println(salariu);
            }
            mapMediePerDepartament.forEach((dep, salarii) -> {
                Double medie = salarii.stream().mapToInt(Integer::intValue).average().orElse(0);
                System.out.println("Departamentul " + dep + " - salariul mediu: " + medie);
                try{
                    bw.write("Departament: " + dep + " - Media salariala: " + medie);
                    bw.write(System.lineSeparator());
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            });
        }

        System.out.println("\n\n");

        Map<String, Double> medieSalariuPerDepartament = listaAngajati
                .values()
                .stream()
                .collect(Collectors.groupingBy(
                        Angajat::getDepartament,
                        Collectors.averagingInt(Angajat::getSalariu)
                ));
        medieSalariuPerDepartament.forEach((departament, medie) ->
                System.out.println("Departamentul " + departament + " - salariul mediu: " + medie));

        System.out.println("\n\n----- TAXA 10% -----\n");
        Map<String, List<Angajat>> listaCuTaxe = listaAngajati.values()
                .stream()
                .collect(Collectors.groupingBy(Angajat::getDepartament));
        for (Map.Entry<String, List<Angajat>> elm : listaCuTaxe.entrySet()) {
            double salariuNou = elm.getValue()
                    .stream()
                    .mapToDouble(x -> x.getSalariu() * 0.9)
                    .sum();
            System.out.println(elm.getKey() + ": " + salariuNou);
        }

    }

}