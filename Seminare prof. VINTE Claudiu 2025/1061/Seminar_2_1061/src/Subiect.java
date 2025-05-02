public class Subiect extends Object {
    private int numar;
    private String enunt;

    public Subiect() {
    }

    public Subiect(int numar, String enunt) {
        this.enunt = enunt;
        this.numar = numar;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public String getEnunt() {
        return enunt;
    }

    public void setEnunt(String enunt) {
        this.enunt = enunt;
    }

    @Override
    public String toString() {
        return "Subiect{" +
                "numar=" + numar +
                ", enunt='" + enunt + '\'' +
                '}';
    }
}
