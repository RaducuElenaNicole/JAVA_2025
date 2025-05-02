import java.util.Scanner;


public class CitireTastatura {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        scanner.useDelimiter("[\\s,\\t,\\,]+");
        while(scanner.hasNext()) {
            String nume = scanner.next();
//            int varsta = scanner.nextInt();
            int varsta = Integer.parseInt(scanner.next());
            System.out.printf("Nume=%s, varsta=%d", nume, varsta);
        }

    }
}
