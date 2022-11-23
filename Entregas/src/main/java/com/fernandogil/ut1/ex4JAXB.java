package com.fernandogil.ut1;

import jakarta.xml.bind.*;

import java.io.File;

public class ex4JAXB {
    public static void main(String[] args) {
        try{    //CREAMOS EL OBJETO QUE VAMOS A CONVERTIR
            Tenista tenista = new Tenista(3,"Roger","Federer",20);
            //CREACION DEL CONTEXTO QUE SE ENCARGA DE LA DEFINICIÓN DEL OBJETO
            JAXBContext context = JAXBContext.newInstance(Tenista.class);
            //CREACIÓN DEL ONJETO MARSHALLER QUE GENERA LA ESTRUCTURA
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(tenista, new File("tenistaMarshall.xml"));

            context = JAXBContext.newInstance(Tenista.class);
            Unmarshaller unmar = context.createUnmarshaller();
            Tenista t = (Tenista) unmar.unmarshal(new File("tenistaMarshall.xml"));
            System.out.println("Ranking: " + t.getIdRanking());
            System.out.println("Nombre: " +t.getNom());
            System.out.println("Apellido: " + t.getApellido());
            System.out.println("Grand-Slams: " + t.getGrandSlams());
        }catch (PropertyException pe){
            pe.printStackTrace();
        }catch (JAXBException je){
            je.printStackTrace();
        }
    }
}
