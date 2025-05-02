import java.io.Serializable;
import java.util.Arrays;

public class Comanda implements Serializable {
    public int nrComanda;
    public String numeClient;
    public float pret;
    public int nrProduse;
    public String[] numeProduse;

    public Comanda(int nrComanda, String numeClinet, float pret, int nrProduse, String[] numeProduse) {
        this.nrComanda = nrComanda;
        this.numeClient = numeClinet;
        this.pret = pret;
        this.nrProduse = nrProduse;
        this.numeProduse = numeProduse;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comanda { ");
        sb.append("nrComanda = ").append(nrComanda);
        sb.append(" | numeClient = '").append(numeClient).append('\'');
        sb.append(" | pret = ").append(pret);
        sb.append(" | nrProduse = ").append(nrProduse);
        sb.append(" | numeProduse = ").append(Arrays.toString(numeProduse));
        sb.append(" }");
        return sb.toString();
    }

    public void setNrComanda(int nrComanda) {
        this.nrComanda = nrComanda;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public void setNrProduse(int nrProduse) {
        this.nrProduse = nrProduse;
    }

    public void seteazaNumeByIndex(String numeProdus, int index) {
        if(index < nrProduse && index >= 0){
            this.numeProduse[index] = numeProdus;
        }else{
            this.numeProduse[index] = "Necunoscut/a";
        }
    }

    public void setNumeProduse(String[] numeProduse) {
        this.numeProduse = numeProduse;
    }
}
