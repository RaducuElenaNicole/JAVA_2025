import java.util.Scanner;

public class Main {

    public static Scanner scaner = new Scanner(System.in);

    public static void main(String[] args) {
       Examen e1 = new Examen();
       e1.setDisciplina("Java");
       e1.setTipExamen(Tip.SCRIS);
       Subiect[] sub1 = new Subiect[3];
       for(int i=0; i<sub1.length; i++){
           sub1[i] = new Subiect(i + 1, "Enunt " + (20 + i + 1));
       }
       e1.setSubiecte(sub1);
       System.out.println("examen.txt 1: " + e1.toString());

       Examen e2 = new Examen();

       System.out.println("Introduceti numele disciplinei: ");
       e2.setDisciplina(scaner.nextLine().trim());

       System.out.println("Introduceti tipul examenului: ");
       e2.setTipExamen(
                       Tip.valueOf(
                                    scaner.nextLine().trim().toUpperCase()
                                  )
                      );


       System.out.println("Introduceti numarul de subiecte: ");
       int n = Integer.valueOf(scaner.nextLine().trim());
       Subiect[] sub2 = new Subiect[n];
       System.out.println("Introduceti subiectele: ");
       for(int  i=0; i<sub2.length; i++){
           sub2[i] = new Subiect(scaner.nextInt(), scaner.nextLine().trim());
       }
       e2.setSubiecte(sub2);
       System.out.println("examen.txt 2:" + e2.toString());
    }
}
