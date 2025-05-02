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
        //Afisare lista de cursuri in ordine alfabetica
        System.out.println("Cursurile listate in ordine alfabetica:");
        programari.stream()
                .filter(Programare::getEsteCurs)
                .map(Programare::getDisciplina)
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }
}