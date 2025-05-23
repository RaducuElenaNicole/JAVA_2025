public class Carte implements Comparable<Carte> {
    private String cota;
    private String autor;
    private String titlu;
    private int anAparitie;

    public Carte(String cota, String autor, String titlu, int anAparitie) {
        this.cota = cota;
        this.autor = autor;
        this.titlu = titlu;
        this.anAparitie = anAparitie;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Carte{");
        sb.append("cota='").append(cota).append('\'');
        sb.append(", autor='").append(autor).append('\'');
        sb.append(", titlu='").append(titlu).append('\'');
        sb.append(", anAparitie=").append(anAparitie);
        sb.append('}');
        return sb.toString();
    }

    public String getCota() {
        return cota;
    }

    public void setCota(String cota) {
        this.cota = cota;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public int getAnAparitie() {
        return anAparitie;
    }

    public void setAnAparitie(int anAparitie) {
        this.anAparitie = anAparitie;
    }

    @Override
    public int compareTo(Carte crt) {
        return this.titlu.compareTo(crt.getTitlu());
    }
}
