package Subiect_5_16.src;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        // citirea din json imbricat
        System.out.println("\n-------------------------------------\n");
        List<Specialitate> listaSpecializari_FromJSON = new ArrayList<>();
        try(BufferedReader fisier = new BufferedReader
                (new InputStreamReader(new FileInputStream("D:\\FACULTATE\\RESTANTE\\2.2 JAVA recuperare" +
                        "\\subiecte examen java 2025\\subiecte\\Subiect_5_16\\Subiect_5_16\\medicale.json")))){

            JSONArray vectorSpecializari_JSON = new JSONArray(new JSONTokener(fisier));

            for(int i = 0; i < vectorSpecializari_JSON.length(); i++){
                var obiectSpecialitate_JSON = vectorSpecializari_JSON.getJSONObject(i);

                String denumire = obiectSpecialitate_JSON.getString("specialitate");

                List<Manevra> listaManevre = new ArrayList<>();
                var listaManevre_JSON = obiectSpecialitate_JSON.getJSONArray("manevre");

                for(int j = 0; j < listaManevre_JSON.length(); j++){
                    var obiectManevra_JSON = listaManevre_JSON.getJSONObject(j);

                    int cod = obiectManevra_JSON.getInt("cod");
                    int durata = obiectManevra_JSON.getInt("durata");
                    float tarif = obiectManevra_JSON.getInt("tarif");

                    Manevra manevra = new Manevra(cod, durata, tarif);
                    listaManevre.add(manevra);
                }

                Specialitate specialitate = new Specialitate(denumire, listaManevre);
                listaSpecializari_FromJSON.add(specialitate);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(var elm : listaSpecializari_FromJSON){
            System.out.println(elm.toString());
            System.out.print("   Numar manevre: " + (elm.getManevre().stream().count()) + " \n---\n");
        }


        // citire din bd
        List<Consultatie> listaConsultatiiFromBD = new ArrayList<>();
        try(Connection conexiuneBD = DriverManager.getConnection("jdbc:sqlite:" +
                "D:\\FACULTATE\\RESTANTE\\2.2 JAVA recuperare\\subiecte examen java 2025\\" +
                "subiecte\\Subiect_5_16\\Subiect_5_16\\consultatii.db")){
            Statement stm = conexiuneBD.createStatement();
            ResultSet rezultat = stm.executeQuery("select * from Consultatii");

            while(rezultat.next()){
                Consultatie consultatie = new Consultatie(
                       rezultat.getString(1),
                       rezultat.getInt(2),
                       rezultat.getInt(3));
                listaConsultatiiFromBD.add(consultatie);
            }
        }
        for(var elm : listaConsultatiiFromBD){
            System.out.println(elm.toString());
        }
    }
}
