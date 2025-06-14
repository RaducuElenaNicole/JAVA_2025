package clase;

import java.util.List;

public class Candidat implements Comparable<Candidat>{
    private int codCandidat;
    private String nume;
    private float medie;
    private List<Optiune> listaOptiuni;
    private int nrOptiuni;

    public Candidat(int codCandidat, String nume, float medie, List<Optiune> listaOptiuni) {
        this.codCandidat = codCandidat;
        this.nume = nume;
        this.medie = medie;
        this.listaOptiuni = listaOptiuni;
    }

    public Candidat(int codCandidat, String nume, float medie, int nrOptiuni) {
        this.codCandidat = codCandidat;
        this.nume = nume;
        this.medie = medie;
        this.nrOptiuni = nrOptiuni;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("clase.Candidat{");
        sb.append("codCandidat=").append(codCandidat);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", medie=").append(medie);
        sb.append(", listaOptiuni = ").append(listaOptiuni.toString());
        sb.append('}');
        return sb.toString();
    }

    public String afiseasaCandidat(){
        final StringBuilder sb = new StringBuilder("clase.Candidat{");
        sb.append("codCandidat=").append(codCandidat);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", medie=").append(medie);
        sb.append(", nrOptiuni = ").append(nrOptiuni);
        sb.append('}');
        return sb.toString();
    }

    public int getCodCandidat() {
        return codCandidat;
    }

    public void setCodCandidat(int codCandidat) {
        this.codCandidat = codCandidat;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public float getMedie() {
        return medie;
    }

    public void setMedie(float medie) {
        this.medie = medie;
    }

    public List<Optiune> getListaOptiuni() {
        return listaOptiuni;
    }

    public void setListaOptiuni(List<Optiune> listaOptiuni) {
        this.listaOptiuni = listaOptiuni;
    }

    public int getNumarOptiuni(){
        return listaOptiuni.size();
    }

    public int getNrOptiuni() {
        return nrOptiuni;
    }

    public void setNrOptiuni(int nrOptiuni) {
        this.nrOptiuni = nrOptiuni;
    }

    @Override
    public int compareTo(Candidat candidat) {
        // a > b => 1
        // a < b => -1
        // a = b => 0

        if(this.getNumarOptiuni() > candidat.getNumarOptiuni()){
            return 1;
        } else if (this.getNumarOptiuni() < candidat.getNumarOptiuni()) {
            return -1;
        }else{
            if(this.getMedie() > candidat.getMedie()){
                return 1;
            } else if (this.getMedie() < candidat.getMedie()) {
                return -1;
            }
        }
        return 0;
    }
}
