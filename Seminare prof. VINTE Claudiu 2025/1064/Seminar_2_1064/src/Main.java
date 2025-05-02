import java.util.Scanner;


public class Main {

    public static Scanner scaner = new Scanner(System.in);

    public static void main(String[] args) {
        Examen e1 = new Examen();
        e1.setDisciplina("JAVA");
        e1.setTip(Tip.ORAL);
        Subiect[] sub1 = new Subiect[3];
        for (int i=0; i<sub1.length; i++) {
            sub1[i] = new Subiect(20+i+1,
                    "Subiect "+20+i+1);
        }
        e1.setSubiecte(sub1);
        System.out.println("Examen 1:" + e1.toString());

        // Preluare valori de la tastatura
        Examen e2 = new Examen();
//        System.out.println("Introduceti numele disciplinei:");
        e2.setDisciplina(scaner.nextLine().trim());

//        System.out.println("Introduceti tipul examenului:");
        e2.setTip(Tip.valueOf(scaner.nextLine().trim().toUpperCase()));
//        System.out.println("Introduceti numarul de subiecte:");
        int n = Integer.parseInt(scaner.nextLine().trim());
        Subiect[] sub2 = new Subiect[n];
//        System.out.println("Introduceti subiect: <numar> <enunt>");
        for (int i=0; i<sub2.length; i++) {
            sub2[i] = new Subiect(scaner.nextInt(),
                    scaner.nextLine().trim());
        }
        e2.setSubiecte(sub2);
        System.out.println("Examen 2:" + e2.toString());
    }
}
