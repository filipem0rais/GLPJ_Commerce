//
// Auteur : Filipe Dias Morais
// Projet : ExerciceCommerce
// Date   : 31.01.2023
// 


package org.example;

// POJO class for the client

public class Client {
    private int num;
    private String prenom;
    private int solde;

    public Client(int num, String prenom, int solde) {
        this.num = num;
        this.prenom = prenom;
        this.solde = solde;
    }

    public int getNum() {
        return num;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getSolde() {
        return solde;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }
}
