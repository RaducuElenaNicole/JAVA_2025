import java.io.Serializable;

public class CameraHotel implements Comparable<CameraHotel>, Serializable {
    private String codCamera;
    private int nrPaturi;
    private float tarif;
    private int nrZileOcupate;

    public CameraHotel(String codCamera, int nrPaturi, float tarif, int nrZileOcupate) {
        this.codCamera = codCamera;
        this.nrPaturi = nrPaturi;
        this.tarif = tarif;
        this.nrZileOcupate = nrZileOcupate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CameraHotel{");
        sb.append("codCamera='").append(codCamera).append('\'');
        sb.append(", nrPaturi=").append(nrPaturi);
        sb.append(", tarif=").append(tarif);
        sb.append(", nrZileOcupate=").append(nrZileOcupate);
        sb.append('}');
        return sb.toString();
    }

    public String getCodCamera() {
        return codCamera;
    }

    public void setCodCamera(String codCamera) {
        this.codCamera = codCamera;
    }

    public int getNrPaturi() {
        return nrPaturi;
    }

    public void setNrPaturi(int nrPaturi) {
        this.nrPaturi = nrPaturi;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public int getNrZileOcupate() {
        return nrZileOcupate;
    }

    public void setNrZileOcupate(int nrZileOcupate) {
        this.nrZileOcupate = nrZileOcupate;
    }

    float tarifPerPat(){
        float pretPerPat = this.tarif / this.nrPaturi;
        return pretPerPat;
    }

    @Override
    public int compareTo(CameraHotel cameraHotel) {
        if(this.tarifPerPat() > ((CameraHotel) cameraHotel).tarifPerPat()){
            return 1;
        } else if (this.tarifPerPat() < ((CameraHotel) cameraHotel).tarifPerPat()) {
            return -1;
        }else{
            return 0;
        }
    }

    public int comparaCamereHotelByTarifPerPat(CameraHotel cameraHotel){
        return Float.compare(this.tarifPerPat(), cameraHotel.tarifPerPat());
    }
}
