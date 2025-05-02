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

    }


}