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
    static void Serializare(PrintWriter fisier, Object obiect)
            throws IllegalAccessException {
        Class<?> clasa = obiect.getClass();
        fisier.println(clasa.getName()); //Student
        fisier.println(clasa.getDeclaredFields().length); //4
        for(Field field :clasa.getDeclaredFields())
        {
            field.setAccessible(true);
            fisier.println(field.getName());
            if(field.getType() == int.class || field.getType() == String.class)
            {
                fisier.println(field.get(obiect));
            }
            else
            {
                Serializare(fisier,field.get(obiect));
            }
        }

    }
    static Object Deserializare(Scanner scanner)
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchFieldException
    {
        String linie = scanner.nextLine();
        Class<?> clasa = Class.forName(linie);
        Object obiect = clasa.getDeclaredConstructor().newInstance(); //instanta de obiect de Student
        int nrAtribute  = Integer.parseInt(scanner.nextLine());
        for(int i=0;i<nrAtribute;i++)
        {
            String numeAtribut = scanner.nextLine();
            Field field = clasa.getDeclaredField(numeAtribut);
            String numeMetoda = "set" + field.getName().substring(0,1).toUpperCase()
                    +field.getName().substring(1);
            Method metoda = clasa.getDeclaredMethod(numeMetoda,field.getType()); //nume si parametru
            if(field.getType()==String.class)
            {
                String valoare = scanner.nextLine();
                metoda.invoke(obiect,valoare);
            }
            else if(field.getType()==int.class)
            {
                int valoare = Integer.parseInt(scanner.nextLine());
                metoda.invoke(obiect,valoare);
            }
            else
                metoda.invoke(obiect,Deserializare(scanner));

        }
        return obiect;
    }

    public static void main(String[] args) {
        List<Student> studenti = new ArrayList<>();
        studenti.add(new Student("Ion",17,"Str. Zambilelor",
                new Persoana("477049328123412")));
        studenti.add(new Student("Marian", 21 ,"Str. Florilor",
                new Persoana(("3454232242434434"))));

        studenti.stream()
                .forEach(System.out::println);

        String caleFisier = "./dataOut/fisierIesire.txt";

        try(PrintWriter fisier = new PrintWriter(caleFisier))
        {
            for(Student student : studenti)
                Serializare(fisier,student);
        } catch (FileNotFoundException | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }


        //deserilizare
        try(Scanner scaner = new Scanner(new File(caleFisier))) {
            System.out.println("\ndeserializare\n");
            while(scaner.hasNext())
            {
                Object obiect = Deserializare(scaner);
                System.out.println(obiect.toString());
            }
        } catch (FileNotFoundException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
