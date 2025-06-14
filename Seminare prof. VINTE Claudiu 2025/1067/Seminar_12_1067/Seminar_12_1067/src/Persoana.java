public class Persoana {
    private String cnp;

    public Persoana() {
    }

    public Persoana(String cnp) {
        this.cnp = cnp;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "cnp='" + cnp + '\'' +
                '}';
    }
}
