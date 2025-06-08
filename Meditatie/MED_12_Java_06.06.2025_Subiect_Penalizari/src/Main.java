package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.json.*; // !!!!!

import static com.sun.management.HotSpotDiagnosticMXBean.ThreadDumpFormat.JSON;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Apartament> listaApartamente = new ArrayList<>();
        try(var fisier = new BufferedReader(
                new FileReader("D:\\FACULTATE\\RESTANTE\\2.2 JAVA recuperare\\GIT_seminare+medicatie_COD_TOT\\meditatie" +
                        "\\Subiect_Penalizari\\Subiect_Penalizari\\date\\intretinere_apartamente.txt"))){
            listaApartamente = fisier.lines().map(x -> new Apartament(
                    Integer.parseInt((x.split(",")[0])),
                    x.split(",")[1],
                    Float.parseFloat(x.split(",")[2])
            )).collect((Collectors.toList()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n------------------------------\n");
        for (var elm:listaApartamente){
            System.out.println(elm.toString());
        }

        System.out.println("\n\n------------------------------\n");
        int suma = 0;
        for (var elm:listaApartamente){
            suma += elm.getValoarePlata();
        }
        System.out.println("Val toata: " + suma);

        System.out.println("\n------------------------------\n");
        List<Apartament> listaSortata = listaApartamente.stream().sorted().collect(Collectors.toList()).reversed();
        for(var elm: listaSortata){
            System.out.println(elm);
        }

        List<Penalizare> listaPenalizari = new ArrayList<>();
        try(var fisier = new FileReader("D:\\FACULTATE\\RESTANTE\\2.2 JAVA recuperare" +
                "\\GIT_seminare+medicatie_COD_TOT\\meditatie\\Subiect_Penalizari\\Subiect_Penalizari" +
                "\\date\\penalizari.json")){

            var JSONArray = new JSONArray(new JSONTokener(fisier));
            for(int i = 0; i < JSONArray.length(); i++){

                var penalizareJSON = JSONArray.getJSONObject(i);
                Penalizare p = new Penalizare(penalizareJSON.getInt("numar_apartament"),
                        penalizareJSON.getFloat("penalizare"));
                listaPenalizari.add(p);
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n\n------------------------------\n");
        for(var elm:listaPenalizari){
            System.out.println(elm.toString());
        }

        System.out.println("\n\n------------------------------\n");
        for(var elmA:listaApartamente){
            for(var elmP:listaPenalizari){
                if(elmA.getNrAp() == elmP.getNrAp()){
                    float sumaTotala = elmA.getValoarePlata() + elmP.getValoare();
                    System.out.println(sumaTotala + " " + elmA.getNrAp() + " " + elmA.getNume());
                }

            }
        }

        System.out.println("\n\n------------------------------\n");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("tabel_intretinere.txt"))){
            for(var elm:listaApartamente){
                bw.write(String.valueOf(elm.getNrAp()));
                bw.write(elm.getNume());
                bw.write(String.valueOf(elm.getValoarePlata()));
                bw.write(System.lineSeparator());
            }
        }
    }
}
