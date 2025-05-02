package instrumente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Instrument e1 = new Instrument();
        Scanner scanner_fisier = new Scanner(System.in);

        while (scanner_fisier.hasNext())
        {
            String linie1 = scanner_fisier.nextLine();
            Scanner scanner_linie = new Scanner(linie1);
            scanner_linie.useDelimiter("[\\,]+");
            String simbol = scanner_linie.next();
            scanner_linie.nextLine();

            String linie2 = scanner_fisier.nextLine();
            scanner_linie = new Scanner(linie2);
            scanner_linie.useDelimiter("[\\,]+");

            List<Instrument.Operatiune> operatiuni = new ArrayList<>();

            while (scanner_linie.hasNext())
            {
                operatiuni.add(new Instrument.Operatiune(TipOperatiune.valueOf(scanner_linie.next()),
                        LocalDate.of(scanner_linie.nextInt(),scanner_linie.nextInt(),scanner_linie.nextInt()),
                        Double.valueOf(scanner_linie.next()),
                        Integer.valueOf(scanner_linie.next())));
            }
            e1.setSimbol(simbol);
            e1.setOperatiuni(operatiuni);
        }

        System.out.println(e1.toString());
    }
}
