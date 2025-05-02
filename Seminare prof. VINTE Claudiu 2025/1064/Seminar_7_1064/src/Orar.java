import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class Profesor {
    private final int codProfesor;
    private final String prenume;
    private final String nume;
    private final String departament;
    public Profesor(int codProfesor, String prenume,
                    String nume, String departament) {
        this.codProfesor = codProfesor;
        this.prenume = prenume;
        this.nume = nume;
        this.departament = departament;
    }

    public int getCodProfesor() {
        return codProfesor;
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

    public String getFullName() {return nume +" "+ prenume;}

    @Override
    public String toString() {
        return "Profesor{" +
                "codProfesor=" + codProfesor +
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
    private final boolean esteCurs;
    private final String formatie;

    public Programare(String ziua, String ora, Profesor profesor,
                      String disciplina, String sala, boolean esteCurs,
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

    public boolean isEsteCurs() {
        return esteCurs;
    }

    public String getFormatie() {
        return formatie;
    }

    @Override
    public String toString() {
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
        Map<Integer,Profesor> profesori = new HashMap<>();
        List<Programare> programari = new ArrayList<>();
        // incarcare date din fisiere
        // incarcare map profesori
        try (
                var fisierProfesori  = new BufferedReader(new
                        FileReader("./dataIN/profesori.txt"))
                )
        {
           profesori  = fisierProfesori.lines()
                   .map(linie -> new Profesor(
                           Integer.parseInt(linie.split("\t")[0]),
                           linie.split("\t")[1],
                           linie.split("\t")[2],
                           linie.split("\t")[3]
                   ))
                   .collect(Collectors.toMap(Profesor::getCodProfesor,
                           Function.identity()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (var entry :profesori.entrySet()
             ) {
            System.out.println(entry.getValue().toString());
        }
        // citire fisier de programari
        try (
                var fisierProgramari = new BufferedReader(new FileReader("./dataIN/programari.txt"))
                )
        {
            Map<Integer, Profesor> finalProfesori = profesori;
            programari = fisierProgramari.lines()
                    .map(linie -> new Programare(
                     linie.split("\t")[0],
                     linie.split("\t")[1],
                     finalProfesori.get(Integer.parseInt(linie.split("\t")[2])),
                     linie.split("\t")[3],
                     linie.split("\t")[4],
                     Boolean.parseBoolean(linie.split("\t")[5]),
                     linie.split("\t")[6]
                    ))
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        for (var programare: programari
             ) {
            System.out.println(programare.toString());
        }

        // Afișare lista cursuri în ordine alfabetică
        programari.stream()
                .filter(Programare::isEsteCurs)
                .map(Programare::getDisciplina)
                .distinct()
                .sorted()
                .forEach(System.out::println);
        // Afișare număr de activități pentru fiecare profesor
        System.out.printf("%35s %2s %2s %n","Profesor","C","S");

        programari.stream()
                .collect(Collectors.groupingBy(Programare::getProfesor))
                .forEach( (profesor,programariProfesor) ->
                        System.out.printf("%35s %2d %2d %n",
                                profesor.getFullName(),
                                programariProfesor.stream()
                                        .filter(Programare::isEsteCurs)
                                        .count(),
                                programariProfesor.stream()
                                        .filter(
                                                p -> !p.isEsteCurs()
                                        ).count()

                                )
                        );
        //2.3 Lista departamentelor ordonate descrescator dupa numărul de activități
        //    Utila clasa Departament cu atributele: (String) denumire, (long) numarActivitati.
        class Departament
        {
            String denumire;
            long nrActivitati;

            public Departament(String denumire, long nrActivitati) {
                this.denumire = denumire;
                this.nrActivitati = nrActivitati;
            }

            public String getDenumire() {
                return denumire;
            }

            public void setDenumire(String denumire) {
                this.denumire = denumire;
            }

            public long getNrActivitati() {
                return nrActivitati;
            }

            public void setNrActivitati(long nrActivitati) {
                this.nrActivitati = nrActivitati;
            }

            @Override
            public String toString() {
                return String.format("%75s %d %n",
                        denumire,nrActivitati
                        );
            }
        }
        System.out.printf("%75s %s %n","Departament","Numar Activitati");
        List<Programare> finalProgramari = programari;
        programari.stream()
                .map(programare -> programare.getProfesor().getDepartament())
                .distinct()
                .map(denumire ->
                {
                    long nrActivitati = finalProgramari.stream()
                            .filter(programare ->
                                denumire.equals(programare.getProfesor().getDepartament())
                            ).count();
                    return new Departament(denumire,nrActivitati);
                })
                .sorted((dep1,dep2) -> Long.compare(dep2.getNrActivitati(),
                        dep1.getNrActivitati()))
                .forEach(departament -> System.out.print(departament.toString()));



    }


}