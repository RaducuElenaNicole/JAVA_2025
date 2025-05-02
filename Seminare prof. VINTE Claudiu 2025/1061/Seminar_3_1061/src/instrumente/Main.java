package instrumente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String linie1;
        String linie2;
        Instrument i1 = new Instrument();
        while(scan.hasNext()){
            linie1 = scan.nextLine();
            Scanner scanLinie = new Scanner(linie1);
            scanLinie.useDelimiter("[\\,]+");
            i1.setSymbol(scanLinie.next());
            scanLinie.nextLine();
            linie2 = scan.nextLine();
            scanLinie = new Scanner(linie2);
            scanLinie.useDelimiter("[\\,]+");
            ArrayList<Instrument.Operatiune> operatiuni = new ArrayList<>();
            while(scanLinie.hasNext()){
                operatiuni.add(new Instrument.Operatiune(
                        TipOperatiune.valueOf(scanLinie.next()),
                        LocalDate.of(scanLinie.nextInt(),scanLinie.nextInt(),scanLinie.nextInt()),
                        Double.parseDouble(scanLinie.next()),
                        scanLinie.nextInt()
                ));
            }
            i1.setListaOperatiuni(operatiuni);
        }
        System.out.println(i1.toString());
    }
}
