public class Subiect extends Object
{
    private int numarSubiect;
    private String enunt;

    public Subiect(int numarSubiect, String enunt) {
        this.numarSubiect = numarSubiect;
        this.enunt = enunt;
    }

    public int getNumarSubiect() {
        return numarSubiect;
    }

    public void setNumarSubiect(int numarSubiect) {
        this.numarSubiect = numarSubiect;
    }

    public String getEnunt() {
        return enunt;
    }

    public void setEnunt(String enunt) {
        this.enunt = enunt;
    }

    @Override
    public String toString() {
        return "Subiect {" +
                "numarSubiect = " + numarSubiect +
                " | enunt = '" + enunt + '\'' +
                '}';
    }
}
