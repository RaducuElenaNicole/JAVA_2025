import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class Profesor {
    private final int idProfesor;
    private final String prenume;

    private final String nume;
    private final String departament;

    public Profesor(int idProfesor, String prenume,
                    String nume, String departament) {
        this.idProfesor = idProfesor;
        this.prenume = prenume;
        this.nume = nume;
        this.departament = departament;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNume() {
        return nume;
    }

    public String getDepartament() {
        return departament;
    }

    public String getFullName(){
        return getNume() + " " + getPrenume();
    }
    @java.lang.Override
    public java.lang.String toString() {
        return "Profesor{" +
                "idProfesor=" + idProfesor +
                ", prenume='" + prenume + '\'' +
                ", nume='" + nume + '\'' +
                ", departament='" + departament + '\'' +
                '}';
    }
}
class Programare {
    private final String ziua;
    private final String ora;
    private final Profesor profesor;
    private final String disciplina;
    private final String sala;
    private final Boolean esteCurs;
    private final String formatie;

    public Programare(String ziua, String ora, Profesor profesor,
                      String disciplina, String sala, Boolean esteCurs,
                      String formatie) {
        this.ziua = ziua;
        this.ora = ora;
        this.profesor = profesor;
        this.disciplina = disciplina;
        this.sala = sala;
        this.esteCurs = esteCurs;
        this.formatie = formatie;
    }

    public String getZiua() {
        return ziua;
    }

    public String getOra() {
        return ora;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getSala() {
        return sala;
    }

    public Boolean getEsteCurs() {
        return esteCurs;
    }

    public String getFormatie() {
        return formatie;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Programare{" +
                "ziua='" + ziua + '\'' +
                ", ora='" + ora + '\'' +
                ", profesor=" + profesor +
                ", disciplina='" + disciplina + '\'' +
                ", sala='" + sala + '\'' +
                ", esteCurs=" + esteCurs +
                ", formatie='" + formatie + '\'' +
                '}';
    }
}


public class Orar {

    public static void main(String[] args) {
        Map<Integer, Profesor> profesori = new HashMap();
        List<Programare> programari;
        //Populare Map Profesor cu date din profesori.txt
        try (var fisierProfesori = new BufferedReader(
                new FileReader("./dataIN/profesori.txt"))) {
            profesori=fisierProfesori.lines() //Avem aici un stream de linii
                    .map(linie -> new Profesor(
                            Integer.parseInt(linie.split("\t")[0]),
                            linie.split("\t")[1],
                            linie.split("\t")[2],
                            linie.split("\t")[3]))
                    .collect(Collectors.toMap(Profesor::getIdProfesor,
                            Function.identity()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(var entry : profesori.entrySet()){
            System.out.println(entry.getValue().toString());
        }
        //Populare Lista Programari din fisierul programari.txt

        try(var fisierProgramari=new BufferedReader(
                new FileReader("./dataIN/programari.txt"))){
            Map<Integer, Profesor> finalProfesori = profesori;
            programari=fisierProgramari.lines()
                    .map(linie -> new Programare(
                            linie.split("\t")[0],
                            linie.split("\t")[1],
                            finalProfesori.get(Integer.parseInt(linie
                                    .split("\t")[2])),
                            linie.split("\t")[3],
                            linie.split("\t")[4],
                            Boolean.parseBoolean(linie.split("\t")[5]),
                            linie.split("\t")[6]))
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(var programare : programari){
            System.out.println(programare.toString());
        }
        //2.1 Afisare lista de cursuri in ordine alfabetica
        System.out.println("Cursurile listate in ordine alfabetica:");
        programari.stream()
                .filter(Programare::getEsteCurs)
                .map(Programare::getDisciplina)
                .distinct()
                .sorted()
                .forEach(System.out::println);

//        2.2 Afișare număr de activități pentru fiecare profesor

        //tiparim capul de tabela
        System.out.printf("%60s %2s %2s%n", "Nume Profesor", "C", "S" );
        programari.stream()
                .collect(Collectors.groupingBy(Programare::getProfesor))
                .forEach((profesor, programariProfesor)->{
                    System.out.printf("%60s %2d %2d%n",
                            profesor.getFullName(),
                            programariProfesor.stream()
                                    .filter(Programare::getEsteCurs)
                                    .count(),
                            programariProfesor.stream()
                                    .filter(p -> !p.getEsteCurs())
                                    .count()
                    );
                });

//        2.3 Lista departamentelor ordonate descrescator dupa numărul de activități
//        Utila clasa Departament cu atributele: (String) denumire, (long) numarActivitati.

        class Departament{
            String denumire;
            long nrActivitati;

            public Departament(String denumire, long nrActivitati) {
                this.denumire = denumire;
                this.nrActivitati = nrActivitati;
            }

            @Override
            public String toString() {
                return String.format("%-85s %4d %n", denumire, nrActivitati);
            }
        }
        System.out.printf("%-85s %s %n", "Denumire Departament", "Numar Activitati" );
        programari.stream()
                .map(programare -> programare.getProfesor().getDepartament())
                .distinct()  //este o multime de denumiri de departamente
                .map(denumire -> {
                    long nrActivitati = programari.stream()
                            .filter(programare -> programare.getProfesor()
                                    .getDepartament().equals(denumire)).count();
                    return new Departament(denumire, nrActivitati);
                })
                .sorted((d1, d2) -> Long.compare(d2.nrActivitati, d1.nrActivitati))
                .forEach(departament -> System.out.print(departament.toString()));
    }
}