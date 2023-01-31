//
// Auteur : Filipe Dias Morais
// Projet : ExerciceCommerce
// Date   : 31.01.2023
// 


package org.example;

// POJO class for the item

public class Item {
    private int num;
    private String description;
    private int prix;
    private int client;

    public Item(int num, String description, int prix, int client) {
        this.num = num;
        this.description = description;
        this.prix = prix;
        this.client = client;
    }

    public int getNum() {
        return num;
    }

    public String getDescription() {
        return description;
    }

    public int getPrix() {
        return prix;
    }

    public int getClient() {
        return client;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setClient(int client) {
        this.client = client;
    }
}
