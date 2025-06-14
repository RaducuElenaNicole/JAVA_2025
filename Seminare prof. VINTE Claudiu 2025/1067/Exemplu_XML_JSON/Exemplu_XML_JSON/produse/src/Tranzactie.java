class Tranzactie {
    private final TipTranzactie tip;
    private final int cantitate;

    public Tranzactie(TipTranzactie tip, int cantitate) {
        this.tip = tip;
        this.cantitate = cantitate;
    }

    public TipTranzactie getTip() {
        return tip;
    }

    public int getCantitate() {
        return cantitate;
    }

    public int getDeltaStoc() {
        return cantitate * tip.getSemn();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tranzactie{");
        sb.append("tip=").append(tip);
        sb.append(", cantitate=").append(cantitate);
        sb.append('}');
        return sb.toString();
    }
}
