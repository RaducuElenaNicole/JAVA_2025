package src;

public class Simbol implements Comparable<Simbol> {
    private String simbol;
    private double pretDeschidere;
    private double pretMax;
    private double pretMin;
    private double pretInchidere;
    private long volum;

    public Simbol(String simbol, double pretDeschidere,
                  double pretMax, double pretMin, double pretInchidere, long volum) {
        this.simbol = simbol;
        this.pretDeschidere = pretDeschidere;
        this.pretMax = pretMax;
        this.pretMin = pretMin;
        this.pretInchidere = pretInchidere;
        this.volum = volum;
    }

    public double getValoare(){
        return this.pretInchidere * this.volum;
    }

    public double getDiferenta(){
        return this.pretMax - this.pretMin;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("src.Simbol{");
        sb.append("simbol='").append(simbol).append('\'');
        sb.append(", pretDeschidere=").append(pretDeschidere);
        sb.append(", pretMax=").append(pretMax);
        sb.append(", pretMin=").append(pretMin);
        sb.append(", pretInchidere=").append(pretInchidere);
        sb.append(", volum=").append(volum);
        sb.append('}');
        return sb.toString();
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public double getPretDeschidere() {
        return pretDeschidere;
    }

    public void setPretDeschidere(double pretDeschidere) {
        this.pretDeschidere = pretDeschidere;
    }

    public double getPretMax() {
        return pretMax;
    }

    public void setPretMax(double pretMax) {
        this.pretMax = pretMax;
    }

    public double getPretMin() {
        return pretMin;
    }

    public void setPretMin(double pretMin) {
        this.pretMin = pretMin;
    }

    public double getPretInchidere() {
        return pretInchidere;
    }

    public void setPretInchidere(double pretInchidere) {
        this.pretInchidere = pretInchidere;
    }

    public long getVolum() {
        return volum;
    }

    public void setVolum(long volum) {
        this.volum = volum;
    }

    @Override
    public int compareTo(Simbol s) {
        // a > b => 1
        // a < b => -1
        // a == b => 0
        if(this.getValoare() > s.getValoare()){
            return 1;
        } else if (this.getValoare() < s.getValoare()) {
            return -1;
        }else {
            return 0;
        }
    }

    public int comparareByVolum(Simbol s){
        if(this.getVolum() > s.getVolum()){
            return 1;
        } else if (this.getVolum() < s.getVolum()) {
            return -1;
        }else {
            return 0;
        }
    }
}
