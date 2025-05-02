import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // obiect anonim
        BinaryOperator op = new BinaryOperator() {
            @Override
            public float calculeazaSuma(float a, float b) {
                return a * b;
            }
        };

        System.out.println(op.calculeazaSuma(5.5f, 7));

        BinaryOperator op2 = new Suma();
        System.out.println(op2.calculeazaSuma(5.5f, 7));

        // functie anonima (functie lambda)
        op = (a, b) -> a - b;
        System.out.println(op.calculeazaSuma(7, 3));

        op =(a, b) -> {
            float suma = a + b;
            float media = suma/2;
            return media;
        };
        System.out.println(op.calculeazaSuma(6, 10));

        Afisabil a = () -> {
            System.out.println("Mesaj 1");
        };
        a.afiseaza();

        AfisareMesaj am = (mesaj -> {
            System.out.println("Mesaj 2");
        });
        am.afiseazaMesaj("Ana are mere!");

        AfisareMesaj am1 = (m) ->
            System.out.println("Afiseaza mesajul: " + am);
        am1.afiseazaMesaj("Mesaj 3");

        OperatorUnar opU = (g) -> ++g;
        System.out.println(opU.calculeaza(10));

        List<Integer> ls = Arrays.asList(2, 5, 6, 9, 5, 7, 1, 3, 2, 1, 6, 9);
        int contor = (int) ls.stream().count();
        System.out.println(contor);

        int contor2 = 0;
        for(int i = 0; i < ls.size(); i++)
            contor2 = contor2 + 1;
        System.out.println(contor2);

        int contor3 = (int) ls.stream().distinct().count();
        System.out.println(contor3);

        int contor4 = (int) ls.stream().filter((x) -> x>=5).count();
        System.out.println(contor4);

        List<Integer> lsDistincta = ls.stream().distinct().collect(Collectors.toList());
        System.out.println(lsDistincta);

        List<Integer> lsPare = ls.stream().filter((x) -> x%2 == 0).distinct().collect(Collectors.toList());
        System.out.println(lsPare);

        List<Integer> lsImpare = ls.stream().distinct().filter((x) -> x%2 == 1).toList();
        System.out.println(lsImpare);

        System.out.println(ls.stream().sorted().toList());

        List<Integer> lsCresc = ls.stream().sorted().distinct().toList();
        System.out.println(lsCresc);

        List<Integer> lsFinal = ls.stream().distinct().filter((x) -> x%2 == 1).sorted().toList();
        System.out.println(lsFinal);

        lsFinal.stream().map(a1 -> a1 * a1).forEach(e -> System.out.println(e));

        System.out.println(lsFinal.stream().reduce((a2, b2) -> a2 - b2).get());

        List<String> lsCuvinte = Arrays.asList("Ana", "Maria", "Bianca", "Andrei", "Toma");
        System.out.println(lsCuvinte);

        List<String> lsCuvinteA = lsCuvinte.stream().filter(x -> x.startsWith("A")).collect(Collectors.toList());
        System.out.println(lsCuvinteA);

        String lsConcatenare = lsCuvinte.stream().collect(Collectors.joining(" | "));
        System.out.println(lsConcatenare);

        lsCuvinte.stream().filter(y -> y.length() > 4).forEach(System.out::println);


        Student s1 = new Student(1, "Mihai", 5.8f, 3, new float[]{6, 7, 8});
        Student s2 = new Student(2, "Mihaela", 9.8f, 4, new float[]{6, 7, 8, 3});
        Student s3 = new Student(3, "Oana", 3.68f, 5, new float[]{2, 6, 7, 8, 3});
        Student s4 = new Student(4, "Ioana", 2.8f, 3, new float[]{1, 2, 8});
        Student s5 = new Student(5, "Vlad", 9.8f, 2, new float[]{2, 8});

        List<Student> lsStud = new ArrayList<>();
        lsStud.add(s1);
        lsStud.add(s2);
        lsStud.add(s3);
        lsStud.add(s4);
        lsStud.add(s5);

        List<Student> lsStudSortat = lsStud.stream().sorted().toList();
        System.out.println(lsStudSortat);

        List<Student> lsStudSortatAlfabetic = lsStud.stream().sorted(Comparator.comparing(Student::getNume)).collect(Collectors.toList());
        System.out.println(lsStudSortatAlfabetic);
    }
}
