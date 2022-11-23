package com.fernandogil.ut1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tenista implements Serializable {
    private int idRanking;
    private String nom;
    private String apellido;
    @XmlElement(name = "Grand-Slams")
    private int grandSlams;


    public Tenista() {
    }

    public Tenista(int idRankingString,String nom, String apellido, int grandSlams) {
        this.idRanking = idRankingString;
        this.nom = nom;
        this.apellido = apellido;
        this.grandSlams = grandSlams;
    }

    public int getIdRanking() {
        return idRanking;
    }

    public void setIdRanking(int idRanking) {
        this.idRanking = idRanking;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getGrandSlams() {
        return grandSlams;
    }

    public void setGrandSlams(int grandSlams) {
        this.grandSlams = grandSlams;
    }

    @Override
    public String toString() {
        return "Tenista{" +
                "idRanking=" + idRanking +
                ", nom='" + nom + '\'' +
                ", apellido='" + apellido + '\'' +
                ", grandSlams=" + grandSlams +
                '}';
    }
}
