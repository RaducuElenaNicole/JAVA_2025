import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static void Serializare(PrintWriter fisier, Object obj)
            throws IllegalAccessException {
        Class<?> clasa = obj.getClass();
        fisier.println(clasa.getName());
        fisier.println(clasa.getDeclaredFields().length);
        for (var atribut : clasa.getDeclaredFields())
        {
            atribut.setAccessible(true);
            fisier.println(atribut.getName());
            if(atribut.getType()==int.class
                    || atribut.getType()==String.class)
                fisier.println(atribut.get(obj));
            else Serializare(fisier,atribut.get(obj));
        }
    }
    static Object Deserializare(Scanner scanner)
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException,
            NoSuchFieldException {
        String linie = scanner.nextLine();
        Class<?> clasa = Class.forName(linie);
        Object obiect = clasa.getDeclaredConstructor().newInstance();
        int nrAtribute = Integer.parseInt(scanner.nextLine());
        for(int i=0;i<nrAtribute;i++)
        {
            linie = scanner.nextLine();
            Field camp = clasa.getDeclaredField(linie);
            String numeMetoda = "set"+camp.getName().substring(
                    0,1
            ).toUpperCase()+camp.getName().substring(1);
            Method metoda = clasa.getDeclaredMethod(numeMetoda,camp.getType());
            if(camp.getType()==String.class)
            {
                String valoare = scanner.nextLine();
                metoda.invoke(obiect,valoare);//apel pe obiect, parametru string

                // se apeleaza metodele setNume si setAdresa
            }
            else if (camp.getType()==int.class)
            {
                String valoare = scanner.nextLine();
                metoda.invoke(obiect,Integer.parseInt(valoare));
                // se apeleaza metoda de tip setVarsta
            }
            else
            {
                metoda.invoke(obiect,Deserializare(scanner));
                //se apeleaza setPersoana
            }
        }
      return  obiect;
    }

    public static void main(String[] args)
    {
        List<Student> listaStudenti = new ArrayList<>();
        listaStudenti.add(new Student(
                "Ion",17,"Str. Zambilelor",
                new Persoana("477049328123412")
        ));
        listaStudenti.add(new Student(
                "Marian",21,"Str. Florilor",
                new Persoana("3454232242434434")
        ));
//        listaStudenti.stream()
//                .forEach(System.out::println);
        String caleFisier ="./dateOutput/fisierIesire.txt";

        try (var fisier = new PrintWriter(caleFisier)) {
            listaStudenti.stream()
                    .forEach(student -> {
                        try {
                            Serializare(fisier,student);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (var scanner = new Scanner(new File(caleFisier))) {
            while(scanner.hasNext())
            {
                Object obiect = Deserializare(scanner);
                System.out.println(obiect.toString());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
