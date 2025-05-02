class Intreg {
    public int intreg;

    public Intreg(int intreg) {
        this.intreg = intreg;
    }
}

public class Interschimb {
    public static void interschimb_1(int a, int b) {
        int aux = a;
        a = b;
        b = aux;
    }

    public static void interschimb_2(int[] vector) {
        int aux = vector[0];
        vector[0] = vector[1];
        vector[1] = aux;
    }

    public static void interschimb_3(Intreg a, Intreg b) {
        Intreg aux = new Intreg(a.intreg);
        a.intreg = b.intreg;
        b.intreg = aux.intreg;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Numar gresit de parametri!");
            System.out.println("Apel Interschimb <par1> <par2>");
            System.exit(-1);
        }
        int a = Integer.valueOf(args[0]);
        int b = Integer.valueOf(args[1]);

        System.out.printf("a=%d, b=%d\n", a, b);
        interschimb_1(a, b);
        System.out.printf("a=%d, b=%d\n", a, b);

        int[] vector = new int[]{11, 7};
        System.out.printf("a=%d, b=%d\n", vector[0], vector[1]);
        interschimb_2(vector);
        System.out.printf("a=%d, b=%d\n", vector[0], vector[1]);

        Intreg p = new Intreg(a);
        Intreg q = new Intreg(b);
        System.out.printf("p=%d, q=%d\n", p.intreg, q.intreg);
        interschimb_3(p, q);
        System.out.printf("p=%d, q=%d\n", p.intreg, q.intreg);
    }
}
