import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

final class NotaContabila implements Comparator<NotaContabila>,
        Comparable<NotaContabila>, Serializable {
    private String idNotaContabila;
    private Date dataOperatiunii; // format: dd.MM.yyyy
    private int contDebitor;
    private int contCreditor;
    private float suma;

    public NotaContabila(String idNotaContabila, Date dataOperatiunii,
                         int contDebitor, int contCreditor, float suma) {
        this.idNotaContabila = idNotaContabila;
        this.dataOperatiunii = dataOperatiunii;
        this.contDebitor = contDebitor;
        this.contCreditor = contCreditor;
        this.suma = suma;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NotaContabila{");
        sb.append("idNotaContabila='").append(idNotaContabila).append('\'');
        sb.append(", dataOperatiunii=").append(dataOperatiunii);
        sb.append(", contDebitor=").append(contDebitor);
        sb.append(", contCreditor=").append(contCreditor);
        sb.append(", suma=").append(suma);
        sb.append('}');
        return sb.toString();
    }

    public String getIdNotaContabila() {
        return idNotaContabila;
    }

    public void setIdNotaContabila(String idNotaContabila) {
        this.idNotaContabila = idNotaContabila;
    }

    public Date getDataOperatiunii() {
        return dataOperatiunii;
    }

    public void setDataOperatiunii(Date dataOperatiunii) {
        this.dataOperatiunii = dataOperatiunii;
    }

    public int getContDebitor() {
        return contDebitor;
    }

    public void setContDebitor(int contDebitor) {
        this.contDebitor = contDebitor;
    }

    public int getContCreditor() {
        return contCreditor;
    }

    public void setContCreditor(int contCreditor) {
        this.contCreditor = contCreditor;
    }

    public float getSuma() {
        return suma;
    }

    public void setSuma(float suma) {
        this.suma = suma;
    }


    @Override
    public int compare(NotaContabila nota1, NotaContabila nota2) {
        if(nota1.dataOperatiunii.compareTo(nota2.dataOperatiunii) == 1){
            return 1;
        }else if(nota1.dataOperatiunii.compareTo(nota2.dataOperatiunii) == -1){
            return -1;
        }else if(nota1.dataOperatiunii.compareTo(nota2.dataOperatiunii) == 0){
            return 0;
        }else{
            return -2;
        }
    }

    @Override
    public int compareTo(NotaContabila nota) {
        if(this.dataOperatiunii.before(nota.dataOperatiunii)){
            return 1;
        } else if (this.dataOperatiunii.after(nota.dataOperatiunii)) {
            return -1;
        } else if (this.dataOperatiunii.equals(nota.dataOperatiunii)) {
            return 0;
        }else{
            return -2;
        }
    }

    public int egalitateNotaContabila(NotaContabila nota1, NotaContabila nota2){
        if(nota1.contCreditor == nota2.contCreditor
                && nota1.contDebitor == nota2.contDebitor
                && nota1.suma == nota2.suma){
            return 1;
        }else{
            return 0;
        }
    }
}

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        List<NotaContabila> listaJurnal = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(
                new FileReader("src\\jurnal.csv"))){
            String linie;
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            linie = br.readLine();
            while(linie != null){
                String[] detaliiNotaContabilaFisier = linie.split(",");

                String idNotaContabila = detaliiNotaContabilaFisier[0];
                Date dataOperatiunii = formatter.parse(detaliiNotaContabilaFisier[1]);
                int contDebitor = Integer.parseInt(detaliiNotaContabilaFisier[2]);
                int contCreditor = Integer.parseInt(detaliiNotaContabilaFisier[3]);
                float suma = Float.parseFloat(detaliiNotaContabilaFisier[4]);

                NotaContabila nota = new NotaContabila(idNotaContabila, dataOperatiunii,
                        contDebitor, contCreditor, suma);
                listaJurnal.add(nota);

                linie = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n-------------------- Citire din fisier --------------------");

        float rulajTotal = 0;
        for(var nota: listaJurnal){
            rulajTotal = rulajTotal + nota.getSuma();
            System.out.println(nota);
        }
        System.out.println("Rulajul total = " + rulajTotal);

        System.out.println("\n--------------------  --------------------");

        Map<Integer, List<NotaContabila>> mapJurnalByDebitor = listaJurnal.stream()
                .collect(Collectors.groupingBy(NotaContabila::getContDebitor));
        for(var nota: mapJurnalByDebitor.entrySet()){
            float sumaRulajPerCheie = (float) nota.getValue().stream()
                    .mapToDouble(NotaContabila::getSuma).sum();
            System.out.println("Simbol cont (cont debitor): " + nota.getKey() + " - Rulaj: " + sumaRulajPerCheie);
            for(var detaliiNota: nota.getValue()) {
                System.out.println("   " + detaliiNota.toString());
            }
        }


    }
}
