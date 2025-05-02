import java.util.Scanner;


public class Main {
    public static Scanner scaner = new Scanner(System.in);

    public static void main(String[] args) {
       // Initializare obiect cu date din cod
        Examen e_1 = new Examen();
        e_1.setDisciplina("JAVA");
        e_1.setTip(Tip.ORAL);
        Subiect[] sub_1 = new Subiect[3];
        for (int i=0; i<sub_1.length; i++) {
            sub_1[i] = new Subiect(20+i+1,
                    "Subiect "+20+i+1);
        }
        e_1.setSubiecte(sub_1);
        System.out.println("Examen 1: " + e_1.toString());

        // Initializare obiect cu date preluate
        // de la tastatura
        Examen e_2 = new Examen();
//        System.out.println("Introduceti denumirea disciplinei:");
        e_2.setDisciplina(scaner.nextLine().trim());
//        System.out.println("Introduceti tipul examenului:");
        e_2.setTip(Tip.valueOf(scaner.nextLine().trim().toUpperCase()));
//        System.out.println("Introduceti numarul de subiecte:");
        int n = Integer.parseInt(scaner.nextLine().trim());
        Subiect[] sub_2 = new Subiect[n];
//        System.out.println("Introduceti subiecte: <numar> <enunt>");
        for (int i=0; i<n; i++) {
            sub_2[i] = new Subiect(scaner.nextInt(),
                    scaner.nextLine());
        }
        e_2.setSubiecte(sub_2);

        // Indirectare tastatura catre un fisier text
        System.out.println("Examen 2: " + e_2.toString());

    }
}
