public class Student {
    private String nume;
    private int varsta;
    private String adresa;
    private Persoana pers;

    public Student() {
    }

    public Student(String nume, int varsta, String adresa, Persoana pers) {
        this.nume = nume;
        this.varsta = varsta;
        this.adresa = adresa;
        this.pers = pers;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Persoana getPers() {
        return pers;
    }

    public void setPers(Persoana pers) {
        this.pers = pers;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", adresa='" + adresa + '\'' +
                ", pers=" + pers +
                '}';
    }
}
