public class MainOperareCont {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Numar incorect de parametri! \n" +
                    "Utilizare <sold> <nr. operatori>");
            System.exit(-1);
        }
        Cont cont=new Cont(Double.parseDouble(args[0]));
        int numarOperatori=Integer.parseInt(args[1]);
        System.out.println("Sold initial: "+cont.getSold());
        System.out.println("Numar operatori: "+numarOperatori);

        OperatorBancar[] operatori=new OperatorBancar[numarOperatori];
        for(int i=0;i<numarOperatori;i++){
            operatori[i]=new OperatorBancar(cont,"Operator "+(i+1),i*1000+1000,i*50+50);
            operatori[i].start();

        }
        for(int i=0;i<numarOperatori;i++){
            try {
                operatori[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Soldul final: "+cont.getSold());


//        TODO

    }
}
