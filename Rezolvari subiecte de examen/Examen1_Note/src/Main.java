import org.json.JSONArray;
import org.json.JSONTokener;
import org.xml.sax.XMLFilter;

import javax.sql.rowset.spi.XmlWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static List<Note> listaStudetiTXT = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        List<Student> listaStudentiJSON = new ArrayList<>();
        try(var fisier = new FileReader("DateIntrare\\s11_studenti.json")) {
            var JSONArray = new JSONArray(new JSONTokener(fisier));
            for (int i = 0; i < JSONArray.length(); i++) {
                var unObiectDeTipJSON = JSONArray.getJSONObject(i);
                int id = unObiectDeTipJSON.getInt("IdStudent");
                String nume = unObiectDeTipJSON.getString("Nume");
                String prenume = unObiectDeTipJSON.getString("Prenume");
                Student s = new Student(id, nume, prenume);
                listaStudentiJSON.add(s);
            }
        }

        System.out.println("Lista studenti JSON");

        int count = 0;
        for(var elm: listaStudentiJSON){
            count++;
            System.out.println(elm);
        }
        System.out.println("Numarul total de studenti: " + count);


        try(var fisier = new BufferedReader(new FileReader("DateIntrare\\s11_note.txt"))){
            listaStudetiTXT = fisier.lines().map(x -> new Note(
                    Integer.parseInt(x.split(",")[0]),
                    x.split(",")[1],
                    Double.parseDouble(x.split(",")[2])
            )).collect(Collectors.toList());
        }

        for(var elm : listaStudetiTXT){
            System.out.println(elm);
        }


        System.out.println("Lista Disciplice: ---------");
        Map<String, List<Note>> listaNote= listaStudetiTXT.stream().collect(Collectors.groupingBy(Note::getMaterie));
        for(var elm : listaNote.entrySet()){
            int nrMaterii = elm.getValue().size();
            System.out.println(elm.getKey() + " - " + nrMaterii);
        }

//        System.out.println("Introduceti materia pentru care vreti sa alfati nota: ");
//        Scanner scanner = new Scanner(System.in);
//        String materie = scanner.nextLine();
//        for(var elmS : listaStudentiJSON){
//            for(var elmN : listaStudetiTXT){
//                if(elmN.getIdStudent() == elmS.getIdStudent()){
//                    if(Objects.equals(elmN.getMaterie(), materie)){
//                        System.out.println(elmS.getNume() + " " + elmS.getPrenume() + " " + elmN.getNota());
//                    }
//                }
//            }
//        }

//        new Thread(()->{
//            try {
//                ServerTCP.main();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//
//        ClientTCP.main();


//        new Thread(()->{
//            try {
//                ServerTCPMultithreading.main();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//
//       ClientTCPMultithreading.main();


       // scriere XML
        try{
            var documentBuilderFactory = DocumentBuilderFactory.newInstance();
            var builder = documentBuilderFactory.newDocumentBuilder();
            var document = builder.newDocument();

            var radacina = document.createElement("note");
            document.appendChild(radacina);
            for(var elm : listaStudetiTXT){
                var nod = document.createElement("nota");
                radacina.appendChild(nod);
                var idStud = document.createElement("idStudent");
                idStud.setTextContent(String.valueOf(elm.getIdStudent()));
                nod.appendChild(idStud);
                var materie = document.createElement("materie");
                materie.setTextContent(elm.getMaterie());
                nod.appendChild(materie);
                var nota = document.createElement("nota");
                idStud.setTextContent(String.valueOf(elm.getNota()));
                nod.appendChild(nota);

            }

            var trasnformerFsctory = TransformerFactory.newInstance();
            var transformer = trasnformerFsctory.newTransformer();

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}