import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

final class Examen implements Comparable<Examen>, Serializable {
   private Date dataExamen;
   private String profesor;
   private String disciplina;
   private int nrStudInscrisi;
   private int nrStudExaminati;

    public Examen(Date dataExamen, String profesor,
                  String disciplina, int nrStudInscrisi, int nrStudExaminati) {
        this.dataExamen = dataExamen;
        this.profesor = profesor;
        this.disciplina = disciplina;
        this.nrStudInscrisi = nrStudInscrisi;
        this.nrStudExaminati = nrStudExaminati;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Examen{");
        sb.append("dataExamen=").append(dataExamen);
        sb.append(", profesor='").append(profesor).append('\'');
        sb.append(", disciplina='").append(disciplina).append('\'');
        sb.append(", nrStudInscrisi=").append(nrStudInscrisi);
        sb.append(", nrStudExaminati=").append(nrStudExaminati);
        sb.append('}');
        return sb.toString();
    }

    public Date getDataExamen() {
        return dataExamen;
    }

    public void setDataExamen(Date dataExamen) {
        this.dataExamen = dataExamen;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public int getNrStudInscrisi() {
        return nrStudInscrisi;
    }

    public void setNrStudInscrisi(int nrStudInscrisi) {
        this.nrStudInscrisi = nrStudInscrisi;
    }

    public int getNrStudExaminati() {
        return nrStudExaminati;
    }

    public void setNrStudExaminati(int nrStudExaminati) {
        this.nrStudExaminati = nrStudExaminati;
    }

    @Override
    public int compareTo(Examen exam) {
        if(this.nrStudExaminati < exam.nrStudExaminati){
            return 1;
        } else if (this.nrStudExaminati > exam.nrStudExaminati) {
            return -1;
        }else {
            return 0;
        }
    }

    public float absenteism(){
        int nrStudNeexaminati = this.nrStudInscrisi - this.nrStudExaminati;
        return ((float) nrStudNeexaminati/this.nrStudInscrisi) * 100;
    }
}

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        List<Examen> listaExameneFisier = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(
                new FileReader("src\\examene.csv"))){
            String linie;
            linie = br.readLine();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            while(linie != null){
                String[] detaliiExameneFisier = linie.split(",");

                Date dataExamen = formatter.parse(detaliiExameneFisier[0]);
                String profesor = detaliiExameneFisier[1];
                String disciplina = detaliiExameneFisier[2];
                int nrStudInscrisi = Integer.parseInt(detaliiExameneFisier[3]);
                int nrStudExaminati = Integer.parseInt(detaliiExameneFisier[4]);

                Examen e = new Examen(dataExamen, profesor, disciplina,
                        nrStudInscrisi, nrStudExaminati);
                listaExameneFisier.add(e);

                linie = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n-------------------- Citire din fisier --------------------");

        int nrTotalExamene = 0;
        for(var exam: listaExameneFisier){
            nrTotalExamene++;
            System.out.println(exam.toString());
        }
        System.out.println("Numarul total de examene = " + nrTotalExamene);

        System.out.println("\n-------------------- Examene By Disiplina --------------------");

        Map<String, List<Examen>> mapExameneByDisciplina = listaExameneFisier.stream()
                .collect(Collectors.groupingBy(Examen::getDisciplina));

        for(var exam: mapExameneByDisciplina.entrySet()){
            double absenteismMediu = exam.getValue().stream()
                    .mapToDouble(Examen::absenteism).average().getAsDouble();
            System.out.println("Disciplina: " + exam.getKey() + " - Absenteism mediu: " + absenteismMediu);

            for(var e: exam.getValue()){
                System.out.println("  " + e.toString() + " - Absenteism: " + e.absenteism());
            }
        }

        System.out.println("\n-------------------- Examene By Disciplina - Salvare in fisier --------------------");

        try(BufferedWriter bw = new BufferedWriter(
                new FileWriter("src\\disciplina.csv"))){
            for(var exam: mapExameneByDisciplina.entrySet()){
                bw.write(exam.getKey());
                bw.write("\n");
                for(var e: exam.getValue()){
                    bw.write(e.toString() + "\n");
                }
                bw.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
