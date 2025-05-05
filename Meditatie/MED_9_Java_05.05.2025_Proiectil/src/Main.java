import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

final class Proiectil implements Comparable<Proiectil>, Serializable {
    private int coordonataX;
    private int coordonataY;
    private double masaExploziva;

    public Proiectil(int coordonataX, int coordonataY, double masaExploziva) {
        this.coordonataX = coordonataX;
        this.coordonataY = coordonataY;
        this.masaExploziva = masaExploziva;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Proiectil{");
        sb.append("coordonataX=").append(coordonataX);
        sb.append(", coordonataY=").append(coordonataY);
        sb.append(", masaExploziva=").append(masaExploziva);
        sb.append('}');
        return sb.toString();
    }

    public int getCoordonataX() {
        return coordonataX;
    }

    public void setCoordonataX(int coordonataX) {
        this.coordonataX = coordonataX;
    }

    public int getCoordonataY() {
        return coordonataY;
    }

    public void setCoordonataY(int coordonataY) {
        this.coordonataY = coordonataY;
    }

    public double getMasaExploziva() {
        return masaExploziva;
    }

    public void setMasaExploziva(double masaExploziva) {
        this.masaExploziva = masaExploziva;
    }

    @Override
    public int compareTo(Proiectil proiectil) {
        if(this.masaExploziva > proiectil.masaExploziva){
            return 1;
        } else if (this.masaExploziva < proiectil.masaExploziva) {
            return -1;
        }else{
            return 0;
        }
    }

    public int egalitateProiectileByMasa(Proiectil proiectil_1, Proiectil proiectil_2){
        if(proiectil_1.masaExploziva == proiectil_2.masaExploziva){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Proiectil proiectil = (Proiectil) o;
        return Double.compare(masaExploziva, proiectil.masaExploziva) == 0;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        List<Proiectil> listaProiectilFisier = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\proiectile.txt"))) {
            String linie = br.readLine();
            while (linie != null) {
                String[] detaliiProiectil = linie.split(",");

                int coordonataX = Integer.parseInt(detaliiProiectil[0]);
                int coordonataY = Integer.parseInt(detaliiProiectil[1]);
                double masaExploziva = Double.parseDouble(detaliiProiectil[2]);

                Proiectil p = new Proiectil(coordonataX, coordonataY, masaExploziva);
                listaProiectilFisier.add(p);

                linie = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n-------------");

        for (var p : listaProiectilFisier) {
            System.out.println(p.toString());
        }

        System.out.println("\n-------------");

        for (var p : listaProiectilFisier) {
            if (p.getCoordonataY() > 0) {
                System.out.println(p.toString());
            }
        }

        System.out.println("\n-------------");

        Map<Integer, List<Proiectil>> mapProiectilByY = listaProiectilFisier.stream()
                .collect(Collectors.groupingBy(Proiectil::getCoordonataY));
        for(var p: mapProiectilByY.entrySet()){
            double sumaMasa = p.getValue().stream().mapToDouble(Proiectil::getMasaExploziva).sum();

            System.out.println("Coordonata Y: " + p.getKey() + " -> Masa: " + sumaMasa);

            for(var p2: p.getValue()){
                System.out.println("  " + p2.toString());
            }
        }

        System.out.println("\n------------- Salvare in fisier binar");

        int nrCoordonotaZero = 0;
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src\\exploziiPeAxe.dat"))){
            for(var p: listaProiectilFisier){
                if(p.getCoordonataY() == 0 || p.getCoordonataX() == 0){
                    nrCoordonotaZero++;
                    oos.writeObject(p);
                }
            }
        }
        System.out.println("Numarul de proiectile ce au cel putin o coordonata 0 = " + nrCoordonotaZero);

        System.out.println("\n------------- Citire din fisier binar");

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src\\exploziiPeAxe.dat"))){
            while(true){
                try{
                    Proiectil p = (Proiectil) ois.readObject();
                    System.out.println(p);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (EOFException e) {
                    break;
                }
            }
        }

    }
}
