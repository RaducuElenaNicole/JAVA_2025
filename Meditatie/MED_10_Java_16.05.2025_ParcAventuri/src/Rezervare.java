class Rezervare{
    private int id_rezervare; // id_rezervare
    private int cod_aventura; // cod_aventura
    private int locuri_rezervate;

    public Rezervare(int id_rezervare, int cod_aventura, int locuri_rezervate) {
        this.id_rezervare = cod_aventura;
        this.cod_aventura = cod_aventura;
        this.locuri_rezervate = locuri_rezervate;
    }

    public int getId_rezervare() {
        return id_rezervare;
    }

    public int getCod_aventura() {
        return cod_aventura;
    }

    public int getLocuri_rezervate() {
        return locuri_rezervate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rezervare{");
        sb.append("cod_aventura='").append(id_rezervare).append('\'');
        sb.append(", denumire='").append(cod_aventura).append('\'');
        sb.append(", locuri_disponibile=").append(locuri_rezervate);
        sb.append('}');
        return sb.toString();
    }
}