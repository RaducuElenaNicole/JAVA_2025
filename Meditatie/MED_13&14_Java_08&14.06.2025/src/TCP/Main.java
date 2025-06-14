package TCP;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clase.Candidat;
import clase.Liceu;
import clase.Optiune;
import clase.Specializare;
import org.json.*; // TREBUIE jar !

public class Main {
    public static List<Candidat> listaCandidati = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // citire din JSON
        System.out.println("\n-------------------------------------\n");

        try(var fisier = new BufferedReader(
                new InputStreamReader(new FileInputStream("DateIntrare\\canditati.json")))){

                    JSONArray vectorCandidati_JSON = new JSONArray(new JSONTokener(fisier));

                    for(int i =0; i < vectorCandidati_JSON.length(); i++){

                        List<Optiune> listaOptiuni = new ArrayList<>();
                        var jsonCandidat = vectorCandidati_JSON.getJSONObject(i);

                        int codCandidat = jsonCandidat.getInt("cod_candidat");
                        String nume = jsonCandidat.getString("nume_candidat");
                        float medie = jsonCandidat.getFloat("media");
                        var listaOptiune_JSON = jsonCandidat.getJSONArray("optiuni");

                        for(int j = 0; j < listaOptiune_JSON.length(); j++){
                            var jsonOptiune = listaOptiune_JSON.getJSONObject(j);

                            int codLiceu = jsonOptiune.getInt("cod_liceu");
                            int codSpecializare = jsonOptiune.getInt("cod_specializare");

                            Optiune opt = new Optiune(codLiceu, codSpecializare);
                            listaOptiuni.add(opt);
                        }

                        Candidat candidat = new Candidat(codCandidat, nume, medie, listaOptiuni);
                        listaCandidati.add(candidat);
                    }

                } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        for(var elm:listaCandidati){
            System.out.println(elm.toString());
        }

        System.out.println("\n\n-------------------------------------\n");
        List<Liceu> listaLicee_TXT = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("DateIntrare\\licee.txt"))){

            String linie1 = br.readLine(); // pt liceu
            String linie2 = br.readLine(); // pt specializare

            while(linie1 != null && linie2 != null){
                String[] detaliiLiceu = linie1.split(",");
                String[] detaliiSpecializare = linie2.split(",");

                int codLiceu = Integer.parseInt(detaliiLiceu[0]);
                String numeLiceu = detaliiLiceu[1];
                int nrSpecializari = Integer.parseInt(detaliiLiceu[2]);

                List<Specializare> listaSpecializare_TXT = new ArrayList<>();

                for(int i = 0; i < nrSpecializari; i++){
                    int codSpecializare = Integer.parseInt(detaliiSpecializare[0]);
                    int numarLocuri = Integer.parseInt(detaliiSpecializare[1]);

                    Specializare specializare = new Specializare(codSpecializare, numarLocuri);
                    listaSpecializare_TXT.add(specializare);
                }

                Liceu liceu = new Liceu(codLiceu, numeLiceu, listaSpecializare_TXT);
                listaLicee_TXT.add(liceu);

                linie1 = br.readLine();
                linie2 = br.readLine();
            }
        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        for(var elm:listaLicee_TXT){
            System.out.println(elm.toString());
        }

        System.out.println("\n\n-------------------------------------\n");
        int nrCanditatiByMedie = 0;
        for(var elm:listaCandidati){
            if(elm.getMedie() >= 9){
                nrCanditatiByMedie++;
            }
        }
        System.out.println("Numarul de candidati cu medii >= 9: " + nrCanditatiByMedie);

        System.out.println("\n\n-------------------------------------\n");
        System.out.println("Numarul total de locuri \n");
        for(var elm:listaLicee_TXT) {
            int nrLocuriPerLiceu = elm.getNumarTotalLocuri();
            System.out.println("Liceul " + elm.toString() + " \n - Nr locuri: " + nrLocuriPerLiceu);
        }

        System.out.println("\n\n-------------------------------------\n");
        List<Liceu> listaLicee_sortata = listaLicee_TXT.stream()
                .sorted().collect(Collectors.toList()).reversed();
        for(var elm:listaLicee_sortata){
            System.out.println("Cod: " + elm.getCodLiceu() + " | Nume: " + elm.getNumeLiceu()
            + " | Nr total locuri: " + elm.getNumarTotalLocuri());
        }

        System.out.println("\n\n-------------------------------------\n");
        List<Candidat> listaCandidati_sortata = listaCandidati.stream()
                .sorted().collect(Collectors.toList()).reversed();
        for(var elm:listaCandidati_sortata){
            System.out.println("Cod: " + elm.getCodCandidat() + " | Nume: " + elm.getNume() +
                    " | Numar optiuni: " + elm.getNumarOptiuni() +
                    " | Medie admitere: " + elm.getMedie());
        }

        System.out.println("\n\n-------------------------------------\n");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("DateIesire\\jurnal.txt"))){
            for(var elm : listaCandidati_sortata){
                bw.write(String.valueOf(elm.getCodCandidat()) + " ");
                bw.write(elm.getNume() + " ");
                bw.write(String.valueOf(elm.getNumarOptiuni()) + " ");
                bw.write(String.valueOf(elm.getMedie()) + " ");
                bw.write(System.lineSeparator());
            }
        }

        // scrierea in BD
        String URL = "jdbc:sqlite:DateIesire\\scriereBD.db";
        try{
            Connection connection = DriverManager.getConnection(URL);
            System.out.println("Conexiunea a pornit!");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS CANDIDATI " +
                    "(codCandidat integer PRIMARY KEY, nume text, medie float, nrOptiuni integer)");
            for(var candidat : listaCandidati){
                statement.execute("insert or ignore into CANDIDATI values (" + candidat.getCodCandidat() + ", '" +
                        candidat.getNume() + "', " +
                        candidat.getMedie() + ", " +
                        candidat.getNumarOptiuni() + ")");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // citire din BD
        List<Candidat> listaCandidatiFromBD = new ArrayList<>();
        try {
            Connection connectionCitireDinBD = DriverManager.getConnection(URL);
            System.out.println("Conexiunea a fost creata cu succes!");
            Statement statementCitireDinBD = connectionCitireDinBD.createStatement();
            var result = statementCitireDinBD.executeQuery("SELECT * FROM CANDIDATI");
            while(result.next()){
                int codCandidat = result.getInt("codCandidat");
                String nume = result.getString("nume");
                float medie = result.getFloat("medie");
                int nrOptiuni = result.getInt("nrOptiuni");

                Candidat candidatFromBD = new Candidat(codCandidat, nume, medie, nrOptiuni);
                listaCandidatiFromBD.add(candidatFromBD);
            }

            for(var elm:listaCandidatiFromBD){
                System.out.println(elm.afiseasaCandidat());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // TCP

        // TCP - pornesc serverul pe un thread separat
        new Thread(() -> {
            try {
                ServerTCP.server_main();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        // aștept puțin ca serverul să se inițializeze
        try {
            Thread.sleep(1000); // 1 secundă
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // pornesc clientul
        try {
            ClientTCP.main();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //ServerTCP.server_main();
        //ClientTCP.main();
    }
}
