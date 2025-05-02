import java.io.Serializable;

public class Proiect implements Comparable, Serializable {
    private int cod;
    private String acronim;
    private String sef;
    private String departament;
    private double buget;
    private int nrMembrii;

    public Proiect(int cod, String acronim, String sef, String departament, double buget, int nrMembrii) {
        this.cod = cod;
        this.acronim = acronim;
        this.sef = sef;
        this.departament = departament;
        this.buget = buget;
        this.nrMembrii = nrMembrii;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Proiect{");
        sb.append("cod=").append(cod);
        sb.append(", acronim='").append(acronim).append('\'');
        sb.append(", sef='").append(sef).append('\'');
        sb.append(", departament='").append(departament).append('\'');
        sb.append(", buget=").append(buget);
        sb.append(", nrMembrii=").append(nrMembrii);
        sb.append('}');
        return sb.toString();
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getAcronim() {
        return acronim;
    }

    public void setAcronim(String acronim) {
        this.acronim = acronim;
    }

    public String getSef() {
        return sef;
    }

    public void setSef(String sef) {
        this.sef = sef;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public double getBuget() {
        return buget;
    }

    public void setBuget(double buget) {
        this.buget = buget;
    }

    public int getNrMembrii() {
        return nrMembrii;
    }

    public void setNrMembrii(int nrMembrii) {
        this.nrMembrii = nrMembrii;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        Proiect prt = (Proiect) obj;
        return cod == prt.cod;
    }

    @Override
    public int compareTo(Object obj) {
        Proiect prt = (Proiect) obj;
        if(this.buget > prt.buget){
            return 1;
        }else if(this.buget < prt.buget){
            return -1;
        }else
            return 0;
    }
}
