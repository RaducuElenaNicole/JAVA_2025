import javax.xml.crypto.Data;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Examen> listaExamene = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader("src\\examene.csv"))){
            String linie;
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            while((linie = br.readLine()) != null){
                String[] token = linie.split(",");
                Date data = formatter.parse(token[0]);
                String prof = token[1];
                String disciplina = token[2];
                int nrStudentiInscrisi = Integer.parseInt(token[3]);
                int nrStudentiExaminati = Integer.parseInt(token[4]);

                Examen exm = new Examen(data, prof, disciplina, nrStudentiInscrisi, nrStudentiExaminati);
                listaExamene.add(exm);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for(var exam: listaExamene){
            System.out.println(exam.toString() + " - " + exam.absenteism());
        }

        Map<String, List<Double> > absenteismPerDisciplina = new HashMap<>();
        for(var exam: listaExamene){
            absenteismPerDisciplina.computeIfAbsent(exam.getDisciplina(),
                    x -> new ArrayList<>()).add(exam.absenteism());
        }

        for(Map.Entry<String, List<Double>> elm: absenteismPerDisciplina.entrySet()){
            Double medie = elm.getValue()
                    .stream().mapToDouble(Double::doubleValue).average().getAsDouble();
            System.out.println(elm.getKey() + " - " + medie);
        }

        Map<String, List<Examen>> discipline = new HashMap<>();
        for(var exam: listaExamene){
            discipline.computeIfAbsent
                    (exam.getDisciplina(), x -> new ArrayList<>())
                    .add(exam);
        }

        for(Map.Entry<String, List<Examen>> elm: discipline.entrySet()){
            System.out.println(elm.getKey() + " - " + elm.getValue());
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("discipline.csv"))) {
            for (String disciplina : discipline.keySet()) {
                bw.write(disciplina + ":   \n");
                for (var examen : discipline.get(disciplina)) {
                    bw.write("    " + examen.getData() + " | " +
                            examen.getProfesor() + " | " +
                            examen.getNrStudentiExaminati() + "\n");
                }

            }
        }
    }
}
