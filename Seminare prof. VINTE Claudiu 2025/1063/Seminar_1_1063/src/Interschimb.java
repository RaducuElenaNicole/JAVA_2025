class Intreg {
    public int intreg;

    public Intreg (int intreg) {
        this.intreg = intreg;
    }
}

public class Interschimb {
    public static void interschimb_1(int a, int b) {
        int aux = a;
        a = b;
        b = aux;
    }

    public static void interschimb_2(Integer a, Integer b) {
        Integer aux = a;
        a = b;
        b = aux;
    }

    public static void interschimb_3(int[] vector) {
        int aux = vector[0];
        vector[0] = vector[1];
        vector[1] = aux;
    }

    public static void interschimb_4(Intreg a, Intreg b) {
        Intreg aux = new Intreg(a.intreg);
        a.intreg = b.intreg;
        b.intreg = aux.intreg;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Numar incorect de argumente in linia comanda!");
            System.out.println("Apel: Interschimb <arg_1> <arg_2>");
            System.exit(-1);
        }
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        System.out.printf("a=%d, b=%d\n", a, b);
        interschimb_1(a, b);
        System.out.printf("a=%d, b=%d\n", a, b);

        Integer x = a;
        Integer y = b;
        System.out.printf("x=%d, y=%d\n", x, y);
        interschimb_2(x, y);
        System.out.printf("x=%d, y=%d\n", x, y);

        int[] vector = new int[2];
        vector[0] = a; vector[1] = b;
        System.out.printf("a=%d, b=%d\n", vector[0], vector[1]);
        interschimb_3(vector);
        System.out.printf("a=%d, b=%d\n", vector[0], vector[1]);

        Intreg p = new Intreg(a);
        Intreg q = new Intreg(b);
        System.out.printf("p=%d, q=%d\n", p.intreg, q.intreg);
        interschimb_4(p, q);
        System.out.printf("p=%d, q=%d\n", p.intreg, q.intreg);

    }
}
