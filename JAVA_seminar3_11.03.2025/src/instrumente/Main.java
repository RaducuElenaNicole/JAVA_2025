package instrumente;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// clasa Main
public class Main {

    // metoda main
    public static void main(String[] args){
        Instrument e1 = new Instrument();

        Scanner scannerFisier = new Scanner(System.in);
        // CLICK DREAPTA PE MAIN => MORE RUN/DEBUG => MODIFY OPTIONS => BIFAT REDIRECT => ALEGERE FISIERUL TXT


        while(scannerFisier.hasNext()){
            String linie1 = scannerFisier.nextLine();

            Scanner scannerLinie = new Scanner(linie1);
            scannerLinie.useDelimiter("[\\,]+");
            String simbol = scannerLinie.next();
            scannerLinie.nextLine();

            String linie2 = scannerLinie.nextLine();
            scannerLinie = new Scanner(linie2);
            scannerLinie.useDelimiter("[\\,]+");

            List<Instrument.Operatiune> operatiuni = new ArrayList<>();

            while(scannerLinie.hasNext()){
                // operatiuni.add(new Instrument.Operatiune((TipOperatiune.valueOf(scannerLinie).ge)));
            }
        }
    }
}
