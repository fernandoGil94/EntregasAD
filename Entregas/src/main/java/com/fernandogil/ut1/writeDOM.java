package com.fernandogil.ut1;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class writeDOM {
    public static void main(String[] args) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        ArrayList<Tenista> listaTenistas = new ArrayList<>();
        listaTenistas.add(new Tenista(1,"Rafa","Nadal",22));
        listaTenistas.add(new Tenista(2,"Novak","Djokovic",21));
        listaTenistas.add(new Tenista(3,"Roger","Federer",20));
        listaTenistas.add(new Tenista(4,"Pete","Sampras",14));
        listaTenistas.add(new Tenista(5,"Bjorn","Borg",11));
        listaTenistas.add(new Tenista(6,"Ivan","Lendl",8));
        listaTenistas.add(new Tenista(7,"Jimmy","Connors",8));
        listaTenistas.add(new Tenista(8,"Andre","Agassi",8));
        listaTenistas.add(new Tenista(9,"John","McKenroe",7));
        listaTenistas.add(new Tenista(10,"Mats","Wilander",7));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation dom = builder.getDOMImplementation();
        Document documento = dom.createDocument(null, "xml", null);
        Element elementoRaiz = documento.createElement("Tenistas");
        documento.getDocumentElement().appendChild(elementoRaiz);
        Element nTenista = null , nDatos = null ;
        Text texto = null;

        for(Tenista t : listaTenistas){
            nTenista = documento.createElement("Tenista");
            elementoRaiz.appendChild(nTenista);
            nDatos = documento.createElement("Ranking");
            nTenista.appendChild(nDatos);
            texto = documento.createTextNode(Integer.toString(t.getIdRanking()));
            nDatos.appendChild(texto);
            nDatos = documento.createElement("Nombre");
            nTenista.appendChild(nDatos);
            texto = documento.createTextNode(t.getNom());
            nDatos.appendChild(texto);
            nDatos = documento.createElement("Apellido");
            nTenista.appendChild(nDatos);
            texto = documento.createTextNode(t.getApellido());
            nDatos.appendChild(texto);
            nDatos = documento.createElement("Grand-Slams");
            nTenista.appendChild(nDatos);
            texto = documento.createTextNode(Integer.toString(t.getGrandSlams()));
            nDatos.appendChild(texto);
        }

        Source fuente = new DOMSource(documento);
        Result resultado = new StreamResult(new File("RankTenistas.xml"));
        Transformer conversor = TransformerFactory.newDefaultInstance().newTransformer();
        conversor.setOutputProperty("indent", "yes");
        conversor.transform(fuente,resultado);

        try {
            DocumentBuilderFactory factoryLectura = DocumentBuilderFactory.newInstance();
            DocumentBuilder builderLectura = factoryLectura.newDocumentBuilder();
            Document documentoLectura = builderLectura.parse(new File("RankTenistas.xml"));
            NodeList tenistas = documentoLectura.getElementsByTagName("Tenista");
            System.out.println("<xml>");
            System.out.println("\t<Tenistas>");
            for (int i = 0; i < tenistas.getLength(); i++) {
                Node tenista = tenistas.item(i);
                Element elemento = (Element) tenista;
                System.out.println("\t\t<Tenista>");
                System.out.println("\t\t\t<Ranking>" + elemento.getElementsByTagName("Ranking").item(0)
                        .getChildNodes().item(0).getNodeValue() + "</Ranking>");
                System.out.println("\t\t\t<Nombre>" + elemento.getElementsByTagName("Nombre").item(0)
                        .getChildNodes().item(0).getNodeValue() + "</Nombre>");
                System.out.println("\t\t\t<Apellido>" + elemento.getElementsByTagName("Apellido").item(0)
                        .getChildNodes().item(0).getNodeValue() + "</Apellido>");
                System.out.println("\t\t\t<Grand-Slams>" + elemento.getElementsByTagName("Grand-Slams").item(0)
                        .getChildNodes().item(0).getNodeValue() + "</Grand-Slams>");
                System.out.println("\t\t</Tenista>");
            }
            System.out.println("\t</Tenistas>");
            System.out.println("</xml>");
        }catch (SAXException sax){
            sax.printStackTrace();
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
