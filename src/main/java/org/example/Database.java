//
// Auteur : Filipe Dias Morais
// Projet : ExerciceCommerce
// Date   : 31.01.2023
// 


package org.example;

import java.sql.*;

public class Database {

    Connection connection = null;

    public Database(String url, String User, String Password) {
        try {
            connection = DriverManager.getConnection(url, User, Password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getItems() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM items LEFT JOIN clients ON items.client = clients.num");
            StringBuilder result = new StringBuilder();
            while (resultSet.next()) {
                result.append(resultSet.getString("num")).append(", ").append(resultSet.getString("description")).append(", ").append(resultSet.getString("prix"));
                if (resultSet.getString("client") != null) {
                    result.append(", ").append(resultSet.getString("prenom"));
                }
                result.append("\n");
            }
            return result.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Error";
        }
    }

    public boolean getClient(String prenom) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients WHERE prenom = ?");
            statement.setString(1, prenom);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public int getSolde(String prenom) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT solde FROM clients WHERE prenom = ?");
            statement.setString(1, prenom);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("solde");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public boolean isBought(int item) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT client FROM items WHERE num = ?");
            statement.setInt(1, item);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("client") != 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void achete(int item, String prenom) {
        int solde = 0;
        if (isBought(item)) {
            throw new IllegalArgumentException("Item déjà acheté");
        }
        try {

            if (!getClient(prenom)) {
                connection.setAutoCommit(false);
                PreparedStatement s0 = connection.prepareStatement(
                        "INSERT INTO clients (prenom, solde) VALUES (?, 50)"
                );
                s0.setString(1, prenom);
                s0.executeUpdate();
            } else {
                connection.setAutoCommit(false);
            }


            PreparedStatement s1 = connection.prepareStatement(
                    "UPDATE items SET client = (SELECT num FROM clients WHERE prenom = ?) WHERE num = ?"
            );


            s1.setString(1, prenom);
            s1.setInt(2, item);
            s1.executeUpdate();

            solde = getSolde(prenom);

            try {
                PreparedStatement s2 = connection.prepareStatement(
                        "UPDATE clients SET solde = solde - (SELECT prix FROM items WHERE num = ?) WHERE prenom = ?"
                );
                s2.setInt(1, item);
                s2.setString(2, prenom);
                s2.executeUpdate();

                connection.commit();
                connection.setAutoCommit(true);
            } catch (Exception e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new IllegalArgumentException(prenom + " n'a pas assez de solde avec " + solde);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
