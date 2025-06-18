package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        // CERINTA 1 ---------------------------------------
        // citire txt
        List<Simbol> listaSimboluriFromTXT = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("PretVolum.txt"))){
            br.readLine(); // sar peste header
            String linie = br.readLine(); // prima linie din fisier cu valori
            while(linie != null){
                String[] detaliiSimbol = linie.split(",");

                String simbol = detaliiSimbol[0];
                double pretDeschidere = Double.parseDouble(detaliiSimbol[1]);
                double pretMax = Double.parseDouble(detaliiSimbol[2]);
                double pretMin = Double.parseDouble(detaliiSimbol[3]);
                double pretInchidere = Double.parseDouble(detaliiSimbol[4]);
                long volum = Long.parseLong(detaliiSimbol[5]);

                Simbol simbolNou = new Simbol(simbol, pretDeschidere, pretMax, pretMin, pretInchidere, volum);
                listaSimboluriFromTXT.add(simbolNou);

                linie = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n-------------");
        for(var elm : listaSimboluriFromTXT){
            System.out.println(elm.toString());
        }

        System.out.println("\n-------------");
        Simbol simbolMax = listaSimboluriFromTXT.getFirst();
        double valoareMax = simbolMax.getValoare();

        Simbol simbolMin = listaSimboluriFromTXT.getFirst();
        double valoareMin = simbolMin.getValoare();

        for(var elm : listaSimboluriFromTXT){
            if(elm.getValoare() < valoareMin){
                valoareMin = elm.getValoare();
                simbolMin = elm;
            }
            if(elm.getValoare() > valoareMax){
                valoareMax = elm.getValoare();
                simbolMax = elm;
            }
        }

        System.out.println("Valoare tranzactionala mica -> Titlu: " + simbolMin.getSimbol()
                + " | Valoare: " + valoareMin);
        System.out.println("Valoare tranzactionala mare -> Titlu: " + simbolMax.getSimbol()
                + " | Valoare: " + valoareMax);

        System.out.println("\n------------- Lista sortata dupa valoare ");
        List<Simbol> listaSimboluriFromTXT_soratata = listaSimboluriFromTXT.stream()
                .sorted().toList();
        for(var elm : listaSimboluriFromTXT_soratata){
            System.out.println(elm.toString() + " -> Valoare: " + elm.getValoare());
        }
        System.out.println("Titlu: " + listaSimboluriFromTXT_soratata.getFirst().getSimbol() +
                " -> Valoare: " + listaSimboluriFromTXT_soratata.getFirst().getValoare());
        System.out.println("Titlu: " + listaSimboluriFromTXT_soratata.getLast().getSimbol() +
                " -> Valoare: " + listaSimboluriFromTXT_soratata.getLast().getValoare());

        // citire din bd
        System.out.println("\n-------------");
        List<Titlu> listaTitluriFromBD = new ArrayList<>();
        try(Connection conexiuneBD = DriverManager.getConnection("jdbc:sqlite:Titluri.db")){
            Statement stm = conexiuneBD.createStatement();
            ResultSet rezultat = stm.executeQuery("select * from Titluri");

            while(rezultat.next()){
                Titlu titluFromBd = new Titlu(rezultat.getString(1),
                        rezultat.getString(2));
                listaTitluriFromBD.add(titluFromBd);
            }
        }
        for(var elm : listaTitluriFromBD){
            System.out.println(elm.toString());
        }

        // CERINTA 2 --------------------------------------------
        System.out.println("\n-------------");
        List<Simbol> listaSimboluriFromTXT_soratataDescByVolum = listaSimboluriFromTXT.stream()
                .sorted(Comparator.comparing(Simbol::getVolum)).toList().reversed();
        for(var elm : listaSimboluriFromTXT_soratataDescByVolum){
            System.out.println(elm.toString());
        }
        System.out.println("\n-------------");
        for(var simbol : listaSimboluriFromTXT_soratataDescByVolum){
            String denumire = null;
            for(var titlu : listaTitluriFromBD){
                if(simbol.getSimbol().equals(titlu.getSimbol())){
                    denumire = titlu.getDenumire();
                    break;
                }
            }
            System.out.println("Simbol: " + simbol.getSimbol() + " | Denumire: "
            + denumire + " | Volum: " + simbol.getVolum());
        }

        // CERINTA 3 --------------------------------------------
        System.out.println("\n-------------");
        List<Simbol> listaSimbolFiltrata = listaSimboluriFromTXT.stream()
                .filter(simbol -> simbol.getDiferenta() > 0.01)
                .sorted(Comparator.comparingDouble(Simbol::getDiferenta))
                .toList().reversed();
        for(var elm : listaSimbolFiltrata){
            System.out.println(elm.toString() + " | Diferenta: " + elm.getDiferenta());
        }

        System.out.println("\n-------------");
        for(var simbol : listaSimbolFiltrata){
//            String denumire = String.valueOf(listaTitluriFromBD.stream()
//                                .filter(titlu -> simbol.getSimbol().equals(titlu.getSimbol()))
//                                .map(Titlu::getDenumire)
//                                .findFirst());
            String denumire = null;
            for(var titlu : listaTitluriFromBD){
                if(simbol.getSimbol().equals(titlu.getSimbol())){
                    denumire = titlu.getDenumire();
                    break;
                }
            }

            System.out.println("Simbol: " + simbol.getSimbol() + " | Denumire: " + denumire
                        + " | Diferenta: " + String.format("%.3f",simbol.getDiferenta()));
        }
    }
}
