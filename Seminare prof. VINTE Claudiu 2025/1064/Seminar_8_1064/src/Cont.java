public class Cont {
    private double sold;
    private boolean inOperare;

    public Cont(double sold) {
        this.sold = sold;
        this.inOperare=false;
    }

    public synchronized double getSold() {
        return sold;
    }
    public double depunere(long suma, String mesaj)
    {
      while(this.inOperare)
      {
          try {
              wait();// asteptare indefinita pana la primirea
              //unei notificari
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
      }
      inOperare=true;
      sold+=suma;
      System.out.println(mesaj+" depus cu succes "
              +suma+ " Sold: "+getSold());
      inOperare=false;
      notifyAll();
      return getSold();
    }
    public synchronized double retragere(long suma, String mesaj)
    {
        while(this.inOperare)
        {
            try {
                wait();// asteptare indefinita pana la primirea
                //unei notificari
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        inOperare=true;
        double rezultat=-1;
        if(getSold()<suma)
        {
            System.out.println(mesaj + " fonduri" +
                    " insuficiente pentru retragerea " +
                    "sumei "+suma);
        }
        else
        {
            sold-=suma;
            rezultat=sold;
            System.out.println(mesaj + " retras cu succes suma "
                    +suma+ " Sold: "+getSold());
        }
        inOperare=false;
        notifyAll();
        return rezultat;
    }

}
