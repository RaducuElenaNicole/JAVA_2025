import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Seminar_Xml_Json {

    public static List<Produs> citireXml(String caleFisier) throws Exception {
        var rezultat = new ArrayList<Produs>();

        var factory = DocumentBuilderFactory.newInstance();
        var builder = factory.newDocumentBuilder();

        // 2. Citim conținutul din fișier
        Document document = builder.parse(caleFisier);
        Element radacina = document.getDocumentElement();

        NodeList noduriProdus = radacina.getElementsByTagName("produs");
        for (int index = 0; index < noduriProdus.getLength(); index++) {
            Element nodProdus = (Element) noduriProdus.item(index);

            int cod = Integer.parseInt(nodProdus
                    .getElementsByTagName("cod")
                    .item(0)
                    .getTextContent());

            String denumire = nodProdus
                    .getElementsByTagName("denumire")
                    .item(0)
                    .getTextContent();

            var nodTranzactii = (Element) nodProdus
                    .getElementsByTagName("tranzactii")
                    .item(0);

            var tranzactii = new ArrayList<Tranzactie>();
            var noduriTranzactie = nodTranzactii.getElementsByTagName("tranzactie");
            for (int indexTranzactii = 0; indexTranzactii < noduriTranzactie.getLength();
                 indexTranzactii++) {
                var nodTranzactie = (Element) noduriTranzactie.item(indexTranzactii);
                var tranzactie = new Tranzactie(
                        TipTranzactie.valueOf(nodTranzactie.getAttribute("tip").toUpperCase()),
                        Integer.parseInt(nodTranzactie.getAttribute("cantitate"))
                );
                tranzactii.add(tranzactie);

//                System.out.println(nodTranzactie.getTextContent());
            }

            rezultat.add(new Produs(cod, denumire, tranzactii));
        }

        return rezultat;
    }

    public static void salvareXml(String caleFisier, List<Produs> produse) throws Exception {
        // 1. Construire document XML gol
        var factory = DocumentBuilderFactory.newInstance();
        var builder = factory.newDocumentBuilder();

        var document = builder.newDocument();

        // 2. Construire și atașare elemente
        var radacina = document.createElement("produse");
        document.appendChild(radacina);

        for (var produs : produse) {
            var nodProdus = document.createElement("produs");
            radacina.appendChild(nodProdus);

            var nodCod = document.createElement("cod");
            nodCod.setTextContent(Integer.toString(produs.getCod()));
            nodProdus.appendChild(nodCod);

            var nodDenumire = document.createElement("denumire");
            nodDenumire.setTextContent(produs.getDenumire());
            nodProdus.appendChild(nodDenumire);

            var nodTranzactii = document.createElement("tranzactii");
            for (var tranzactie : produs.getTranzactii()) {
                var nodTranzactie = document.createElement("tranzactie");
                nodTranzactie.setAttribute("tip", tranzactie.getTip().toString().toLowerCase());
                nodTranzactie.setAttribute("cantitate", Integer.toString(tranzactie.getCantitate()));
                nodTranzactii.appendChild(nodTranzactie);
            }
            nodProdus.appendChild(nodTranzactii);
        }

        // 3. Salvare XML în fișier
        var transformerFactory = TransformerFactory.newInstance();
        var transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        try (var fisier = new FileOutputStream(caleFisier)) {
            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(fisier));
        }
    }

    public static void salvareJson(String caleFisier, List<Produs> produse) throws Exception {

        var jsonProduse = new JSONArray();

        for (var produs : produse) {

            var jsonProdus = new JSONObject();
            jsonProdus.put("cod", produs.getCod());
            jsonProdus.put("denumire", produs.getDenumire());

            var jsonTranzactii = new JSONArray();
            for (var tranzactie : produs.getTranzactii()) {
                var jsonTranzactie = new JSONObject();
                jsonTranzactie.put("tip", tranzactie.getTip().toString().toLowerCase());
                jsonTranzactie.put("cantitate", Integer.toString(tranzactie.getCantitate()));
                jsonTranzactii.put(jsonTranzactie);
            }
            jsonProdus.put("tranzactii", jsonTranzactii);

            jsonProduse.put(jsonProdus);
        }

        try (var fisier = new FileWriter(caleFisier)) {
            jsonProduse.write(fisier, 4, 0);
        }
    }

    public static List<Produs> citireJson(String caleFisier) throws Exception {
        var rezultat = new ArrayList<Produs>();

        try (var fisier = new FileInputStream(caleFisier)) {

            var tokener = new JSONTokener(fisier);
            var jsonProduse = new JSONArray(tokener);

            for (int index = 0; index < jsonProduse.length(); index++) {
                var jsonProdus = jsonProduse.getJSONObject(index);

                var tranzactii = StreamSupport
                        .stream(jsonProdus.getJSONArray("tranzactii").spliterator(), false)
                        .map(item -> (JSONObject) item)
                        .map(item -> new Tranzactie(
                                TipTranzactie.valueOf(item.getString("tip").toUpperCase()),
                                item.getInt("cantitate")
                        ))
                        .collect(Collectors.toList());

                rezultat.add(new Produs(
                        jsonProdus.getInt("cod"),
                        jsonProdus.getString("denumire"),
                        tranzactii
                ));
            }
        }

        return rezultat;
    }

    public static void main(String[] args) throws Exception {

        // Testare clase:
//        var p = new Produs(1, "test", Arrays.asList(
//                new Tranzactie(TipTranzactie.INTRARE, 10),
//                new Tranzactie(TipTranzactie.INTRARE, 5),
//                new Tranzactie(TipTranzactie.IESIRE, 11)
//        ));
//        System.out.println(p);
//        System.out.println(p.getStoc());


        var produse = citireXml("dateIN\\produse.xml");
        System.out.println("\nProduse citite din fisier XML:");
        produse.stream()
            .forEach(produs -> System.out.println(produs.toString()));
        salvareXml("dateOUT\\produse_generat.xml", produse);

        salvareJson("dateOUT\\produse_generat.json", produse);
        produse = citireJson("dateOUT\\produse_generat.json");
        System.out.println("\nProduse refacute din fisier JSON:");
        produse.stream()
                .forEach(produs -> System.out.println(produs.toString()));
    }

}
