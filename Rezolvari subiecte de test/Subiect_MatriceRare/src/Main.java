import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class Element implements Comparable<Element>, Serializable {
    private int indexLinie;
    private int indexColoana;
    private float valoareElement;

    public Element() {
        this.indexLinie = 0;
        this.indexColoana = 0;
        this.valoareElement = 0;
    }

    public Element(int indexLinie, int indecColoana, float valoareElement) {
        this.indexLinie = indexLinie;
        this.indexColoana = indecColoana;
        this.valoareElement = valoareElement;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Element {");
        sb.append(" indexLinie = ").append(indexLinie);
        sb.append(" | indexColoana = ").append(indexColoana);
        sb.append(" | valoareElement = ").append(valoareElement);
        sb.append(" }");
        return sb.toString();
    }

    public int getIndexLinie() {
        return indexLinie;
    }

    public void setIndexLinie(int indexLinie) {
        this.indexLinie = indexLinie;
    }

    public int getIndexColoana() {
        return indexColoana;
    }

    public void setIndexColoana(int indexColoana) {
        this.indexColoana = indexColoana;
    }

    public float getValoareElement() {
        return valoareElement;
    }

    public void setValoareElement(float valoareElement) {
        this.valoareElement = valoareElement;
    }

    @Override
    public int compareTo(Element elem) {
        if(this.valoareElement < elem.valoareElement) {
            return 1;
        }else if (this.valoareElement > elem.valoareElement) {
            return -1;
        }else{
            return 0;
        }
    }

    public int egalitateElemente(Element elem){
        if(this.indexLinie == elem.indexLinie &&
                this.indexColoana == elem.indexColoana &&
                this.valoareElement == elem.valoareElement){
            return 1;
        }else {
            return -1;
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        List<Element> listaElemente = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src\\matriceRara.csv"))){
//            String linie;
//            while((linie = br.readLine()) != null){
//                String[] valoareDinFisier = linie.split(",");
//
//                int indexLinie = Integer.parseInt(valoareDinFisier[0]);
//                int indexColoana = Integer.parseInt(valoareDinFisier[1]);
//                float valoareElement = Float.parseFloat(valoareDinFisier[2]);
//
//                Element elem = new Element(indexLinie, indexColoana, valoareElement);
//                listaElemente.add(elem);
//            }
            String linie = br.readLine();
            while(linie!= null){
                String[] valoareDinFisier = linie.split(",");

                int indexLinie = Integer.parseInt(valoareDinFisier[0]);
                int indexColoana = Integer.parseInt(valoareDinFisier[1]);
                float valoareElement = Float.parseFloat(valoareDinFisier[2]);

                Element elem = new Element(indexLinie, indexColoana, valoareElement);
                listaElemente.add(elem);

                linie = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n-------------------- Citire din fisier --------------------");

        for(var e: listaElemente){
            System.out.println(e.toString());
        }

        System.out.println("\n-------------------- Elementele negative --------------------");

        int contorElementeNegative = 0;
        for(var e: listaElemente){
            if(e.getValoareElement() < 0){
                contorElementeNegative++;
                System.out.println(e.toString());
            }
        }
        System.out.println("Numarul elementelor negative din fisier este: " + contorElementeNegative);

        System.out.println("\n-------------------- Valoare medie per Cheie (coloana) --------------------");

        Map<Integer, List<Element>> mapElemente = listaElemente.stream()
                        .collect(Collectors.groupingBy(Element::getIndexColoana));
        for(var elem: mapElemente.entrySet()){
            int nrValori = elem.getValue().size();
            float valoareMediePerKey = ((float) elem.getValue().stream()
                    .mapToDouble(Element::getValoareElement).sum())/nrValori;
            System.out.println("Index coloana: " + elem.getKey() +
                    " - Valoare medie: " + valoareMediePerKey);
        }

        System.out.println("\n-------------------- Salvare in fisier binar --------------------");

        try(ObjectOutputStream oos = new ObjectOutputStream
                (new FileOutputStream("diagonal.dat"))){
            for(var elem: listaElemente){
                if(elem.getIndexColoana() == elem.getIndexLinie()){
                    oos.writeObject(elem);
                }
            }
        }

        System.out.println("\n-------------------- Citire din fisier binar --------------------");

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream
                ("diagonal.dat"))){
            while(true){
                try {
                    Element e = (Element) ois.readObject();
                    System.out.println(e);
                }catch(EOFException e){
                    break; // cand s-a ajuns la finalul fisierului, se iese din while
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
