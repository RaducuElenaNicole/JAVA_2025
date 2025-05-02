import java.util.*;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Masina m = new Masina();
        System.out.println(m.toString());

        int[] dotari = new int[3];
        Masina m1 = new Masina(1, "Audi", 4580.99f, new int[]{3, 4, 5});
        System.out.println(m1.toString());

        Masina m2 = new Masina(2, "Dacia Logan", 9080.99f, new int[]{3, 4, 5, 7});
        System.out.println(m2.toString());

        Masina m3 = (Masina)m2.clone();
        System.out.println(m3.toString());

        MasinaDeCurse mc = new MasinaDeCurse();
        System.out.println(mc.toString());

        MasinaDeCurse mc1 = new MasinaDeCurse(11, "Tesla", 4500.89f, new int[]{5, 6}, 56, 140);
        System.out.println(mc1.toString());

        List<Masina> masini = new ArrayList<>();
        masini.add(m1);
        masini.add(m2);
        masini.add(m3);

        for (Masina masina: masini){
            System.out.println(masina);   
        }
        
        masini.remove(m1);
        
        for(Masina masina: masini){
            System.out.println(masina);
        }

        List<MasinaDeCurse> masiniDeCurse = new Vector<MasinaDeCurse>();
        masiniDeCurse.add(mc1);
        masiniDeCurse.add(mc);

        for(int i = 0; i < masiniDeCurse.size(); i++){
          System.out.println(masiniDeCurse.get(i));
        }

        for(MasinaDeCurse mcI: masiniDeCurse){
            System.out.println(mcI);
        }

        Set<Masina> masiniSet = new TreeSet<>();
        masiniSet.add(m2);
        masiniSet.add(m3);
        masiniSet.add(m1);

        for(Masina ms: masiniSet){
            System.out.println(ms);
        }

        HashMap<Integer, Masina> mapMasini = new HashMap<>();
        mapMasini.put(m1.getId(), m1);
        mapMasini.put(m2.getId(), m2);
        System.out.println(mapMasini.get(m1.getId()));
        System.out.println(mapMasini.get(2));

        for(int cheie: mapMasini.keySet()){
            System.out.println(cheie + " " + mapMasini.get(cheie));
        }

        TreeMap<String, Masina> mapM = new TreeMap<>();
        mapM.put(m2.getDenumire(), m1);
        mapM.put(m1.getDenumire(), m2);

        System.out.println(mapM.get("Dacia Logan"));

        for(String cheie: mapM.keySet()){
            System.out.println(mapM.get(cheie));
        }

        // equals -> de facut
    }
}