import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class Profesor {
    private final int id_profesor;
    private final String prenume;
    private final String nume;
    private final String departament;

    public Profesor(int id_profesor, String prenume, String nume, String departament) {
        this.id_profesor = id_profesor;
        this.prenume = prenume;
        this.nume = nume;
        this.departament = departament;
    }

    public int getId_profesor() {
        return id_profesor;
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

    public String getNumeComplet()
    {return getNume()+" "+getPrenume();}


    @Override
    public String toString() {
        return "Profesor{" +
                "id_profesor=" + id_profesor +
                ", prenume='" + prenume + '\'' +
                ", nume='" + nume + '\'' +
                ", departament='" + departament + '\'' +
                '}';
    }
}

class Programare
{
    private final String ziua;
    private final String ora;
    private final Profesor profesor;
    private final String disciplina;
    private final String sala;
    private final boolean este_curs;
    private final String formatia;

    public Programare(String ziua, String ora, Profesor profesor,
                      String disciplina, String sala, boolean este_curs,
                      String formatia) {
        this.ziua = ziua;
        this.ora = ora;
        this.profesor = profesor;
        this.disciplina = disciplina;
        this.sala = sala;
        this.este_curs = este_curs;
        this.formatia = formatia;
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

    public boolean isEste_curs() {
        return este_curs;
    }

    public String getFormatia() {
        return formatia;
    }

    @Override
    public String toString() {
        return "Programare{" +
                "ziua='" + ziua + '\'' +
                ", ora='" + ora + '\'' +
                ", profesor=" + profesor +
                ", disciplina='" + disciplina + '\'' +
                ", sala='" + sala + '\'' +
                ", este_curs=" + este_curs +
                ", formatia='" + formatia + '\'' +
                '}';
    }
}


public class Orar {
    public static void main(String[] args) {
        Map<Integer, Profesor> profesori = new HashMap<>();
        List<Programare> programari = new ArrayList<>();
        try(var fisier_profesori = new BufferedReader(new FileReader("./dataIN/profesori.txt")))
        {
            profesori = fisier_profesori.lines()
                    .map(linie -> new Profesor(
                            Integer.parseInt(linie.split("\t")[0]),
                            linie.split("\t")[1],
                            linie.split("\t")[2],
                            linie.split("\t")[3]
                            ))
                    .collect(Collectors.toMap(Profesor::getId_profesor, Function.identity()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for(var entry : profesori.entrySet())
        {
            System.out.println(entry.getValue().toString());
        }

        try(var fisier_programari = new BufferedReader(new FileReader("./dataIn/programari.txt"))) {
            Map<Integer, Profesor> finalProfesori = profesori;
            programari = fisier_programari.lines()
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

        for(var programare: programari)
            System.out.println(programare.toString());

        // 2.1 Afișare lista cursuri în ordine alfabetică
        System.out.println("discipline in ordine alfabetica:");
        programari.stream()//colectie de programari
                .filter(Programare::isEste_curs)//colectie de programari
                .map(Programare::getDisciplina)//colectie de string-uri
                .distinct()
                .sorted()
                .forEach(System.out::println);

//        2.2 Afișare număr de activități pentru fiecare profesor
        System.out.printf("%35s %2s %2s%n","Profesor","C","S");
        programari.stream()
                .collect(Collectors.groupingBy(Programare::getProfesor)) //colectie de programari grupate dupa profesori
                .forEach((profesor,programariProfesor)  ->  {
                    System.out.printf("%35s %2d %2d%n",
                            profesor.getNumeComplet(),
                            programariProfesor.stream()
                                    .filter(Programare::isEste_curs)
                                    .count(),
                            programariProfesor.stream()
                                    .filter( programare -> !(programare.isEste_curs()))
                                    .count());
                });

//        2.3 Lista departamentelor ordonate descrescator dupa numărul de activități
        //declaram clasa interna Departament
        class Departament{
            String denumire;
            long nrActivitati;

            public Departament(String denumire, long nrActivitati) {
                this.denumire = denumire;
                this.nrActivitati = nrActivitati;
            }

            @Override
            public String toString() {
                return String.format("%-80s %d%n",denumire,nrActivitati);
            }
        }

        System.out.printf("%-80s %s%n","Denumire Departament","Numar activitati");
        List<Programare> finalProgramari = programari;
        programari.stream()
                .map( programare -> programare.getProfesor().getDepartament())
                .distinct()
                .map( denumire -> {
                    long nrActiv = finalProgramari.stream()
                            .filter( programare -> denumire.equals(programare.getProfesor().getDepartament()))
                            .count();
                    return new Departament(denumire,nrActiv);
                } )
                .sorted( (depA, depB)-> Long.compare(depB.nrActivitati, depA.nrActivitati))
                .forEach(System.out::print);

    }
}
