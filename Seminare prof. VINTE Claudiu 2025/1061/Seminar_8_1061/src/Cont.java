public class Cont {
    private double sold;
    private boolean inOperare;

    public Cont(double sold) {
        this.sold = sold;
        this.inOperare=false;
    }
    public synchronized double depunere(long suma,String mesaj){
        while(this.inOperare){
            try {
                wait();//asteptare indefinita pana cand este primit un notifyAll
            } catch (InterruptedException e) {
               // throw new RuntimeException(e);
            }
        }
        this.inOperare=true;
        this.sold+=suma;
        System.out.println(mesaj+" depus cu succes "+ suma+", sold: "+this.getSold());
        this.inOperare=false;
        notifyAll();
        return this.getSold();
    }
    public synchronized double retragere(long suma,String mesaj){
        double rezultat=-1;
        while(this.inOperare){
            try {
                wait();//asteptare indefinita pana cand este primit un notifyAll
            } catch (InterruptedException e) {
                // throw new RuntimeException(e);
            }
        }
        this.inOperare=true;
        if(this.getSold()-suma>=0){
            this.sold-=suma;
            rezultat=this.getSold();
            System.out.println(mesaj+" retras cu succes "+ suma+", sold: "+this.getSold());
        }else{
            System.out.println(mesaj+" are fonduri insuficiente pentru a retrage suma "+suma);
        }
        this.inOperare=false;
        notifyAll();
        return rezultat;
    }


    public synchronized double getSold() {
        return sold;
    }
}
