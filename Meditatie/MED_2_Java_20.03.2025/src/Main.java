import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Comanda c1 = new Comanda(1, "Gabriela", 25.5f, 3,
                new String[]{"Paste", "Pizza", "Ciorba"});
        System.out.println("c1 -> " + c1.toString());

        List<Comanda> listComanda = new ArrayList<>();

        try {
            // fisierul de scriere
            FileOutputStream fos = new FileOutputStream("Text.txt");
            // fluxul de informatie (canalul de comunicare dintre Main si fisier)
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            // scrie informatia
            BufferedWriter bw = new BufferedWriter(osw);

            bw.write(Integer.toString(c1.nrComanda));
            bw.write(System.lineSeparator());
            bw.write(c1.numeClient);
            bw.write(System.lineSeparator());
            bw.write(Float.toString(c1.pret));
            bw.write(System.lineSeparator());
            bw.write(Integer.toString(c1.nrProduse));
            bw.write(System.lineSeparator());
            for(int i = 0; i < c1.nrProduse; i++){
                bw.write(c1.numeProduse[i]);
                bw.write(System.lineSeparator());
            }

            bw.write(System.lineSeparator());
            bw.write(System.lineSeparator());

            //bw.write(c1.toString());
            //bw.write(System.lineSeparator());

            bw.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FileInputStream fis = new FileInputStream("Text.txt");
            InputStreamReader iis = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(iis);

            c1.setNrComanda(Integer.parseInt(br.readLine()));
            c1.setNumeClient(br.readLine());
            c1.setPret(Float.parseFloat(br.readLine()));
            c1.setNrProduse(Integer.parseInt(br.readLine()));

//            for(int i = 0 ; i < c1.nrProduse; i++){
//                c1.seteazaNumeByIndex(br.readLine(), i);
//            }

            String[] produseComanda = new String[c1.nrProduse];
            for(int i = 0 ; i < c1.nrProduse; i++){
                produseComanda[i] = br.readLine();
            }
            c1.setNumeProduse(produseComanda);

            br.close();

            System.out.println("Citire din fisier txt: " + c1.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try{
            // scriere in fisier binar
            FileOutputStream fos = new FileOutputStream("Fisier.bin");
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeInt(c1.nrComanda);
            dos.writeUTF(c1.numeClient);
            dos.writeFloat(c1.pret);
            dos.writeInt(c1.nrProduse);
            for(int i = 0; i < c1.nrProduse; i++){
                dos.writeUTF(c1.numeProduse[i]);
            }

            dos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try{
            FileInputStream fis = new FileInputStream("Fisier.bin");
            DataInputStream dis = new DataInputStream(fis);

            c1.setNrComanda(dis.readInt());
            c1.setNumeClient(dis.readUTF());
            c1.setPret(dis.readFloat());
            c1.setNrProduse(dis.readInt());
            String[] produseComanda = new String[c1.nrProduse];
            for(int i = 0; i < c1.nrProduse; i++ ){
                produseComanda[i] = dis.readUTF();
            }
            c1.setNumeProduse(produseComanda);

            dis.close();

            System.out.println("Citire din fisier binar: " + c1.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Pentru orice obiect
        try{
            FileOutputStream fos = new FileOutputStream("Fisier.data");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(c1);

            oos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try{
            FileInputStream fis = new FileInputStream("Fisier.data");
            ObjectInputStream ois = new ObjectInputStream(fis);

            c1 = (Comanda) ois.readObject();

            System.out.println("Citire din fisier data: " + c1.toString());

            ois.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try{
            FileReader fr = new FileReader("Fisier.txt");
            BufferedReader br = new BufferedReader(fr);

            String linie = null;
            while((linie = br.readLine()) != null) {
                String[] linieDelimitat = linie.split(",");

                c1.nrComanda = Integer.parseInt(linieDelimitat[0]);
                c1.numeClient = linieDelimitat[1];
                c1.pret = Float.parseFloat(linieDelimitat[2]);
                c1.nrProduse = Integer.parseInt(linieDelimitat[3]);

                String[] produseComanda = new String[c1.nrProduse];
                for(int i = 4; i < linieDelimitat.length; i++){
                    for(int j = 0; j < c1.nrProduse; j++){
                        produseComanda[j] = linieDelimitat[i];
                    }
                }

                c1.setNumeProduse(produseComanda);

                System.out.println("Citire din fisier txt cu virgula: " + c1.toString());
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}