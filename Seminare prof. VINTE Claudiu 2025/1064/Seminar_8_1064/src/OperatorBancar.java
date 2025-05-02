public class OperatorBancar extends Thread {
    private Cont cont;
    private String nume;
    private int timpPregatire;//milisecunde
    private long suma;

    public OperatorBancar(Cont cont, String nume,
                          int timpPregatire,
                          long suma) {
        this.cont = cont;
        this.nume = nume;
        this.timpPregatire = timpPregatire;
        this.suma = suma;
    }

    @Override
    public void run() {
        super.run();
        while(cont.getSold()>=this.suma)
        {
            try {
                sleep(this.timpPregatire);
                cont.retragere(this.suma,this.nume);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(this.nume + " a terminat" +
                " retragerile Sold: "+cont.getSold());

    }
}
