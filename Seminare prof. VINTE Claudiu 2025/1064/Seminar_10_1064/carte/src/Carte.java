import java.io.Serializable;

public class Carte implements Serializable {
    private String cota;    // varchar(16)
    private String titlu;   // varchar(64)
    private String autori;  // varchar(64)
    private int an; // an aparitie (int)

    public Carte() {
        super();
    }

    public Carte(String cota, String titlu, String autori, int an) {
        super();
        this.cota = cota;
        this.titlu = titlu;
        this.autori = autori;
        this.an = an;
    }

    public String getCota() {
        return cota;
    }

    public void setCota(String cota) {
        this.cota = cota;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutori() {
        return autori;
    }

    public void setAutori(String autori) {
        this.autori = autori;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    @Override
    public String toString() {
//        return cota + "," +
//                titlu + "," +
//                autori + "," +
//                an;
        StringBuilder rezultat = new StringBuilder();
        String separator = "\t";
        rezultat.append(cota); rezultat.append(separator);
        rezultat.append(titlu); rezultat.append(separator);
        rezultat.append(autori); rezultat.append(separator);
        rezultat.append(an);
        return rezultat.toString();
    }
}
