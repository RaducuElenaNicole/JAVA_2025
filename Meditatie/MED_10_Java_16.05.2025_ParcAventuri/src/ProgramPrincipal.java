import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ProgramPrincipal{
    public static void main(String[] args) throws IOException {
        // citire din JSON
        System.out.println("\n------------------------------------- Citire din fisier JSON -> AVENTURA");
        List<Aventura> listaAventura = new ArrayList<>();
        try(var fisier = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("Date\\aventuri.json")))){
            JSONArray vector = new JSONArray(new JSONTokener(fisier));

            listaAventura = StreamSupport.stream(vector.spliterator(), false)
                    .map(x -> (JSONObject)x)// cast la json obiect
                    .map(x -> new Aventura(
                            x.getInt("cod_aventura"),
                            x.getString("denumire"),
                            x.getFloat("tarif"),
                            x.getInt("locuri_disponibile"))).toList();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        for(var aventura: listaAventura){
            System.out.println(aventura.toString());
        }

        System.out.println("\n------------------------------------- CERINTA 1");
        System.out.println("Aventurile cu locurile disponibile >= 20");

        for(var aventura: listaAventura){
            if(aventura.getLocuri_disponibile() >= 20) {
                System.out.println(aventura.toString());
            }
        }

        System.out.println("\n------------------------------------- Citire fisier txt -> REZERVARE");
        List<Rezervare> listaRezervari = new ArrayList<>();
        try(var fisier = new BufferedReader(new FileReader("Date\\rezervari.txt"))){
            listaRezervari = fisier.lines().map(x -> new Rezervare(
                    Integer.parseInt(x.split(",")[0]),
                    Integer.parseInt(x.split(",")[1]),
                    Integer.parseInt(x.split(",")[2])
            )).toList();
        }
        for(var rezervare: listaRezervari){
            System.out.println(rezervare.toString());
        }

        System.out.println("\n------------------------------------- CERINTA 2");
        List<Rezervare> finalListaRezervari = listaRezervari;
        listaAventura.forEach(aventura -> {
            int nr_locuri_rezervate = finalListaRezervari.stream().filter(rezervare ->
                            rezervare.getCod_aventura() == aventura.getCod_aventura())
                    .mapToInt(Rezervare::getLocuri_rezervate).sum();

            int nr_locuri = aventura.getLocuri_disponibile() - nr_locuri_rezervate;
            if(nr_locuri >= 5){
                System.out.println("Cod aventura: " + aventura.getCod_aventura() +
                        " - Denumire: " + aventura.getDenumire() +
                        " - Locurile disponibile: " + nr_locuri);
            }
        });

        System.out.println("\n------------------------------------- Mapare rezervari by cod_aventura");
        Map<Integer, List<Rezervare>> mapRezervare = listaRezervari.stream()
                .collect(Collectors.groupingBy(Rezervare::getCod_aventura));
        for(var rezervare : mapRezervare.entrySet()){
            System.out.println("Cod_aventura: " + rezervare.getKey());
            for(var rezervare2 : rezervare.getValue()) {
                System.out.println("      " + rezervare2.toString());
            }
        }

        System.out.println("\n------------------------------------- Mapare rezervari by cod_aventura - var 2");
        Map<Integer, Integer> locuriRezervateTotal = listaRezervari.stream()
                .collect(Collectors.toMap(
                        Rezervare::getCod_aventura,
                        Rezervare::getLocuri_rezervate,
                        Integer::sum // daca cheia exista, se aduna
                ));
        for(var rezervare : locuriRezervateTotal.entrySet()){
            System.out.println("Cod_aventura: " + rezervare.getKey()
                    + " - Locuri rezervare total: " + rezervare.getValue().toString());
        }

        System.out.println("\n------------------------------------- CERINTA 3");
        listaAventura.forEach(aventura -> {
            int nr_locuri_rezervate_total = finalListaRezervari.stream()
                    .filter(rez -> rez.getCod_aventura() == aventura.getCod_aventura())
                    .mapToInt(Rezervare::getLocuri_rezervate).sum();
            int locuriRamase = aventura.getLocuri_disponibile() - nr_locuri_rezervate_total;
            if( locuriRamase >= 5){
                System.out.println("Cod_aventura: " + aventura.getCod_aventura() +
                        " - Denumire: " + aventura.getDenumire() +
                        " - Locuri disponibile ramase: " + locuriRamase);
            }
        });

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Rezultate\\venituri.txt"))) {
            listaAventura.forEach(avt -> {
                int nr_loc_rez_s = finalListaRezervari.stream().filter(rez -> rez.getCod_aventura() == avt.getCod_aventura())
                        .mapToInt(Rezervare::getLocuri_rezervate).sum();
                float tarifTotal = avt.getTarif() * nr_loc_rez_s;
                System.out.println(avt.getCod_aventura() + " " + (avt.getTarif() * nr_loc_rez_s));
                try {
                    bw.write(avt.getDenumire() + " ");
                    bw.write(nr_loc_rez_s + " ");
                    bw.write(String.valueOf(tarifTotal));
                    bw.write(System.lineSeparator());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }
}