import javax.management.DescriptorKey;

public class Imprumut implements Comparable<Imprumut> {
    private String numeCititor;
    private String cotaCarte;
    private int nrZileImprumut;

    public Imprumut(String numeCititor, String cotaCarte, int nrZileImprumut) {
        this.numeCititor = numeCititor;
        this.cotaCarte = cotaCarte;
        this.nrZileImprumut = nrZileImprumut;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Imprumut{");
        sb.append("numeCititor='").append(numeCititor).append('\'');
        sb.append(", cotaCarte='").append(cotaCarte).append('\'');
        sb.append(", nrZileImprumut=").append(nrZileImprumut);
        sb.append('}');
        return sb.toString();
    }

    public String getNumeCititor() {
        return numeCititor;
    }

    public void setNumeCititor(String numeCititor) {
        this.numeCititor = numeCititor;
    }

    public String getCotaCarte() {
        return cotaCarte;
    }

    public void setCotaCarte(String cotaCarte) {
        this.cotaCarte = cotaCarte;
    }

    public int getNrZileImprumut() {
        return nrZileImprumut;
    }

    public void setNrZileImprumut(int nrZileImprumut) {
        this.nrZileImprumut = nrZileImprumut;
    }

    @Override
    public int compareTo(Imprumut imprumut) {
        if(this.nrZileImprumut < imprumut.nrZileImprumut){
            return 1;
        } else if (this.nrZileImprumut > imprumut.nrZileImprumut) {
            return -1;
        }else{
            return 0;
        }
    }

}
