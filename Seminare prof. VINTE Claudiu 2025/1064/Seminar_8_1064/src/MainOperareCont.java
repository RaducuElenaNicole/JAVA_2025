public class MainOperareCont {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Numar incorect de parametri! \n" +
                    "Utilizare <sold> <nr. operatori>");
            System.exit(-1);
        }

//        TODO
        Cont cont = new Cont(Double.parseDouble(args[0]));
        int nrOperatori = Integer.parseInt(args[1]);
        System.out.println("Sold initial: "+cont.getSold());
        System.out.println("Numar operatori: "+nrOperatori);

        OperatorBancar[] operatori = new OperatorBancar[nrOperatori];

        for(int i=0;i<nrOperatori;i++)
        {
            operatori[i] = new OperatorBancar(cont,
                    "Operator "+(i+1),
                    i*10+1000,
                    i*50+50
                    );
            operatori[i].start();

        }
        for(int i=0;i<nrOperatori;i++)
            try {
                operatori[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        System.out.println("Sold final: "+cont.getSold());
    }
}
