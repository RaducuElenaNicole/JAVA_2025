package clase;

import java.util.List;

public class Liceu implements Comparable<Liceu>{
    private int codLiceu;
    private String numeLiceu;
    private List<Specializare> listaSpecializari;

    public Liceu(int codLiceu, String numeLiceu, List<Specializare> listaSpecializari) {
        this.codLiceu = codLiceu;
        this.numeLiceu = numeLiceu;
        this.listaSpecializari = listaSpecializari;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("clase.Liceu{");
        sb.append("codLiceu=").append(codLiceu);
        sb.append(", numeLiceu='").append(numeLiceu).append('\'');
        sb.append(", listaSpecializari=").append(listaSpecializari.toString());
        sb.append('}');
        return sb.toString();
    }

    public int getCodLiceu() {
        return codLiceu;
    }

    public void setCodLiceu(int codLiceu) {
        this.codLiceu = codLiceu;
    }

    public String getNumeLiceu() {
        return numeLiceu;
    }

    public void setNumeLiceu(String numeLiceu) {
        this.numeLiceu = numeLiceu;
    }

    public List<Specializare> getListaSpecializari() {
        return listaSpecializari;
    }

    public void setListaSpecializari(List<Specializare> listaSpecializari) {
        this.listaSpecializari = listaSpecializari;
    }

    public int getNumarTotalLocuri(){
        int nrTotalLocuri = 0;
        for(int i = 0; i < listaSpecializari.size(); i++){
            nrTotalLocuri += listaSpecializari.get(i).getNumarLocuri();
        }
        return nrTotalLocuri;
    }

    @Override
    public int compareTo(Liceu lic) {
        // a > b => 1
        // a < b => -1
        // a = b => 0
        if(this.getNumarTotalLocuri() > lic.getNumarTotalLocuri()){
            return 1;
        }else if(this.getNumarTotalLocuri() < lic.getNumarTotalLocuri()){
            return -1;
        }else{
            return 0;
        }
    }
}
